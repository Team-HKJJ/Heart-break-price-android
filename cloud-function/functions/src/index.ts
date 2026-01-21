import * as admin from "firebase-admin";
import {getFirestore} from "firebase-admin/firestore";
import {onSchedule} from "firebase-functions/v2/scheduler";
import {NAVER_CLIENT_ID,
  NAVER_CLIENT_SECRET,
} from "./params";

admin.initializeApp();
const db = getFirestore("heart-break-price");

/**
 * 네이버 API 인증 정보를 환경 변수에서 가져온다.
 *
 * @return {Object} 네이버 API 인증 정보
 * @return {string} return.clientId 네이버 Client ID
 * @return {string} return.clientSecret 네이버 Client Secret
 * @throws {Error} 환경 변수가 설정되어 있지 않은 경우
 */
function getNaverConfig() {
  const clientId = process.env.NAVER_CLIENT_ID;
  const clientSecret = process.env.NAVER_CLIENT_SECRET;

  if (!clientId || !clientSecret) {
    throw new Error("Missing Naver API configuration");
  }

  return {clientId, clientSecret};
}

/**
 * 네이버 쇼핑 API 응답 아이템 타입
 */
interface NaverShoppingItem {
  lprice: string;
}

/**
 * 네이버 쇼핑 API 응답 타입
 */
interface NaverShoppingResponse {
  items: NaverShoppingItem[];
}

/**
 * 네이버 쇼핑 API를 호출하여 상품 가격을 조회한다.
 *
 * @param {string} query 검색 키워드
 * @return {Promise<{ price: number } | null>} 조회된 가격 또는 null
 */
async function fetchProductFromNaver(
  query: string
): Promise<{ price: number } | null> {
  const {clientId, clientSecret} = getNaverConfig();

  const url =
    "https://openapi.naver.com/v1/search/shop.json" +
    `?query=${encodeURIComponent(query)}` +
    "&display=1&sort=sim";

  const res = await fetch(url, {
    headers: {
      "X-Naver-Client-Id": clientId,
      "X-Naver-Client-Secret": clientSecret,
    },
  });

  if (!res.ok) {
    console.error("Naver API error:", res.status);
    return null;
  }

  const json = (await res.json()) as NaverShoppingResponse;

  if (!json.items || json.items.length === 0) {
    return null;
  }

  return {
    price: parseInt(json.items[0].lprice, 10),
  };
}

/**
 * 디스코드 웹훅으로 알림 전송 결과를 전송한다.
 *
 * @param {number} totalGenerated 생성된 알림(DB 저장) 수
 * @param {number} totalSent 성공적으로 전송된 FCM 수
 * @param {number} totalFailed 전송 실패한 FCM 수
 */
async function sendDiscordWebhook(
  totalGenerated: number,
  totalSent: number,
  totalFailed: number
) {
  const webhookUrl = process.env.DISCORD_WEBHOOK_URL;
  if (!webhookUrl) {
    console.warn("DISCORD_WEBHOOK_URL is not set.");
    return;
  }

  const message = {
    content: `📢 [알림 발송 리포트]\n- 생성된 알림: ${totalGenerated}건\n- FCM 전송 성공: ${totalSent}건\n- FCM 전송 실패: ${totalFailed}건`,
  };

  try {
    await fetch(webhookUrl, {
      method: "POST",
      headers: {"Content-Type": "application/json"},
      body: JSON.stringify(message),
    });
  } catch (e) {
    console.error("Failed to send Discord webhook:", e);
  }
}

/**
 * 목표 가격에 도달한 경우 사용자에게 푸시 알림을 전송한다.
 *
 * @param {string} productId 상품 ID
 * @param {string} productName 상품명
 * @param {string} productImage 상품이미지
 * @param {number} oldPrice 원가
 * @param {number} currentPrice 현재 가격
 * @return {Promise<{generated: number, sent: number, failed: number}>} 결과
 */
