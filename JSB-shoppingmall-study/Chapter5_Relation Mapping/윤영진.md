# Chapter 5: 연관 관계 매핑

엔티티들은 대부분 다른 엔티티와 연관 관계를 맺고 있다. JPA에서는 엔티티에 연관 관계를 매핑해두고 필요할 때 해당 엔티티와 연관된 엔티티를 사용하여 좀 더 객체지향적으로 프로그래밍할 수 있도록 도와준다.

- [x] 일대일(1:1): `@OneToOne`
- [x] 일대다(1:N): `@OneToMany`
- [x] 다대일(N:1): `@ManyToOne`
- [x] 다대다(N:N): `@ManyToMany`

엔티티를 매핑할 때는 방향성을 고려해야 한다. 테이블에서 관계는 항상 양방향이지만, 객체에서는 단방향과 양방햐이 존재한다. 

## 연관 관계 매핑 종류

### 일대일 단방향 매핑하기

```java
@Table(name = "cart")
@Getter
@Entity
public class Cart {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY) // 1)
    @JoinColumn(name = "member_id") // 2)
    private Member member;
    
}
```

1. `@OneToOne` 어노테이션을 이용해 멤버 엔티티와 일대일 매핑
2. `@JoinColumn` 어노테이션을 이용해 매핑할 외래키(FK)를 지정한다. name 속성에는 매핑할 외래키의 이름을 설정한다. name을 명시하지 않으면 JPA가 알아서 ID를 찾지만 컬럼명이 원하는 대로 생성되지 않을 수 있기 때문에 직접 지정한다.

### 다대일 단방향 매핑하기

