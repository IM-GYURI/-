# Project : Public-Wifi
- 서울시 공공 와이파이 OPEN API를 이용해 사용자의 위치 혹은 특정 위치에서 가장 가까운 20개의 공공 와이파이 정보를 제공
- 프로젝트 기간 : 2024.02.20 ~ 2024.03.11

# Tech Stack
- Language: `Java`
- Build : `Maven`
- Database : `SQLITE`
- Server : `Tomcat 8.5`
- JDK : `JDK 1.8`
- Web : `JSP`, `HTML5`, `CSS`
- Library : `Lombok`, `Okhttp3`, `Gson`

# 프로젝트 작동 순서 및 기능
- OPEN API를 활용하여 서울시의 모든 공공 와이파이 정보를 가져옵니다.
- 사용자의 위치 좌표를 기반으로 가장 가까운 공공 와이파이 정보 20개를 보여줍니다.
- 사용자가 조회한 위치 정보와 조회한 시점을 기준으로 DB에 정보를 저장하고, 저장된 위치 히스토리를 조회 및 삭제할 수 있습니다.
- 특정 공공 와이파이의 상세 정보를 제공합니다.
- 사용자는 북마크 그룹을 생성, 조회, 수정, 삭제할 수 있습니다.
- 사용자는 공공 와이파이 정보를 북마크로 추가하거나 삭제할 수 있습니다.

# ERD
![erd 캡처](https://github.com/IM-GYURI/public-wifi/assets/80020777/9eec8e80-f248-49db-90ba-e2bf951d5a8f)

# 시연영상
![KakaoTalk_20240318_033343673](https://github.com/IM-GYURI/public-wifi/assets/80020777/6a175a94-8dc5-4bf6-86bf-229e72730124)
[Youtube 링크](https://www.youtube.com/watch?v=K9lwkijCfp8)
