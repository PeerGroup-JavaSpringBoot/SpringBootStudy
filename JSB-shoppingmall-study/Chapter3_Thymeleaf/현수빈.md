# Chapter 3. Thymeleaf 

### 서버 사이드 템플릿 엔진 

: Thymeleaf, JSP, Freemarker, Groovy, Mustache

화면을 동적으로 만들기 위해 템플릿 엔진 사용 

: 미리 정의된 템플릿을 만들고, 동적으로 HTML 페이지를 만들어서 클라이언트에 전달하는 방식

요청이 올 때마다 서버에서 새로운 HTML 페이지를 만들어 주기 때문에 서버 사이드 렌더링 방식이라고 함.

즉, HTML 코드에서 고정적으로 사용되는 부분은 템플릿으로 만들어두고 동적으로 생성되는 부분만 템플릿 특정 장소에 끼워넣는 방식이다.

과정)

1. 클라이언트의 요청을 받아
2. 필요한 데이터를 가져온다.
3. 템플릿에 해당 데이터를 넣는다.
4. 서버에서 HTML을 그린다.
5. HTML을 클라이언트에게 전달한다.


### cf) 클라이언트 사이드 템플릿 엔진이란?

: HTML 형태로 코드를 작성할 수 있고, 동적으로 DOM을 그리게 해주는 역할을 한다. 

과정) 

1. 클라이언트에서 공통적인 프레임을 미리 템플릿으로 만든다.
2. 서버에서 필요한 데이터를 받는다.
2. 데이터를 템플릿의 적절한 위치에 재배치하고, DOM에 동적으로 그려준다.

클라이언트 사이드 템플릿 엔진의 필요성 

- js 라이브러리로 랜더링이 끝난 후 서버 통신 없이 화면 변경이 필요한 경우

### Spring MVC 템플릿 엔진 vs Spring Boot 템플릿 엔진

Java Object에서 데이터를 생성하여 Template에 넣어주면 템플릿 엔진에서 Template에 맞게 변환하여 html 파일을 생성하는 역할을 한다.

- Spring Template Engine 참고

JSP, Thymeleaf, Groovy, Freemarker, Jade4j, JMustache, Pebble, Handlebars

추천
- Thymeleaf

비추천
- Velocity는 Spring 버전 4.3부터 사용을 중단한다.

<br/>

Spring Boot Template Engine 공식 지원 템플릿
- Mustache, Thymeleaf, Groovy, Freemarker

추천
- Handlebars
- Mustache

출처 : [https://gmlwjd9405.github.io/2018/12/21/template-engine.html]


### Spring Boot Devtools

: 애플리케이션 개발 시 유용한 기능들을 제공하는 모듈

대표적인 기능 
- Automatic Restart : classpath에 있는 파일이 변경될 때마다 애플리케이션을 자동으로 재시작해줌. 개발자가 소스 수정 후 애플리케이션을 재실행하는 과정을 줄일 수 있으므로 생산성을 향상시킬 수  있음.
- Live Reload : 정적 자원 수정 시 새로 고침 없이 바로 적용 가능
- Property Defaults : Thymeleaf는 기본적으로 성능 향상을 위해 캐싱 기능 사용. 하지만, 개발 과정에서 캐싱 기능을 사용한다면 수정한 소스가 제대로 반영되지 않을 수 있기 때문에 cache의 기본값을 false로 설정할 수 있음