![image](https://user-images.githubusercontent.com/83503188/168439607-3654c05e-18f6-4d58-8c2d-16095d95963b.png)


하나의 장바구니(Cart)에는 여러 개의 상품(CartItem)이 들어갈 수 있다. 또한 같은 상품을 여러 개 주문할 수도 있으므로 몇 개를 담아 줄 것인지도 설정해줘야 한다.

```java
@Entity
@Getter
@Table(name = "cart_item")
public class CartItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart; // 1)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item; // 2)
    
    private int count; // 3)
    
}

```

1. 하나의 장바구니에는 여러 개의 상품을 담을 수 있으므로 `@ManytoIne` 어노테이션을 이용하여 다대일 관계로 매핑
2. 장바구니에 담을 상품의 정보를 알아야 하므로 상품 엔티티를 매핑해준다. 하나의 상품은 여러 장바구니의 장바구니 상품으로 담길 수 있으므로 마찬가지로 `@ManyToone` 어노테이션을 이용하여 다대일 관계로 매핑한다.
3. 같은 상품을 장바구니에 몇 개 담을지 저장한다.

### 다대일/일대다 양방향 매핑하기

![image](https://user-images.githubusercontent.com/83503188/168440596-3dc81774-9455-4023-bd6e-7b591f6ced15.png)

```java
@Getter
@Table(name = "order_item")
@Entity
public class OrderItem extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item; // 1)
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order; // 2)
    
    private int orderPrice;
    
    private int count;
    
}
```

1. 하나의 상품은 여러 주문 상품으로 들어갈 수 있으므로 주문 상품 기준으로 다대일 단방향 매핑을 설정한다.
2. 한 번의 주문에 여러 개의 상품을 주문할 수 있으므로 주문 상품 엔티티와 주문 엔티티를 다대일 단방향 매핑을 설정한다.




```java
@Getter
@Table(name = "orders")
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order") // 1)
    private List<OrderItem> orderItems = new ArrayList<>(); // 2)
    
}
```

1. 주문 상품 엔티티와 일대다 매핑을 한다. 외래키(order_id)가 order_item 테이블에 있으므로 연관 관계의 주인은 OrderItem 엔티티, Order 엔티티가 주인이 아니므로 `mappedBy` 속성으로 연관 관계의 주인을 설정한다. 속성의 값으로 `order`를 적은 이유는 OrderItem에 있는 Order에 의해 관리된다는 의미로 해석하면 된다. 
2. 하나의 주문이 여러 개의 주문 상품을 갖으므로 List 자료형을 사용해서 매핑한다.

테이블은 외래키 하나로 양방향 조회가 가능하는데 반해 엔티티는 양방향 연관 관계로 설정하면 객체의 참조는 둘인데 외래키는 하나이므로 둘 중 누가 외래키를 관리할지를 정해야 한다.

- 연관 관계의 주인은 외래키가 있는 곳으로 설정
- 연관 관계의 주인이 외래키를 괸리(등록, 수정, 삭제)
- 주인이 아닌 쪽은 연관 관계 매핑 시 mappedBy 속성 값으로 연관 관계 주인을 설정
- 주인이 아닌 쪽은 읽기만 가능

### 다대다 매핑하기

다대다 매핑은 실무에서는 사용하지 않는 매핑 관계, 관계형 데이터베이스는 정규화된 테이블 2개로 다대다를 표현할 수 없다. 따라서 연결 테이블을 생성해서 다대다 관계를 일대다, 다대일 관계로 풀어낸다.

다대다 매핑을 사용하지 않는 이유는 연결 테이블에는 컬럼을 추가할 수 없기 때문이다.(연결 테이블에는 2개의 아이디만 존재) 연결 테이블에는 조인 컬럼뿐 아니라 추가 컬럼들이 필요한 경우가 많다. 또한 엔티티를 조회할 때 member 엔티티에서 item을 조회하면 중간 테이블이 있기 때문에 어떤 쿼리문이 실행될지 예측하기도 쉽지 않다. 

따라서 연결 테이블용 엔티티를 하나 생성한 후 일대다 다대일 관계로 매핑을 하면 된다.

## 영속성 전이 

영속성 전이 즉, 'cascade'의 사전적 정의는 '작은 폭포', '폭포처럼 흐르다'라는 뜻이 있다. 영속성 전이란 엔티티의 상태를 변경할 때 해강 엔티티와 연관된 엔티티의 상태 변화를 전파시키는 옵션이다. 
이때 부모는 일(One)에 해당하고 자식은 다(Many)에 해당한다. 예를 들어 Order(일)가 삭제되었을 때 해당 엔티티와 연관되어 있는 OrderItem(다)가 함께 삭제 되거나, Order(일)를 저장 할 때 Order(일)에 담겨있던 OrderItem(다)를 한번에 저장할 수 있다. 


| CASCADE 종류  | 설명                                      |
|-------------|-----------------------------------------|
| **PERSIST** | 부모 엔티티가 영속화될 때 자식 엔티티도 영속화              |
| MERGE       | 부모 엔티티가 병합될 때 자식 엔티티도 병합                |
| REMOVE      | 부모 엔티티가 삭제될 때 자식 엔티티도 삭제                |
| REFRESH     | 부모 엔티티가 refresh되면 자식 엔티티도 refresh       |
| DETACH      | 부모 엔티티가 detach되면 자식 엔티티도 detach 상태로 변경  |
| **ALL**     | 부모 엔티티의 영속성 상태 변화를 자식 엔티티에 모두 전이        |




```java
    @Test
    @DisplayName("영속성 전이 테스트")
    @Transactional
    public void 영속성_전이_테스트() {
        Order order = new Order();

        for (int i = 0; i < 3; i++) {
            Item item = createItem();
            itemRepository.save(item);
            OrderItem orderItem = OrderItem.builder()
                    .item(item)
                    .count(10)
                    .orderPrice(1000)
                    .order(order)
                    .build();

            order.getOrderItems().add(orderItem);
        }

        orderRepository.save(order);
        em.clear(); // 1)

        Order savedOrder = orderRepository.findById(order.getId())
                .orElseThrow(EntityNotFoundException::new);
        
        assertEquals(3, savedOrder.getOrderItems().size());

    }
```

1. 영속성 컨텍스트를 초기화했기 때문에 데이터베이스에서 주문 엔티티를 조회한다. 만약 clear하지 않았으면 1차 캐시에 해당 엔티티가 남아있기 때문에 데이터베이스에서 조회하지 않고 1차 캐시에서 가져오기 때문에 SELECT QUERY를 확인할 수 없다.

![image](https://user-images.githubusercontent.com/83503188/168464312-3ee0bf6b-20b8-49c5-b8ca-ecef393903a6.png)

Order 엔티티만 save해줬는데 Order에 속해 있던 OrderItem까지 같이 insert 되는 것을 확인할 수 있다. 


### 고아 객체 제거하기

부모 엔티티와 연관 관계가 끊어진 자식 엔티티를 고아 객체라고 한다. 영속성 전이 기능과 같이 사용하면 부모 엔티티를 통해서 자식의 생명 주기를 관리할 수 있다. 

고아 객체 제거 기능을 사용하기 위해서 주의사항이 있다. 고아 객체 제거 기능은 참조하는 곳이 하나일 때만 사용해야 한다. 다른 곳에서도 참조하고 있는 엔티티인데 삭제하면 문제가 생길 수 있다. OrderItem 엔티티를 Order 엔티티가 아닌 다른 곳에서 사용하고 있다면 이 기능을 사용하면 안 된다.

```java
package dev.yoon.shop.domain.order.entity;

import dev.yoon.shop.domain.member.entity.Member;
import dev.yoon.shop.domain.order.constant.OrderStatus;
import dev.yoon.shop.domain.orderitem.entity.OrderItem;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Order {

    ...
    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();
}
```

```java
    @Test
    @DisplayName("고아객체 제거 테스트")
    @Transactional
    public void 고아객체_제거_테스트() {
        Order order = createOrder();
        order.getOrderItems().remove(0);
        em.flush();
    }
```

![image](https://user-images.githubusercontent.com/83503188/168464889-edecc030-b03c-4cc1-bce8-eeb05f2165b4.png)

flush()를 호출하면 orderItem을 삭제하는 remove 쿼리가 출력되는 것을 확인할 수 있다. 즉, 부모 엔티티와 연관 관계가 끊어졌기 때문에 고아 객체를 삭제하는 쿼리문이 실행되는 것이다.

Cascade 옵션 중 REMOVE 옵션과 헷갈리지 말자. Cascade REMOVE 옵션은 부모 엔티티가 삭제될 때 연관된 자식 엔티티도 함께 삭제되는 것이다. order를 삭제하면 order에 매핑되어 있던 orderItem이 함께 삭제

```java
    @Test
    @DisplayName("Cascade REMOVE 테스트")
    @Transactional
    public void Cascade_REMOVE_테스트() {
        Order order = createOrder();
        
        orderRepository.delete(order);
        em.flush();
    }
```

![image](https://user-images.githubusercontent.com/83503188/168465013-b6e5ea96-aba4-41c6-9cf9-0756e3ee6815.png)

## 지연 로딩 

연관된 엔티티를 사용할 때 조회하는 지연 로딩과 지연 로딩을 사용해야 하는 이유를 알아본다. 

즉시 로딩을 사용할 경우 OrderItem을 조회할 경우 order_item 테이블과 item, orders, member 테이블을 조인해서 모두 가지고 온다. 

일대일, 다대일로 매핑할 경우 기본 전략인 즉시 로딩을 통해 엔티티를 함께 가져온다. 심지어 OrderItem을 조회했을 뿐인데 OrderItem에 연관된 Order를 가져올 때 Order에 연관된 Member까지 가져오는 것을 확인할 수 있다. 지금 예제에서는 4개의 테이블을 조인해서 가지고 오지만,
실제 비즈니스를 하고 있다면 매핑되는 엔티티의 개수는 훨씬 많다. 그렇게 되면 개발자는 쿼리가 어떻게 실행될지 예측할 수 없게 된다. 

```java
    @Test
    @DisplayName("지연 로딩 테스트")
    @Transactional
    public void lazyLoadingTest(){
        Order order = this.createOrder();
        Long orderItemId = order.getOrderItems().get(0).getId();
        em.flush();
        em.clear();
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(EntityNotFoundException::new);
        System.out.println("Order class : " + orderItem.getOrder().getClass()); // 1)
        System.out.println("===========================");
        orderItem.getOrder().getOrderStatus(); // 2)
        System.out.println("===========================");
    }
```

1. Order 클래스 조회 결과가 HibernateProxy라고 출력된다. 지연 로딩으로 설정하면 실제 엔티티 대신에 프록시 객체를 넣어둔다.

![image](https://user-images.githubusercontent.com/83503188/168465630-7fa12a16-bde6-48bd-a38e-ca1037431755.png)

2. 프록시 객체는 실제로 사용되기 전까지 데이터 로딩을 하지 않고, 실제 사용 시점에 조회 쿼리문이 실행된다.

![image](https://user-images.githubusercontent.com/83503188/168465678-13029e7b-edf7-48c9-9f65-a6a435c90d91.png)

--- 

## Auditing을 이용한 엔티티 공통 속성 공통화

등록시간, 수정시간 멤버변수의 경우 대부분의 엔티티에서 사용되는 변수이다. 실제 서비스를 운영할 때는 보통 등록시간과 수정시간, 등록자, 수정자를 테이블에 넣어 놓고 활용을 한다. 그리고 데이터가 생성되거나 수정될 때 시간을 기록해주고, 어떤 사용자가 등록을 했는지 아이디를 남긴다.
이 컬럼들은 버그가 있거나 문의가 들어왔을 때 활용이 가능하다. 대용량 데이터를 업데이트했는데, 다시 업데이트를 해야 할 경우 변경된 대상을 찾을 때 활용할 수도 있다. 

Spring Data Jpa에서는 Auditing 기능을 제공하여 엔티티가 저장 또는 수정될 때 자동으로 등록일, 수정일, 등록자, 수정자를 입력해준다.
Audit의 사전적 정의가 '감시하다'인 것처럼 엔티티의 생성과 수정을 감시하고 있는 것이다. 이런 공통 멤버 변수들을 추상 클래스로 만들고, 해당 추상 클래스를 상속받는 형태로 사용한다.

현재 로그인한 사용자의 정보를 등록자와 수정자로 지정하기 위해서 AuditorAware 인터페이스를 구현한 클래스를 생성한다. 
```java
public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userId = "";
        if (authentication != null){
            userId = authentication.getName();
        }
        return Optional.of(userId);
    }
}
```

```java
@Configuration
@EnableJpaAuditing // 1)
public class AuditConfig {
    
    @Bean
    public AuditorAware<String> auditorProvider() { // 2)
        return new AuditorAwareImpl();
    }
}

```

1. JPA의 Auditing 기능을 활성화한다.
2. 등록자와 수정자를 처리해주는 AuditorAware을 빈으로 등록한다. 

보통 테이블에 등록일, 수정일, 등록자, 수정자를 모두 다 넣어주지만 어떤 테이블은 등록자, 수정자를 넣지 않는 테이블도 있을 수 있다. 그런 엔티티는 BaseTimeEntity만 상속받을 수 잇도록 BaseTimeEntity 클래스를 생성한다.

```java
@Test
@DisplayName("Auditing 테스트")
@WithMockUser(username = "yoon", roles = "USER") // 1)
public void auditingTest() {

        Member member = Member.builder()
        .email("dudwls143@gmail.com")
        .password(Password.builder()
        .value("password")
        .build())
        .build();
        memberRepository.save(member);

        em.flush();
        em.clear();

        Member member1 = memberRepository.findById(member.getId())
        .orElseThrow(EntityExistsException::new);

        System.out.println("register time : " + member1.getRegTime());
        System.out.println("update time : " + member1.getUpdateTime());
        System.out.println("create member : " + member1.getCreatedBy());
        System.out.println("modify member : " + member1.getModifiedBy());

        }
```

1. 스프링 시큐리티에서 제공하는 어노테이션으로 `@WithMockUser`에 지정한 사용자가 로그인한 상태라고 가정하고 테스트를 진행할 수 있다.

![image](https://user-images.githubusercontent.com/83503188/168466395-4c8d512a-5baf-4a96-8ded-1dc8fec7acbb.png)

