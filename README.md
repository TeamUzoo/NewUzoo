




![header](https://capsule-render.vercel.app/api?type=waving&text=Uzoo&animation=scaleIn&color=timeGradient&fontSize=70&height=200 )


# 🌏 우주: Uzoo
##### _공부/업무에 방해되는 스마트폰, 이제는 집중 도구로 사용할 수는 없을까?_
</br>
</br>

### 데이터분석과 인공지능을 활용한 스마트폰 과몰입방지 서비스!

</br>

</br>

## 목차

- [🌏 우주: Uzoo](#-------uzoo)

  * [🚀 개요](#🚀-개요)
    + [- 화면구성](#--화면구성)
    + [- 참여 인원](#--참여-인원)
  * [🚀 프로젝트 기획](#----------)
    + [- 기획 의도](#--기획-의도)
    + [- 화면 기획서](#--화면-기획서)
    + [- 화면 플로우](#--화면-플로우)
    + [- 테이블 설계](#--테이블-설계)
    + [- API 설계서](#--API-설계서)
    + [- AWS 클라우드 기반의 시스템 구축](#--AWS-클라우드-기반의-시스템-구축)
    + [- 사용 기술](#--사용-기술)
    + [📌 진행 중의 문제점 및 해결 방법](#--------------------)
    + [- 사용 언어](#--사용-언어)
    + [- 사용 툴](#--사용-툴)
      - [기본사용 툴]
      - [협업툴]
    + [- 프레임워크 및 라이브러리](#--프레임워크-및-라이브러리)
  * [🚀 테스트](#------)
    + [- 단위 테스트](#--단위-테스트)
    + [- API 테스트](#--API-테스트)


</br>

## 🚀 개요

\- Backend, Frontend, DevOps의 통합 시간관리 안드로이드 앱 제작 프로젝트입니다.
\- 스마트폰의 화면잠금 및 타이머를 활용하여, 학업의 도움과 시간측정을 제공합니다.

</br>

### - 화면구성

> 스마트폰에 집중 시간을 설정합니다.  그리고 집중 시간 동안 키워낼 동물을 선택합니다.  
> 내 할 일을 다 하고 돌아오면, 성장한 나의 동물을 확인할 수 있습니다.
>  </br>

| 타이머                                                       | 설정                                                         | 업적                                                         |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| ![enter image description here](https://user-images.githubusercontent.com/105832457/190309072-adf9dae7-e467-41a2-8806-2982955740b1.gif) | ![enter image description here](https://user-images.githubusercontent.com/105832457/190309723-5fcf001f-b3ff-4e02-8e0b-65b76425bffc.gif) | ![enter image description here](https://user-images.githubusercontent.com/105832457/190309728-9a2b0cf9-2314-4fe5-881b-f37d2a760b62.gif) |
| 내가 집중할 시간을 선택해요! 핸드폰을 만지거나, 다른 앱을 키는 순간 타이머는 자동 소멸됩니다. | 내 계정에 대해 설정을 만져봅시다.                            | 내가 집중한 만큼의 보상을 추가로 얻어요!                     |

</br>

| 인공지능                                                     | 친구                                                         | 리스트                                                       | 나의 우주                                                    |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| ![enter image description here](https://user-images.githubusercontent.com/105832457/190309730-f4fa5560-0da5-47bd-9e9a-eb33b9df0d5e.gif) | ![enter image description here](https://user-images.githubusercontent.com/105832457/190309732-7e92a637-a751-4e80-aa0f-92c2ceb514bd.gif) | ![enter image description here](https://user-images.githubusercontent.com/105832457/190309734-8a4e8c42-09d1-405d-bf0e-a33468ba0fed.gif) | ![enter image description here](https://user-images.githubusercontent.com/105832457/190311167-2598c9f3-0751-4f86-b6bf-8672ffe4fa1f.gif) |
| 집중하기 적합한 환경인지 촬영하고 추가 코인을 얻어요!        | 친구의 성공/실패 및 랭킹을 확인해요!                         | 현재 내가 어떤일에 집중하고 있는지 확인하고 Todo List를 작성해보아요! | 내가 성공한 동물을 확인하고, 나의 집중시간을 그래프로 확인해요! |



</br>

### - 참여 인원

|                                                              |                                                              |                                                              |
| :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
| <img src="https://user-images.githubusercontent.com/102447800/190305479-1220a176-d937-4129-8e54-91e2f275a083.png" alt="enter image description here" style="zoom: 150%;" /> | ![enter image description here](https://user-images.githubusercontent.com/102447800/190305605-7785141e-04a3-4e1a-9ade-8c4f50ab1e16.png) | <img src="https://user-images.githubusercontent.com/102447800/190305704-b79d7105-9604-4d23-ad00-abd5450a9dd2.png" alt="enter image description here" style="zoom:150%;" /> |
|                          **한재훈**                          |                          **여현진**                          |                          **조윤영**                          |
|   개발 총괄 </br> 테이블 설계 </br>  협업툴 및 리소스 관리   | RestfulAPI 설계 </br> Rekognition 기능 구현등 백엔드 위주 작업 | 디자인 총괄 </br> 문서 작성 및 화면 기획등 프론트엔드 위주 작업 |

</br>

</br>



## 🚀 프로젝트 기획

### - 기획 의도

> 사용자가 스마트폰의 화면잠금 및 타이머를 활용하여, 학업의 도움과 시간측정을 제공합니다.

- 할 일을 미뤄두고, 스마트폰에 너무 중독된 학생/직장인들. 그렇다면 역으로 스마트폰을 이용하여 해야할 일을 완성시키면 어떨까?

- 잠시 핸드폰을 내려놓고 현실의 중요한 일에 집중해야 할 때,
  우주 : Uzoo 에서 동물을 키워 보세요. 시간이 지남에 따라, 동물은 점점 자랄것입니다. 

- 하지만 스마트폰의 유혹에 견디지 못하고 우주 : Uzoo 앱을 나가버리면 동물이 병에 들어버릴 것입니다.
- 동물을 키워나가며 얻게 되는 성취감과 책임감 덕분에, 핸드폰에서 점차 멀어지고 시간을 더욱 효율적으로 활용할 수 있습니다.

![enter image description here](https://user-images.githubusercontent.com/102447800/190291635-e6cbda6c-e725-4915-be1b-5d4f9f2bcb4b.png)

</br>



### - 화면 기획서

![](https://user-images.githubusercontent.com/102447800/191146470-9d517c4a-9ae0-4efc-9299-bbeceec57307.png)

</br>

- [화면 기획서 보기 클릭]: https://www.figma.com/file/Qggk82xEXTcKjfOcETnQ2T/%EC%9A%B0%EC%A3%BC-Uzoo-%ED%99%94%EB%A9%B4-%EA%B8%B0%ED%9A%8D%EC%84%9C?node-id=0%3A1

</br>



### - 화면 플로우

![enter image description here](https://user-images.githubusercontent.com/102447800/190296180-539a7486-d57c-4dc7-8bb0-d50aeff223da.png)

</br> 

[상세 플로우 차트 보기 클릭](https://www.figma.com/file/NTCJN9dpGnpSRmIulOm2dn/%ED%94%8C%EB%A1%9C%EC%9A%B0%EC%B0%A8%ED%8A%B8?node-id=0:1)

 </br> 

### - 테이블 설계

![enter image description here](https://user-images.githubusercontent.com/102447800/190310435-d5416e7d-4a48-40bc-bda7-ecc000bd45ae.png)

</br>

- [테이블 설계도 확대하기](https://drive.google.com/file/d/10NSzGyFc59dpKupwvLHp63_stMtM-lRN/view?usp=sharing)
- 왜래키(Foreign Key)를 이용해 중복 데이터 방지하였습니다.
- 실제 작업은 mySQL workbench에서 했습니다.

</br>
</br>

### - API 설계서

![enter image description here](https://user-images.githubusercontent.com/102447800/190303299-22c5f106-d818-4d57-86e3-c1054316f809.png)
</br>

- **헤더 부분에 RESTfulAPI의 형식에 맞추어 jwt인증토큰 처리를 하였습니다.**



</br>



- [상세 API 설계서 보기](https://docs.google.com/spreadsheets/d/1zu51hqyamtfsdBtHhGUQVd5dFlh0UWrh6zXra2bPybo/edit?usp=sharing)

 </br>

### - AWS 클라우드 기반의 시스템 구축

![](https://user-images.githubusercontent.com/102447800/191147040-a15ff2e0-28d1-4e0f-9f66-ac982ffdbf93.png)



- Lambda, MySQL과 RDS, S3 기반의 Serverless 구축
- API GateWay를 적용한 RESTfulAPI 기반 서버 개발 및 AWS CloudWatch를 활용하여 Logging
- AWS의 인공지능 Rekognition을 활용한 서버와 안드로이드 앱 개발-- 사용한 프레임워크 및 라이브러리

</br>

### - 사용 기술

-   인공지능 AWS Recognition 객체 탐지 기능  
-   MPAndroidChart 사용하여 그래프 구현
-   안드로이드 수명주기 Life Cycle 활용해서 사용자의 어플 전환 추적

</br>



### 📌 진행 중의 문제점 및 해결 방법

> Rekognition

- 서버 응답시간의 초과로 인해 API 작동이 멈추는 현상
  -> AWS의 Lambda 제한시간을 늘려서 해결

- 촬영 구도와 대상의 위치 따른 객체 탐지의 어려움
  -> 추후 촬영 가이드라인을 제시하여 원활한 객체탐지 도움

> Android LifeCycle

- 스마트폰의 자체 화면 꺼짐현상과 사용자의 다른 활동으로 인한 onPause 작동이 동일
  -> onPause 시에 상단의 화면이 무엇인지 감지하고 구분하는 작업을 추가하여 혼선 발생을 방지

</br>



### - 사용 언어
<a href="https://www.python.org/">
    <img src="https://img.shields.io/badge/python-%20-brightgreen"
        style="height : auto; margin-left : 8px; margin-right : 8px;"/>
</a>  : flask를 기반으로 둔 API를 설계 

</br>



<a href="https://www.java.com/ko/">
    <img src="https://img.shields.io/badge/JAVA-%20-%23F7DF1E"
        style="height : auto; margin-left : 8px; margin-right : 8px;"/>
</a> : 안드로이드 스튜디오에서 프론트엔드 개발
</br>

###  - 사용 툴
- Backend: MySQL, Visual Studio Code, Postman

- Frontend: Android Studio

- DevOps:  AWS EC2/ RDS/ CloudWatch/ S3/ Lambda, Serverless

  </br>

  

> #### 기본사용 툴

<a href="https://code.visualstudio.com/">
    <img src="https://img.shields.io/badge/VisualStudioCode-ss%20-%23007ACC"
        style="height : auto; margin-left : 8px; margin-right : 8px;"/>
</a> : flask를 이용하여 Restful API 구현에 사용했습니다.   
</br>

<a href="https://developer.android.com/studio/intro">
    <img src="https://img.shields.io/badge/AndroidStudio-%20-%233DDC84"
        style="height : auto; margin-left : 8px; margin-right : 8px;"/>
</a> : 앱의 화면 구현/프론트엔드에 사용하였습니다.   
</br>

<a href="https://www.mysql.com/">
    <img src="https://img.shields.io/badge/MySQL-%20-%234479A1"
        style="height : auto; margin-left : 8px; margin-right : 8px;"/>
</a> : DB관리 및 쿼리문 작성에 사용하였습니다.  
</br>

<a href="https://www.anaconda.com/">
    <img src="https://img.shields.io/badge/Anaconda-%20-%2344A833"
        style="height : auto; margin-left : 8px; margin-right : 8px;"/>
</a> : 파이썬을 이용하여 코드 작성 및 테스트에 사용했습니다.  
</br>

</br>

> #### 협업툴

<a href="https://github.com/">
<img src="https://img.shields.io/badge/GitHub-%20-%23181717"
   style="height : auto; margin-left : 8px; margin-right : 8px;"/></a> : 코드의 공유 및 실시간 반영을 위해 GitHub의 Team Repository 활용하였습니다.
</br>

<a href="https://www.postman.com/">
    <img src="https://img.shields.io/badge/Postman-%20-%23FF6C37"
        style="height : auto; margin-left : 8px; margin-right : 8px;"/></a>
 : RestfulAPI 테스트 및 서버 적용 여부 테스트에 사용하였습니다.   
</br>

 <a href="https://slack.com/intl/ko-kr/">
    <img src="https://img.shields.io/badge/Slack-%20-%234A154B"
        style="height : auto; margin-left : 8px; margin-right : 8px;"/></a>  : 진행상황 공유 및 회의진행, 전반적 소통을 위해 Slack 활용하였습니다. 
</br>

<a href="https://www.figma.com/">
    <img src="https://img.shields.io/badge/Figma-%20-%23F24E1E"
        style="height : auto; margin-left : 8px; margin-right : 8px;"/>
</a> :  APP의 디자인 리소스 작업 및 공유를 위해 Figma 활용하였습니다.

</br>

<a href="https://www.erdcloud.com/">
    <img src="https://img.shields.io/badge/ERD%20Cloud-%20-%239333EA"
        style="height : auto; margin-left : 8px; margin-right : 8px;"/>
</a>: DataBase의 테이블의 기록 및 공유를 위해 ERD Cloud 활용하였습니다.

</br>

<a href="https://www.google.com/drive/">
    <img src="https://img.shields.io/badge/Google%20Drive-%20-%234285F4"
        style="height : auto; margin-left : 8px; margin-right : 8px;"/>
</a>: API 명세서 작성, 대량 리소스 보관, 문서 작성 등에 사용하였습니다.  

</br>

 ###  - 프레임워크 및 라이브러리
<a href="https://flask.palletsprojects.com/en/2.2.x/">
    <img src="https://img.shields.io/badge/Flask-%20-%23000000"
        style="height : auto; margin-left : 8px; margin-right : 8px;"/>
</a> : RestfulAPI 작업에 사용하였습니다.  

</br>

<a href="https://www.serverless.com/">
    <img src="https://img.shields.io/badge/Serverless-%20-%23FD5750"
        style="height : auto; margin-left : 8px; margin-right : 8px;"/>
</a>:  작업 후에 서버에 바로 배포할 수 있게 했습니다.  

</br>

<a href="https://github.com/vinc3m1/RoundedImageView">
    <img src="https://img.shields.io/badge/Rounded Image View-%20-%f58e3b"
        style="height : auto; margin-left : 8px; margin-right : 8px;"/>
</a>: 이미지 프레임을 원형으로 사용하기 위해 사용한 라이브러리입니다.

</br>

<a href="https://github.com/tankery/CircularSeekBar">
    <img src="https://img.shields.io/badge/Circular SeekBar-%20-%23F7DF1E"
        style="height : auto; margin-left : 8px; margin-right : 8px;"/>
</a>: 타이머의 시간을 조절하는 영역을 수직의 ProgressBar가 아닌 원형으로 구성하기 위해 사용하였습니다.

</br>

<a href="https://github.com/PhilJay/MPAndroidChart">
    <img src="https://img.shields.io/badge/MPAndroidChart-%20-%23302bb5"
        style="height : auto; margin-left : 8px; margin-right : 8px;"/>
</a> : 사용자의 데이터를 가공하고 실시간으로 시각화하기 위해 사용하였습니다.

</br>

</br>



## 🚀 테스트



### - 단위 테스트

- 서버:  함수별로 테스트 했습니다. 
- SQL: 쿼리문으로 mySQL Workbench에서 직접 실행하여 테스트 했습니다.

</br>

### - API 테스트

- Postman : 각 API 별로 로컬테스트 -≫ 피드백 문서 작성 -≫ 공유 및 테스트 -≫ 서버 배포 순으로 함수 단위로 테스트를 하였습니다.


</br>

![Footer](https://capsule-render.vercel.app/api?type=waving&color=timeGradient&height=200&section=footer)