async function notifyUsersIfNeeded(
  productId: string,
  productName: string,
  productImage: string,
  oldPrice: number,
  currentPrice: number
): Promise<{ generated: number; sent: number; failed: number }> {
  let generated = 0;
  let sent = 0;
  let failed = 0;

  // 1. 이 상품을 찜한 유저들의 ID 목록을 가져옴 (UserList 서브컬렉션 조회)
  const userListSnapshot = await db
    .collection("Products")
    .doc(productId)
    .collection("UserList")
    .get();

  // 찜한 유저가 없으면 종료
  if (userListSnapshot.empty) {
    return {generated, sent, failed};
  }

  for (const userListDoc of userListSnapshot.docs) {
    const userId = userListDoc.id; // UserList 문서 ID가 곧 User ID

    // 2. 해당 유저의 정보와 상세 Wish 설정(목표가 등)을 가져옴
    const userDocRef = db.collection("Users").doc(userId);
    const wishDocRef = userDocRef.collection("Wishes").doc(productId);

    // 병렬로 조회하여 성능 향상
    const [userSnap, wishSnap] = await Promise.all([
      userDocRef.get(),
      wishDocRef.get(),
    ]);

    if (!userSnap.exists || !wishSnap.exists) continue;

    const user = userSnap.data();
    const wish = wishSnap.data();

    const fcmToken: string | undefined = user?.fcmToken;
    const targetPrice: number | undefined = wish?.targetPrice;
    const targetNotified: boolean = wish?.targetNotified ?? false;

    // 가격 업데이트를 위한 데이터 객체 (기본적으로 가격은 무조건 업데이트)
    const updateData: any = {
      price: currentPrice,
    };

    // 알림 조건 확인
    const shouldNotify =
      targetPrice !== undefined &&
      currentPrice <= targetPrice &&
      !targetNotified;

    if (shouldNotify) {
      const message =
        `${productName} 가격이 목표가에 도달했어요! ` +
        `${currentPrice.toLocaleString()}원`;

      /** 1️⃣ Firestore notifications 저장 */
      await userDocRef.collection("Notifications").add({
        type: "TARGET_REACHED",
        productName,
        productImage,
        message,
        timestamp: admin.firestore.FieldValue.serverTimestamp(),
        isRead: false,
        oldPrice,
        newPrice: currentPrice,
      });
      generated++;

      /** 2️⃣ FCM 푸시 전송 */
      if (fcmToken) {
        try {
          await admin.messaging().send({
            token: fcmToken,
            notification: {
              title: "🎯 목표가 도달!",
              body: message,
            },
            data: {
              type: "TARGET_REACHED",
              productId,
              newPrice: currentPrice.toString(),
            },
          });
          sent++;
        } catch (e) {
          console.error(`Failed to send FCM to user ${userId}:`, e);
          failed++;
        }
      } else {
        console.log(`User ${userId} has no FCM token. Notification saved but push skipped.`);
      }

      // 알림을 보냈음을 표시
      updateData.targetNotified = true;
      updateData.notifiedAt = admin.firestore.FieldValue.serverTimestamp();
    }

    /** 3️⃣ wish 상태 업데이트 (가격은 항상 업데이트, 알림 상태는 조건부 업데이트) */
    await wishDocRef.update(updateData);
  }

  return {generated, sent, failed};
}

/**
 * 상품 가격을 주기적으로 크롤링하고
 * 가격 변동 시 사용자에게 알림을 전송한다.
 */
export const crawlProductPrices = onSchedule(
  {
    schedule: "every 60 minutes",
    secrets: [NAVER_CLIENT_ID, NAVER_CLIENT_SECRET],
    timeZone: "Asia/Seoul",
  },
  async () => {
    console.log("Crawl started");
    const webhookUrl = process.env.DISCORD_WEBHOOK_URL;
    console.log(`Webhook URL loaded: ${webhookUrl ? "Yes (starts with " + webhookUrl.substring(0, 5) + ")" : "No"}`);

    const productsSnapshot = await db.collection("Products").get();
    let totalGenerated = 0;
    let totalSent = 0;
    let totalFailed = 0;

    for (const productDoc of productsSnapshot.docs) {
      const product = productDoc.data();

      const productId: string = productDoc.id;
      const productName: string = product.name;
      const oldPrice: number = product.price;

      const crawlResult = await fetchProductFromNaver(productName);
      if (!crawlResult) continue;

      const newPrice = crawlResult.price;

      if (newPrice !== oldPrice) {
        console.log(`Price changed for ${productName}: ${oldPrice} -> ${newPrice}`);
        await productDoc.ref.update({
          price: newPrice,
          updatedAt: admin.firestore.FieldValue.serverTimestamp(),
        });

        const {generated, sent, failed} = await notifyUsersIfNeeded(
          productId,
          productName,
          product.image,
          oldPrice,
          newPrice
        );
        totalGenerated += generated;
        totalSent += sent;
        totalFailed += failed;
      }
    }

    console.log(`Crawl finished. Generated: ${totalGenerated}, Sent: ${totalSent}, Failed: ${totalFailed}`);

    if (totalGenerated > 0 || totalSent > 0 || totalFailed > 0) {
      console.log("Sending Discord webhook...");
      await sendDiscordWebhook(totalGenerated, totalSent, totalFailed);
    } else {
      console.log("No notifications generated/sent/failed. Skipping webhook.");
    }
  }
);
