# Chapter5 강의 정리

-----------
### Mybatis ?
- 데이터베이스 관련된 자바 프레임워크
- Java함수를 SQL선언문과 연결 지어 사용
- Java클래스를 이용하여 SQL 결과를 받거나 SQL 선언문(insert,select,where,…)에서 사용할 인자를 전달한다.
<p align="center">
    <img src="https://user-images.githubusercontent.com/83503188/155977649-fc90ce36-a42f-4262-90ea-4e0ba112ae45.png" width="300">
</p>

### POJO(Plain Old Java Object) ?
- 단순한 자바 오브젝트, 객체 지향적인 원리에 충실하면서 환경과 기술에 종속되지 않고 필요에 따라 재활용될 수 있는 방식으로 설계된 오브젝트

### Database를 사용하는 이유 ?
1.	Java코드로 작성한 List, HashMap등은 memory에 저장된다.
2.	여러 서버 프로세스가 같은 기능을 하면서 Data를 공유해야 한다.

> Database의 table에서 하나의 Row를 Java기준에서 class
> MyBatis에서는 SQL선언문을 xml형태로 저장, xml파일을 application실행 시 불러들이고, 미리 정의된 Java코드 상의 함수와 연결을 한다.

### DAO(Data Access Object) ?
- 데이터를 주고 받는 기능을 위한 객체
- 다른 Spring 앱과 소통하기 위한 부분

### DTO(Data Transfer Object) ?
- 실제 데이터를 담기 위한 객체

### 관계형 데이터베이스의 한계
- Primary Key와 Foreign Key(Primary key를 가리키는 키)를 통해 각 테이블의 연관성을 주입 
- 객체에 Foreign Key를 필드로 가져야 하는 한계를 가짐 -> 객체 입장에서 아무 쓸모없는 데이터가 존재 
- 관계형 데이터베이스에서 사용하는 자료의 형태가 객체 지향 관점에서 맞지 않아서 생기는 간극

### ORM(Object Relational Mapping)
- 관계형 데이터베이스의 한계를 통해 등장 
- 관계형 데이터를 객체로 표현하는 프로그래밍 기법
```
public class Food {
	private String name;
	private int price;
	private int count;
}
```
```
public class Order {
	private Food orderFood;
	private int orderCount;
}
```

### JPA(Java Persistence API)
- JPA는 자바 진영에서 ORM 기술 표준으로 사용되는 인터페이스의 모음
- JPA 자체는 관계형 데이터를 객체로 표기히는 기능 뿐 -> 실제적으로 구현된것이 아니라 구현된 클래스와 매핑을 해주기 위해 사용되는 프레임워크
- 자바에서 ORM을 사용하기 위해서는 Hibernae(=ORM 프레임워크, JPA를 구현한 대표적인 오픈소스)를 사용

>Service : 비즈니스 로직,
>Repository : 데이터 엑세스,
>Controller : front gate
