# Chapter 3: Thymeleaf
- 화면을 동적으로 만들려면 템플릿 엔진을 사용
- 미리 정의된 템플릿을 만들고 동적으로 HTML 페이지를 만들어서 클라이언트에 전달하는 방식
- 요청이 올 때마다 서버에서 새로운 HTML 페이지를 만들어 주기 때문에 **서버 사이드 렌더링 방식**이라고 함
- 서버 사이드 템플릿 엔진으로는 Thymeleaf, JSP, Freemarker, Groovy, Mustache, ...
- 스프링에서 권장하는 템플릿 엔진은 Thymeleaf

## Spring Boot Devtools
- 애플리케이션 개발 시 유용한 기능들을 제공하는 모듈

### Spring Boot Devtools에서 제공하는 대표적인 기능
- [x] Automatic Restart
  - classpath에 있는 파일이 변경될 때마다 애플리케이션을 자동으로 재시작해준다.
  - 개발자가 소스 수정 후 애플리케이션을 재실행하는 과정을 줄일 수 있으므로 생산성을 향상시킬 수 있다.
- [x] Live Reload
  - 정적 자원(html, css, js) 수정 시 새로 고침 없이 바로 적용 가능
- [x] Property Defaults
  - Thymeleaf는 기본적으로 성능을 향상시키기 위해서 캐싱 기능을 사용하는데 개발하는 과정에서 캐싱 기능을 사용한다면 수정한 소스가 제대로 반영되지 않을 수 있기 때문에 cache의 기본값을 false로 설정 가능

## Thymeleaf 문법

### th:text

```java
@GetMapping("/ex02")
    public String test02(Model model){
        ItemDto itemDto = new ItemDto();
        itemDto.setItemDetail("상품 상세 설명");
        itemDto.setItemNm("테스트 상품1");
        itemDto.setPrice(10000);
        itemDto.setRegTime(LocalDateTime.now());

        model.addAttribute("itemDto", itemDto);
        return "thymeleafEx/thymeleafEx02";
    }
```
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h1>상품 데이터 출력 예제</h1>
<div>
    상품명 : <span th:text="${itemDto.itemNm}"></span>
</div>
<div>
    상품상세설명 : <span th:text="${itemDto.itemDetail}"></span>
</div>
<div>
    상품등록일 : <span th:text="${itemDto.regTime}"></span>
</div>
<div>
    상품가격 : <span th:text="${itemDto.price}"></span>
</div>
</body>
</html>
```
![image](https://user-images.githubusercontent.com/83503188/166110516-075f4f89-6162-4363-8a53-4f4cf2dae8f6.png)


### th:each

```java
@GetMapping("/ex03")
    public String test03(Model model) {

        List<ItemDto> itemDtoList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            ItemDto itemDto = new ItemDto();
            itemDto.setItemDetail("상품 상세 설명");
            itemDto.setItemNm("테스트 상품1");
            itemDto.setPrice(10000);
            itemDto.setRegTime(LocalDateTime.now());

            itemDtoList.add(itemDto);
        }
        model.addAttribute("itemDtoList", itemDtoList);
        return "thymeleafEx/thymeleafEx03";

    }
```
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<table border="1">
    <thead>
    <tr>
        <td>순번</td>
        <td>상품명</td>
        <td>상품설명</td>
        <td>가격</td>
        <td>상품등록일</td>
    </tr>
    </thead>
    <tbody>
        <tr th:each="itemDto, status: ${itemDtoList}">
            <td th:text="${status.index}"></td>
            <td th:text="${itemDto.itemNm}"></td>
            <td th:text="${itemDto.itemDetail}"></td>
            <td th:text="${itemDto.price}"></td>
            <td th:text="${itemDto.regTime}"></td>

        </tr>
    </tbody>
</table>

</body>
</html>
```

