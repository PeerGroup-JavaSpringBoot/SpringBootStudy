# Spring Boot Study
### `Spring Boot Fighter`
> Since 2022.02.11

  - <a href="https://www.notion.so/Peer-Group-2-4f767d2bb511429db7b2bb4fd8c94055">Spring Boot Fighter 스터디 기록 노션</a>
  - [Repository Rule](#repository-rule)
  - [Collaborator](#collaborator)
  - [Reference](#reference)
  
<p align="center">
  <img src="https://blog.kakaocdn.net/dn/bTUS0r/btqCtYkkVjX/d3KozubgCSWLv1X9V5lbY1/img.png" alt="coding" width="150px" />
</p>

### Collaborator

<p align="center">
  
<a href="https://github.com/myway00">
  <img src="https://github.com/myway00.png" width="100">
</a>
  
 <a href="https://github.com/gdakate">
  <img src="https://github.com/gdakate.png" width="100">
</a>
  
 <a href="https://github.com/hehahihang">
  <img src="https://github.com/hehahihang.png" width="100">
</a>
  
 <a href="https://github.com/LeeJin0527">
  <img src="https://github.com/LeeJin0527.png" width="100">
</a>
  
 <a href="https://github.com/Subinhyun">
  <img src="https://github.com/Subinhyun.png" width="100">
</a>
  
 <a href="https://github.com/yoon-youngjin">
  <img src="https://github.com/yoon-youngjin.png" width="100">
</a>
  
</p>

### Repository-Rule

> 1) [Github 가이드](https://www.notion.so/Github-Study-2bac0600e75d477e828e96ab319f0247)<br>
> 2) [미션 올리는 방법](https://www.notion.so/Mission-63c14f867c6a4a5583f143a9472255f7)

- **Commit convention rule** : <br>
   - => `peer/성명 : n번째 미션 수행 / Chapter1 내용 정리 `
   - (ex) `peer/성명 : 첫번째 미션 수행` or `peer/성명 : Chapter1 내용 정리`
   
 <br>
 
- **Branch naming convention** : <br>
   - => `peer/성명` 
   - (ex) `peer/김동윤`
 <br> 


- **Pull Request rule** : <br>
    <br>
   - `Pull Request` 제목 : `[peer/성명] : 수행한 내용`
      - (ex) `[peer/성명] 첫번째 미션 수행` or `[peer/성명] Chapter1 내용 정리`<br><br>
   - `Pull Request` 내용 : 
      - 자유롭게 적으셔도 좋고! (다만 자신이 어떤 부분 공부했는지 등에 대해서는 넣어주시면 다른 분들이 볼 때 편하실 것 같네욥 ㅎㅎ)
      - ![image](https://user-images.githubusercontent.com/76711238/153603767-44a0b735-dd9f-4398-ba7c-b94a9a793196.png)<br>
      - 위의 이미지에서 템플릿 복붙해서 작성해주시면 됩니다!

 <br> <br>

## Table of Contents

1) The Origin : 스프링부트 강의 (2/11 ~ 4/5)
- [Chapter 1. Basics]
- [Chapter 2. Spring Boot Basics (1)]
- [Chapter 3. Spring Boot Basics (2)]
- [Chapter 4. CRUD & Data (1)]
- [Chapter 5. CRUD & Data (2)]
- [Chapter 6. Spring Boot 기능활용 (1)]
- [Chapter 7. Spring Boot 기능활용 (2)]
- [Chapter 8. Spring Security]
- [Chapter 9. Spring과 Middlewares]

2) 스프링 부트 쇼핑몰 프로젝트 with JPA (4/12 ~ )
- [1장 개발 환경 구축]
1.1 스프링 부트의 특징
1.2 JDK 설치
1.3 인텔리제이 설치
1.4 애플리케이션 실행하기
1.4.1 Spring Boot Project 생성하기
1.4.2 빌드 도구
1.4.3 설정 파일(application.properties)
1.4.4 Hello World 출력하기
1.5 Lombok 라이브러리
1.6 MySQL 설치하기


- [2장 Spring Data JPA]
2.1 JPA
2.1.1 JPA란?
2.1.2 JPA 동작 방식
2.2 쇼핑몰 프로젝트 생성하기
2.2.1 프로젝트 생성하기
2.2.2 application.properties 설정하기
2.3 상품 엔티티 설계하기
2.3.1 상품 엔티티 설계하기
2.4 Repository 설계하기
2.5 쿼리 메소드
2.6 Spring DATA JPA @Query 어노테이션
2.7 Spring DATA JPA Querydsl

- [3장 Thymeleaf 학습하기]
3.1 Thymeleaf 소개
3.2 Spring Boot Devtools
3.2.1 Automatic Restart 적용하기
3.2.2 Live Reload 적용하기
3.2.3 Property Defaults 적용하기
3.3 Thymeleaf 예제 진행하기
3.3.1 th:text 예제
3.3.2 th:each 예제
3.3.3 th:if, th:unless 예제
3.3.4 th:switch, th:case 예제
3.3.5 th:href 예제
3.4 Thymeleaf 페이지 레이아웃
3.4.1 Thymeleaf Layout Dialect dependency 추가하기
3.5 부트스트랩으로 header, footer 영역 수정하기
3.5.1 Bootstrap CDN 추가하기
3.5.2 Bootstrap Navbar Component 활용하기


- [4장 스프링 시큐리티를 이용한 회원 가입 및 로그인]
4.1 스프링 시큐리티 소개
4.2 스프링 시큐리티 설정 추가하기
4.2.1 security dependency 추가하기
4.2.2 스프링 시큐리티 설정하기
4.3 회원 가입 기능 구현하기
4.4 로그인/로그아웃 구현하기
4.4.1 UserDetailsService
4.4.2 UserDetail
4.4.3 로그인/로그아웃 구현하기
4.5 페이지 권한 설정하기


- [5장 연관 관계 매핑]
5.1 연관 관계 매핑 종류
5.1.1 일대일 단방향 매핑하기
5.1.2 다대일 단방향 매핑하기
5.1.3 다대일/일대다 양방향 매핑하기
5.1.4 다대다 매핑하기
5.2 영속성 전이
5.2.1 영속성 전이란?
5.2.2 고아 객체 제거하기
5.3 지연 로딩
5.4 Auditing을 이용한 엔티티 공통 속성 공통화

- [6장 상품 등록 및 조회하기]
6.1 상품 등록하기
6.2 상품 수정하기
6.3 상품 관리하기
6.4 메인 화면
6.5 상품 상세 페이지

- [7장 주문]
7.1 주문 기능 구현하기
7.2 주문 이력 조회하기
7.3 주문 취소하기

- [8장 장바구니]
8.1 장바구니 담기
8.2 장바구니 조회하기
8.3 장바구니 상품 주문하기

### Reference

- [CS-study](https://github.com/Seogeurim/CS-study#repository-rule)


