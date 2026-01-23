# 심쿵가 (Heartbreak Price)

> "원하는 가격에 상품을 구매하세요"

![Android](https://img.shields.io/badge/Android-3DDC84?style=flat-square&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=flat-square&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=flat-square&logo=jetpackcompose&logoColor=white)
![Firebase](https://img.shields.io/badge/Firebase-FFCA28?style=flat-square&logo=firebase&logoColor=black)
![Koin](https://img.shields.io/badge/Koin-F6A008?style=flat-square&logo=koin&logoColor=black)

## 📌 Introduction

**심쿵가**는 사용자가 원하는 상품의 가격이 목표 가격 이하로 떨어졌을 때 알림을 받아볼 수 있는 스마트한 쇼핑 도우미 안드로이드 애플리케이션입니다.

네이버 쇼핑 검색을 통해 상품을 찾고, 관심 상품으로 등록하여 가격 변동 추적을 자동화하세요. 클라우드 기반 백엔드를 활용하여 실시간에 가까운 가격 모니터링 서비스를 제공합니다.

### 💡 Why 심쿵가?
> "망설이면 가격만 오를 뿐? 아니, 기다리면 가격은 내려갑니다!"

*   💸 **호갱 탈출**: 내가 찜한 그 상품, 최저가가 되면 가장 먼저 알려드립니다.
*   📉 **스마트한 소비**: 단순 최저가 검색을 넘어, 내가 설정한 '목표 가격' 도달 시점에 정확히 픽(Pick)!
*   ⚡ **실시간 캐치**: 가격 변동을 수시로 확인하는 번거로움 없이, 푸시 알림으로 편안하게 쇼핑 타이밍을 잡으세요.

#### 📆 개발 기간
2026.01.13 ~ 2026.01.21

## 🧑🏻‍💻 팀원 소개

|                                                                                                                                    [🐱 정건희](https://github.com/MisterJerry123)                                                                                                                                    |                                                                                                                                                                   [🐹 주연우](https://github.com/Neouul)                                                                                                                                                                   |                                                                                                                                                                   [🐸 김영우](https://github.com/whoamixzerone)                                                                                                                                                                   |                                                                                                                                   [🐻 홍희표](https://github.com/hhp227)                                                                                                                                   |
|:--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|:-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|
|                                                                                                                 <a href="https://github.com/MisterJerry123"> <img src="https://avatars.githubusercontent.com/u/62222459?s=96&v=4" width=200px alt="_"/> </a>                                                                                                                 |                                                                                                                                                <a href="https://github.com/Neouul"> <img src="https://avatars.githubusercontent.com/u/155894259?s=96&v=4" width=200px alt="_"/> </a>                                                                                                                                                 |                                                                                                                                              <a href="https://github.com/whoamixzerone"> <img src="https://avatars.githubusercontent.com/u/67082984?s=96&v=4" width=200px alt="_"/> </a>                                                                                                                                              |                                                                                                              <a href="https://github.com/hhp227"> <img src="https://avatars.githubusercontent.com/u/14990586?s=64&v=4" width=200px alt="_"/> </a>                                                                                                              |
|                                                                                                                                                    팀장 & Android                                                                                                                                                    |                                                                                                                                                                                        Android                                                                                                                                                                                         |                                                                                                                                                                                   Android                                                                                                                                                                                   |                                                                                                                                                    Android & Backend                                                                                                                                                     |


## ✨ Key Features

| 상품 검색 | 즐겨찾기 및 목표가 설정 | 푸시 알림 |
|:---:|:---:|:---:|
| <img src="docs/search_screenshot.png" width="200" /> | <img src="docs/wishlist_screenshot.png" width="200" /> | <img src="docs/notification_screenshot.png" width="200" /> |
| 네이버 쇼핑 API를 통한<br>빠른 상품 검색 | 관심 상품 등록 및<br>목표 가격(Target Price) 설정 | 가격 하락 시<br>실시간 FCM 푸시 알림 |

*   **🔍 스마트 검색**: 네이버 쇼핑 연동으로 방대한 상품 데이터베이스 검색 지원.
*   **❤️ 위시리스트 관리**: 관심 상품을 한눈에 모아보고 현재 가격 변동 추이 확인.
*   **🔔 가격 변동 알림**: 설정한 목표 가격 도달, 할인 발생 시 푸시 알림 전송.
*   **🔒 보안 로그인**: Firebase Authentication을 이용한 안전한 이메일 회원가입 및 로그인.
*   **📜 알림 히스토리**: 지난 알림 내역을 저장하여 놓친 딜도 다시 확인 가능.

## 🛠 Tech Stack

### Android (Mobile)
*   **Language**: Kotlin
*   **UI Framework**: Jetpack Compose (Material3 Design)
*   **Architecture**: Clean Architecture + MVVM + MVI Pattern
*   **Concurrency**: Coroutines, Flow
*   **Dependency Injection**: Koin
*   **Network**: Retrofit2, OkHttp, Kotlin Serialization
*   **Image Loading**: Coil
*   **Navigation**: Jetpack Navigation Compose

### Backend & Cloud (Serverless)
*   **Platform**: Firebase
*   **Database**: Cloud Firestore (NoSQL)
*   **Authentication**: Firebase Auth
*   **Messaging**: Firebase Cloud Messaging (FCM)
*   **Server Logic**: Cloud Functions for Firebase (TypeScript)

## 🏗 Architecture

이 프로젝트는 유지보수성과 확장성을 고려하여 **Clean Architecture** 원칙을 따르며, **MVVM** 패턴을 기반으로 구현되었습니다.


## 📸 Screenshots & Demo


## 🤝 협업 방식 및 개발 문화

### 1. 프로젝트 진행 원칙
*   **공통 설계**: 프로젝트 초기 아키텍처와 핵심 로직은 반드시 팀원 전원이 모여서 설계합니다.
*   **AI 기반 페어 프로그래밍**: 모든 업무는 '2인 1조 페어 프로그래밍 + AI 어시스턴트' 조합으로 진행합니다.

### 2. AI 가속 페어 프로그래밍 (AI Accelerated Pair Programming)
> "우리는 AI를 도구로 쓰되, 코드의 주인은 우리다."

**🔄 진행 방식 (Ping-Pong 모드)**
*   **Driver (AI 활용)**: AI에게 인터페이스나 테스트 코드를 요청하고 작성합니다.
*   **Navigator (검증 및 설계)**: Driver가 작성한 테스트를 통과하기 위한 로직을 AI와 함께 구상하거나, 공식 문서를 체크합니다.
*   **Cycle**: 15~20분 단위로 역할을 교대하며, 한 가지 작은 기능이 완료(Git Commit)되면 바로 역할을 바꿉니다.

**📋 그라운드 룰 (Ground Rules)**
*   **🗣 말하면서 코딩하기**: Driver는 현재 AI에게 어떤 지시를 내리고 있는지 끊임없이 공유합니다.
*   **⌨️ 키보드 뺏지 않기**: 답답해도 직접 수정하기보다 "AI한테 ~라고 물어보는 건 어때?"라고 제안합니다.
*   **🧐 Explain First**: AI가 작성한 코드를 적용하기 전, Driver는 Navigator에게 10초 내외로 해당 코드를 설명해야 합니다.
*   **⚖️ AI-Driven Conflict**: 의견 충돌 시 AI에게 장단점 비교를 요청하고 1분 내에 의사결정을 내립니다.
*   **📝 회고**: 교대 시 "방금 로직은 왜 이렇게 짰는지" 1~2분간 짧게 브리핑합니다.

**📅 팀 루틴**
*   **Daily Rotation**: 매일 페어를 교체하여 모든 팀원이 전체 시스템 구조를 파악합니다.
*   **Weekly Share**: 금요일마다 '삽질기' 및 기술 교류회를 가집니다.

## 🚀 Getting Started

### Prerequisites
*   Android Studio Ladybug | 2024.2.1 이상
*   JDK 17 이상
*   Firebase 프로젝트 설정 (`google-services.json`)
*   Naver Developers API Client ID/Secret

### Installation

1.  **Clone the repository**
    ```bash
    git clone https://github.com/hkjj/heartbreakprice-android.git
    ```

2.  **Secret Configuration**
    프로젝트 루트의 `local.properties` 파일에 API 키를 설정해야 합니다.
    ```properties
    NAVER_CLIENT_ID=your_naver_client_id
    NAVER_CLIENT_SECRET=your_naver_client_secret
    ```
    
    Firebase 콘솔에서 `google-services.json`을 다운로드하여 `app/` 디렉토리에 위치시킵니다.

3.  **Build & Run**
    Android Studio에서 프로젝트를 열고 Sync를 완료한 후 `app` 모듈을 실행합니다.

