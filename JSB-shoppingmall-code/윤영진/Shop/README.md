# Chapter 2: Spring Data JPA

## JPA(`Java Persistence API`) 는 자바 ORM 기술에 대한 API 표준

> ORM?
> 
> Object Relational Mapping의 약자로 객체와 관계형 데이터베이스를 매핑해주는 것

## 관계형 데이터베이스의 문제점

- [x] 개발자가 SQL을 매핑하는 역할을 반복해야 한다.
- [x] 겍체와 관계형 데이터베이스의 패러다임 불일치
  - 패러다임 불일치 문제를 해결하기 위해 나온 기술이 **ORM**
  - 객체는 객체지향적으로, 데이터베이스는 데이터베이스 대로 설계하고 **ORM**이 중간에서 2개를 매핑하는 역할 
  - JPA는 위에서 설명한 ORM 기술의 표준 명세로 자바에서 제공하는 API -> JPA는 인터페이스고 이를 구현한 대표적인 구현체가 Hibernate, EclipseLink, DataNucleus, OpenJpa, TopLink,...

### JPA 사용 시 장점

- [x] 특정 데이터베이스에 종속되지 않음
- [x] 객체지향적 프로그래밍
- [x] 생산성 향상

### JPA 사용 시 단점

- [x] 복잡한 쿼리 처리
  - JPA에서는 Native SQL을 통해 기존의 SQL문을 사용할 수 있지만 그러면 특정 데이터베이스에 종속된다는 단점이 생김
  - 이를 보완하기 위해서 SQL과 유사한 기술인 JPQL을 지원
- [x] 성능 저하 위험
  - 자동으로 생성되는 쿼리가 많기 때문에 개발자가 의도하지 않는 쿼리로 인해 성능이 저하되기도 함
- [x] 학습 시간

## JPA 동작 방식

