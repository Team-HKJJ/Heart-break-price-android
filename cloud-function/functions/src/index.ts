import * as admin from "firebase-admin";
import {onSchedule} from "firebase-functions/v2/scheduler";
import {NAVER_CLIENT_ID, NAVER_CLIENT_SECRET} from "./params";

admin.initializeApp();
const db = admin.firestore();

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
 * 목표 가격에 도달한 경우 사용자에게 푸시 알림을 전송한다.
 *
 * @param {string} productId 상품 ID
 * @param {string} productName 상품명
 * @param {number} currentPrice 현재 가격
 * @return {Promise<void>}
 */
async function notifyUsersIfNeeded(
  productId: string,
  productName: string,
  currentPrice: number
): Promise<void> {
  const usersSnapshot = await db.collection("Users").get();

  for (const userDoc of usersSnapshot.docs) {
    const user = userDoc.data();
    const fcmToken: string | undefined = user.fcmToken;

    if (!fcmToken) continue;

    const wishRef = userDoc.ref
      .collection("wishes")
      .doc(productId);

    const wishSnap = await wishRef.get();
    if (!wishSnap.exists) continue;

    const wish = wishSnap.data();
    const targetPrice: number | undefined = wish?.targetPrice;

    if (
      targetPrice !== undefined &&
      currentPrice <= targetPrice
    ) {
      await admin.messaging().send({
        token: fcmToken,
        notification: {
          title: "🎉 가격 알림!",
          body: `${productName} 가격이 ${currentPrice.toLocaleString()}원으로 내려갔어요!`,
        },
        data: {
          productId,
          price: currentPrice.toString(),
        },
      });

      await wishRef.update({
        price: currentPrice,
        notifiedAt: admin.firestore.FieldValue.serverTimestamp(),
      });
    }
  }
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
    const productsSnapshot = await db.collection("Products").get();

    for (const productDoc of productsSnapshot.docs) {
      const product = productDoc.data();

      const productId: string = product.id;
      const productName: string = product.name;
      const oldPrice: number = product.price;

      const crawlResult = await fetchProductFromNaver(productName);
      if (!crawlResult) continue;

      const newPrice = crawlResult.price;

      if (newPrice !== oldPrice) {
        await productDoc.ref.update({
          price: newPrice,
          updatedAt: admin.firestore.FieldValue.serverTimestamp(),
        });

        await notifyUsersIfNeeded(
          productId,
          productName,
          newPrice
        );
      }
    }
  }
);