![image](https://user-images.githubusercontent.com/83503188/166110533-b24def1b-26f2-49b2-95d0-ae7b9424c883.png)

### th:if, th:unless

```java
@GetMapping("/ex04")
    public String test04(Model model) {
        List<ItemDto> itemDtoList = new ArrayList<>();

        for(int i=1;i<=10;i++){

            ItemDto itemDto = new ItemDto();
            itemDto.setItemDetail("상품 상세 설명"+i);
            itemDto.setItemNm("테스트 상품" + i);
            itemDto.setPrice(1000*i);
            itemDto.setRegTime(LocalDateTime.now());

            itemDtoList.add(itemDto);
        }

        model.addAttribute("itemDtoList", itemDtoList);
        return "thymeleafEx/thymeleafEx04";
    }
```
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<table border="1">
    <thead>
    <tr>
        <td>순번</td>
        <td>상품명</td>
        <td>상품설명</td>
        <td>가격</td>
        <td>상품등록일</td>
    </tr>
    </thead>
    <tbody>
    <tr th:each="itemDto, status: ${itemDtoList}">
        <td th:if="${status.even}" th:text="짝수"></td>
        <td th:unless="${status.even}" th:text="홀수"></td>
        <td th:text="${itemDto.itemNm}"></td>
        <td th:text="${itemDto.itemDetail}"></td>
        <td th:text="${itemDto.price}"></td>
        <td th:text="${itemDto.regTime}"></td>
    </tr>
    </tbody>
</table>
</body>
</html>
```
![image](https://user-images.githubusercontent.com/83503188/166110635-de8fb693-ff02-4def-9413-d4c9a3d9a7c5.png)

- status에는 현재 반복에 대한 정보가 존재, 인덱스가 짝수일 경우 status.even은 true -> 현재 인덱스가 짝수라면 순번에 짝수를 출력
- 현재 인덱스가 짝수가 아닐 경우, 홀수를 출력

### th:switch

```java
@GetMapping("/ex05")
    public String test05(Model model) {
        List<ItemDto> itemDtoList = new ArrayList<>();

        for(int i=1;i<=10;i++){

            ItemDto itemDto = new ItemDto();
            itemDto.setItemDetail("상품 상세 설명"+i);
            itemDto.setItemNm("테스트 상품" + i);
            itemDto.setPrice(1000*i);
            itemDto.setRegTime(LocalDateTime.now());

            itemDtoList.add(itemDto);
        }

        model.addAttribute("itemDtoList", itemDtoList);
        return "thymeleafEx/thymeleafEx05";
    }
```

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<table border="1">
    <thead>
    <tr>
        <td>순번</td>
        <td>상품명</td>
        <td>상품설명</td>
        <td>가격</td>
        <td>상품등록일</td>
    </tr>
    </thead>
    <tbody>
    <tr th:each="itemDto, status: ${itemDtoList}">
        <td th:switch="${status.even}">
            <span th:case="true">짝수</span>
            <span th:case="false">홀수</span>
        </td>
        <td th:text="${itemDto.itemNm}"></td>
        <td th:text="${itemDto.itemDetail}"></td>
        <td th:text="${itemDto.price}"></td>
        <td th:text="${itemDto.regTime}"></td>
    </tr>
    </tbody>
</table>
</body>
</html>
```
![image](https://user-images.githubusercontent.com/83503188/166110635-de8fb693-ff02-4def-9413-d4c9a3d9a7c5.png)


### th:href

- 링크를 처리하는 문법

```java
@GetMapping(value = "/ex06")
    public String test06(){
        return "thymeleafEx/thymeleafEx06";
    }

```

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h1>Thymeleaf 링크처리 예제 페이지</h1>
<div>
    <a th:href="@{/thymeleaf/ex01}">예제1 페이지 이동</a>
</div>
<div>
    <a th:href="@{https://www.thymeleaf.org/}">thymeleaf 공식 페이지 이동</a>
</div>
</body>
</html>
```

- 스프링 부트에서는 애플리케이션의 루트가 "/", 만약 애플리케이션 루트가 "/shop"으로 지정했다면 html 파일에 생성되는 이동 경로는 "/shop/thymeleaf/**"

```java
@GetMapping(value = "/ex07")
    public String test07(String param1, String param2, Model model){
        model.addAttribute("param1", param1);
        model.addAttribute("param2", param2);
        return "thymeleafEx/thymeleafEx06";
    }
```

- 해당 링크로 이동 시 파라미터값을 전달해야 하는 경우

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <h1>Thymeleaf 링크처리 예제 페이지</h1>
    <div>
        <a th:href="@{/thymeleaf/ex01}">예제1 페이지 이동</a>
    </div>
    <div>
        <a th:href="@{https://www.thymeleaf.org/}">thymeleaf 공식 페이지 이동</a>
    </div>
    <div>
        <a th:href="@{/thymeleaf/ex07(param1 = '파라미터 데이터1', param2 = '파라미터 데이터2')}">thymeleaf 파라미터 전달</a>
    </div>
</body>
</html>
```

```java
@GetMapping(value = "/ex07")
    public String test07(String param1, String param2, Model model){
        model.addAttribute("param1", param1);
        model.addAttribute("param2", param2);
        return "thymeleafEx/thymeleafEx07";
    }
```

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h1>파라미터 전달 예제</h1>
<div th:text="${param1}"></div>
<div th:text="${param2}"></div>
</body>
</html>
```
![image](https://user-images.githubusercontent.com/83503188/166111137-2be354e6-03b8-4e76-b962-09518a122365.png)
![image](https://user-images.githubusercontent.com/83503188/166111145-7543351a-b039-4d72-b874-3b084c1b09a0.png)


### Thymeleaf 페이지 레이아웃

- header, footer, menu 등 공통적인 페이지 구성 요소 처리

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">

<div th:fragment="header">
    header 영역 입니다.
</div>

</html>
```

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">

<div th:fragment="footer">
    footer 영역 입니다.
</div>

</html>
```

```html
<!DOCTYPE html>
<html xmlns:th="http://thymeleaf.org"
      xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"> <!-- 1) -->

<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>

<body>

<div th:replace="fragments/header::header"></div> <!-- 2) -->

<div layout:fragment="content"> <!-- 3) -->

</div>

<div th:replace="fragments/footer::footer"></div> <!-- 4) -->
</body>

</html>
```
1. layout 기능을 사용하기 위해 html 태그에 네임스페이스를 추가
2. th:replace 속성은 해당 속성이 선언된 html 태그를 다른 html 파일로 치환, fragments 폴더 아래의 header.html 파일의 "th:fragment=header" 영역을 가지고 온다.
3. layout에서 변경되는 영역을 fragment로 설정
4. header 영역과 마찬가지로 fragements 폴더 아래의 footer.html 파일의 "th:fragment=footer" 영역을 가지고 온다.

```html
<!DOCTYPE html>
<html xmlns:th="http://thymeleaf.org"
      xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
      layout:decorate="~{layouts/layout1}"> <!-- 1) -->

<div layout:fragment="content"> <!-- 2) -->
    본문 영역 입니다.
</div>

</html>
```
1. layouts 폴더 아래에 있는 layout1.html을 적용하기 위해서 네임스페이스를 추가
2. layout1.html 파일의 <div layout:fragment="content"> 영역에 들어가는 영역