![image](https://user-images.githubusercontent.com/83503188/164912823-1497e159-706f-4a3e-a552-ad5b72116e9d.png)


### 엔티티 
- 엔티티란 데이터 베이스의 테이블에 대응하는 클래스
- `@Entity`가 붙은 클래스는 JPA에서 관리하며 엔티티라고 함

### 엔티티 매니저 팩토리

- 엔티티 매니저 인스턴스를 관리하는 주체

### 엔티티 매니저
- 영속성 컨텍스트에 접근하여 엔티티에 대한 데이터베이스 작업을 제공
- 엔티티 매니저의 메소드
  - [x] find(): 영속성 컨텍스트에서 엔티티를 검색하고 영속성 컨텍스트에 없을 경우 데이터베이스에서 데이터를 찾아 영속성 컨텍스트에 저장
  - [x] persist(): 엔티티를 영속성 컨텍스트에 저장
  - [x] remove(): 엔티티 클래스를 영속성 컨텍스트에서 삭제
  - [x] flush(): 영속성 컨텍스트에 저장된 내용을 데이터베이스에 반영
  > 영속성 컨텍스트?
  > 
  > 영속성 컨텍스트는 논리적인 개념으로 `엔티티를 영구 저장하는 환경`이라는 뜻

#### 엔티티 생명주기 

![image](https://user-images.githubusercontent.com/83503188/164913028-5d0e9959-b813-423f-8e7f-03b0a87ab5b8.png)

- [x] 비영속(new): new 키워드를 통해 생성된 상태로 영속성 컨텍스트와 관련이 없는 상태
- [x] 영속(managed)
  - 엔티티가 영속성 컨텍스트에 저장된 상태로 영속성 컨텍스트에 의해 관리되는 상태
  - 영속 상태에서 데이터베이스에 저장되지 않으며, 트랜잭션 커밋 시점에 데이터베이스에 반영
- [x] 준영속(detached): 영속성 컨텍스트에 엔티티가 저장되었다가 분리된 상태
- [x] 삭제(removed): 영속성 컨텍스트와 데이터베이스에서 삭제된 상태

```java

/**
 * 영속성 컨텍스트에 저장할 상품 엔티티 생성
 */
Item item = new Item();
item.setItemNm("테스트 상품");

/**
 * 엔티티 매니저 팩토리로부터 엔티티 매니저를 생성
 */
EntityManager em = entityManagerFactory.createEntityManager();

/**
 * 엔티티 매니저는 데이터 변경 시 데이터의 무결성을 위해 반드시 트랜잭션을 시작해야 함
 * 여기서의 트랜잭션도 데이터베이스의 트랜잭션과 같은 의미 -> 데이터베이스의 상태를 변화시키기 해서 수행하는 작업의 단위
 */
EntityTransaction transaction = em.getTransaction();

/**
 * 생성한 엔티티가 영속성 컨텍스트에 저장 -> 아직 INSERT SQL X
 */
em.persist(item);

/**
 * 트랜잭션을 데이터베이스에 반영
 * 이때 영속성 컨텍스트에 저장된 상품 정보가 데이터베이스 INSERT 되면서 반영
 */
transaction.commit();

/**
 * 자원 반환
 */
        em.close();
emf.close();

```

#### 영속성 컨텍스트 사용 시 이점
> 버퍼링, 캐싱 가능

- [x] 1차 캐시
  - 영속성 컨텍스트에는 1차 캐시가 존재하며 Map<KEY, VALUE>로 저장
  - find() 메소드 호출 시 영속성 컨텍스트의 1차 캐시를 조회하고 엔티티가 존재할 경우 해당 엔티티를 반환하고, 엔티티가 없으면 데이터베이스에서 조회 후 1차 캐시에 저장 및 반환

![image](https://user-images.githubusercontent.com/83503188/164913465-67d24fa0-19d0-4ac1-a019-2aa8896d5d2f.png)

- [x] 동일성 보장
  - 하나의 트랜잭션에서 같은 키값으로 영속성 컨텍스트에 저장된 엔티티 조회 시 같은 엔티티 조회를 보장

- [x] 트랜잭션을 지원하는 쓰기 지연
  - 영속성 컨텍스트에는 쓰기 지연 SQL 저장소가 존재 
  - persist() 메소드를 호출하면 1차 캐시에 저장되는 것과 동시에 쓰기 지연 SQL문이 저장
  - SQL을 쌓아두고 트랜잭션을 커밋하는 시점에 저장된 SQL문들이 flush되면서 데이터베이스에 반영

![image](https://user-images.githubusercontent.com/83503188/164913662-0abbde07-ce1d-4b5e-a322-1ed7f0e63611.png)

![image](https://user-images.githubusercontent.com/83503188/164913683-38fc3a7e-c30a-4827-8c74-a59f6139851f.png)

- [x] 변경 감지 
  - JPA는 1차 캐시에 데이터베이스에서 처음 불러온 엔티티의 스냅샷 값을 갖고 있음
  - 1차 캐시에 저장된 엔티티와 스냅샷을 비교 후 변경 내용이 있다면 UPDATE SQL 문을 쓰기 지연 SQL 저장소에 담아둠
  - 데이터베이스에 커밋 시점에 변경 내용을 자동으로 반영 -> **따로 update문을 호출할 필요 X**
![image](https://user-images.githubusercontent.com/83503188/164913769-ce0a24f0-64d5-4192-a9b0-83c2cf75fd3f.png)


> CLOB와 BLOB의 의미
> 
> CLOB: 사이즈가 큰 데이터를 외부 파일로 저장하기 위한 데이터 타입 -> 문자형 대용량 파일을 저장하는데 사용하는 데이터 타입
> 
> BLOB: 바이너리 데이터를 DB 외부에 저장하기 위한 타입 -> 이미지, 사운드, 비디오 같은 멀티미디어 데이터를 다룰 때 사용

### 쿼리 메소드

- 애플리케이션 개발에 있어서 데이터를 조회하는 기능은 필수
- 쿼리 메소드는 스프링 데이터 JPA에서 제공하는 핵심 기능 중 하나로 Repository 인터페이스에 간단한 네이밍 룰을 이용하여 메소드를 작성하면 쿼리를 실행할 수 있다
- [x] 조회
  - `find + (엔티티 이름) + By + 변수이름`
- [x] 내림차순 조회
  - `find + (엔티티 이름) + By + 변수이름 + LessThan + OrderBy + 속성명 + Desc 키워드`
- [x] 오름차순 조회
  - `find + (엔티티 이름) + By + 변수이름 + LessThan + OrderBy + 속성명 + Asc 키워드`


### `@Query` 어노테이션

- 쿼리 메소드를 사용하면 조건이 많아질 때 이름이 너무 길어지는 경우가 발생
- 쿼리 메소드의 경우 간단한 쿼리를 처리할 때는 유용하지만 복잡한 쿼리를 다루기에는 적합하지 않다. 
- 이를 해결하기 위해 Spring Data Jpa에서 제공하는 `@Query` 어노테이션을 이용하면 SQL과 유사한 JPQL(Java Persistence Query Language)이라는 객체지향 쿼리 언어를 통해 복잡한 쿼리 처리 가능
- 만약 기존의 데이터베이스에서 사용하던 쿼리를 그대로 사용해야 할 때는 `@Query`의 nativeQuery 속성을 사용하면 기존 쿼리를 그대로 활용 가능

### QueryDsl

- `@Query` 어노테이션 안에 JPQL 문법으로 문자열을 입력하기 때문에 잘못 입력하면 컴파일 시점에 에러를 발견할 수 없다는 단점이 존재
- 이를 보완하는 방법으로 Querydsl을 사용 -> ? 
- QueryDsl은 JPQL을 코드로 작성할 수 있도록 도와주는 빌더 API
- QueryDsl은 소스코드로 SQL문을 문자열이 아닌 코드로 작성하기 때문에 컴파일러의 도움을 받을 수 있다.
- **동적 쿼리**를 생성하는데 있어서 가장 큰 장점을 가짐

#### JPAQuery 데이터 반환 메소드

- [x] List<T> fetch(): 조회 결과 리스트 반환
- [x] T fetchOne(): 조회 대상이 1건인 경우 제네릭으로 지정한 타입 반환
- [x] T fetchFirst(): 조회 대상 중 1건만 반환
- [x] List<T> fetch(): 조회 대상 개수 반환
- [x] QueryResult<T> fetchResults(): 조회한 리스트와 전체 개수를 포함한 QueryResults 반환

#### QueryDslPredicateExecutor
> Predicate? 
> 
> '이 조건이 맞다'고 판단하는 근거를 함수로 제공하는 것

- Repository에 Predicate를 파라미터로 전달하기 위해서 QueryDslPredicateExecutor 인터페이스를 상속
- QueryDslPredicateExecutor의 메소드
  - [x] long count(Predicate): 조건에 맞는 데이터의 총 개수 반환
  - [x] boolean exists(Predicate): 조건에 맞는 데이터 존재 여부 반환
  - [x] Iterable findAll(Predicate): 조건에 맞는 모든 데이터 반환
  - [x] Page<T> findAll(Predicate, Pageable): 조건에 맞는 페이지 데이터 반환
  - [x] Iterable findAll(Predicate, Sort): 조건에 맞는 정렬된 데이터 반환
  - [x] T findOne(Predicate): 조건에 맞는 데이터 1개 반환






