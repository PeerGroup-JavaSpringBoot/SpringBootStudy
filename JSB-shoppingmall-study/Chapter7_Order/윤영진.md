# Chapter 7: 주문

## 주문 기능 구현하기

고객이 상품을 주문하면 현재 상품의 재고에서 주문 수량만큼 재고를 감소시켜야 한다. 고객이 주문을 했는데 실제 재고가 없다면 배송을 하지 못하고 결품 처리가 되기 때문에 주문 수량만큼 상품의 재고를 감소시켜야 한다. 
또한 주문 수량이 현재 재고 수보다 클 경우 주문이 되지 않도록 구현해야 한다.

```java
public class OutOfStockException extends BusinessException {

    public OutOfStockException(String msg) {
        super(msg);
    }
}

```
상품의 주문 수량보다 재고의 수가 적을 때 발생시킬 exception을 정의한다.

```java
public class Item extends BaseEntity {

    ...
    
    public void removeStock(int stockNumber) {
        int restStock = this.stockNumber - stockNumber;

        if (restStock < 0) {
            throw new OutOfStockException(ErrorCode.OUT_OF_STOCK.getMessage() + String.format("(현재 재고 수량: %d)", this.stockNumber));
        }
        if (restStock == 0) {
            this.itemSellStatus = ItemSellStatus.SOLD_OUT;
        }
        this.stockNumber = restStock;
    }
}

```
엔티티 클래스 안에 비즈니스로직을 메소드로 작성하면 코드의 재사용과 데이터의 변경 포인트를 한군데로 모을 수 있다는 장점이 있다.


```java
public class OrderItem extends BaseEntity {

    ...

    public static OrderItem createOrderItem(Item item, int count) {
        /**
         * 주문 상품을 생성한다는 것은 주문 수량만큼 상품의 재고를 차감하는 것
         */
        item.removeStock(count); // 1)

        return OrderItem.builder()
                .orderPrice(item.getPrice())
                .count(count)
                .item(item)
                .build();
    }

    public int getTotalPrice() {
        return this.getOrderPrice() * this.getCount();
    }

}

```

주문할 상품과 주문 수량을 통해 OrderItem 객체를 만드는 메소드를 작성한다. 

1. 주문 상품을 생성한다는 것은 주문 수량만큼 상품의 재고를 차감하는 것

```java
public class Order  extends BaseEntity {

    ...

    public static Order createOrder(Member member, List<OrderItem> orderItems) {

        return Order.builder()
                .orderStatus(OrderStatus.ORDER)
                .member(member)
                .orderItems(orderItems)
                .build();
        
    }
    public int getTotalPrice() {
        
        int totalPrice = this.orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice).sum();
        return totalPrice;
        
    }
}

```

## 주문 이력 조회하기

주문 정보를 담은 OrderHistDto를 정의하고 중첩클래스로 조회한 주문 데이터를 화면에 보낼 때 사용할 OrderItemHistDto를 정의

```java
@Getter @Setter
public class OrderHistDto {

    private Long orderId;
    private LocalDateTime orderTime;
    private OrderStatus orderStatus;
    private List<OrderItemHistDto> orderItemHistDtos = new ArrayList<>();

    @Builder
    public OrderHistDto(Long orderId, LocalDateTime orderTime, OrderStatus orderStatus,
                         List<OrderItemHistDto> orderItemHistDtos) {
        
        this.orderId = orderId;
        this.orderTime = orderTime;
        this.orderStatus = orderStatus;
        this.orderItemHistDtos = orderItemHistDtos;
        
    }

    public static OrderHistDto of(Order order) {
        
        return OrderHistDto.builder()
                .orderId(order.getId())
                .orderTime(order.getOrderDate())
                .orderStatus(order.getOrderStatus())
                .build();
        
    }

    @Getter @Setter
    public static class OrderItemHistDto {
        private String itemName;
        private int count;
        private int orderPrice;
        private String imageUrl;

        @Builder
        public OrderItemHistDto(String itemName, Integer count, Integer orderPrice, String imageUrl) {
            this.itemName = itemName;
            this.count = count;
            this.orderPrice = orderPrice;
            this.imageUrl = imageUrl;
        }

        public static OrderItemHistDto of(OrderItem orderItem, ItemImage itemImage) {

            return OrderItemHistDto.builder()
                    .itemName(orderItem.getItem().getItemNm())
                    .count(orderItem.getCount())
                    .imageUrl(itemImage.getImgUrl())
                    .orderPrice(orderItem.getItem().getPrice())
                    .build();
            
        }
    }

}
```

### OrderRepository

@Query어노테이션을 이용하여 주문 이력을 조회하는 쿼리를 작성한다. 조회 조건이 복잡하지 않은 경우 QueryDsl을 사용하지 않고 @Query 어노테이션을 사용하여 구현한다.

```java
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o join fetch o.member " +
            "where o.member.email =: email " +
            "order by o.orderDate desc ")
    List<Order> findOrdersWithMember(@Param("email") String email, Pageable pageable);

    @Query("select count(o) from Order o " +
            "where o.member.email =: email")
    Long countOrder(@Param("email") String email);
    
}

```

### ItemImageRepository

상품의 대표 이미지를 찾는 쿼리 메소드를 추가한다. 

```java
public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {
    
    ...
    
    Optional<ItemImage> findByItemIdAndIsRepImg(Long itemId, boolean isRepImage);
}
```

### default_batch_fetch_size

in 쿼리를 통해 DB에서 데이터를 가져올 때 배치 단위로 가져오는 옵션

```java

public class OrderHistService {

    private final OrderService orderService;
    private final ItemImageService itemImageService;

    public Page<OrderHistDto> getOrderList(String email, Pageable pageable) {

        // 1. 회원 및 주문 데이터 조회
        Page<Order> orders = orderService.getOrdersWithMember(email, pageable);

        // 2. 주문 데이터 dto 변환
        List<OrderHistDto> orderHistDtos = new ArrayList<>();

        for (Order order : orders) {

            // 3. 주문 정보 dto 생성
            OrderHistDto orderHistDto = OrderHistDto.of(order);

            // 4. 주문 상품 dto 리스트 생성
            List<OrderHistDto.OrderItemHistDto> orderItemHistDtos = getOrderItemHistDtos(order);
            orderHistDto.setOrderItemHistDtos(orderItemHistDtos);

            orderHistDtos.add(orderHistDto);

        }

        return new PageImpl<OrderHistDto>(orderHistDtos, pageable, orders.getTotalElements());
    }

    private List<OrderHistDto.OrderItemHistDto> getOrderItemHistDtos(Order order) {

        List<OrderItem> orderItems = order.getOrderItems();

        List<OrderHistDto.OrderItemHistDto> orderItemHistDtos = orderItems.stream().map(orderItem -> {
            ItemImage itemImage = itemImageService.getItemImageByItemIdAndIsRepImg(orderItem.getItem().getId(), true);
            return OrderHistDto.OrderItemHistDto.of(orderItem, itemImage);
        }).collect(Collectors.toList());
        return orderItemHistDtos;

    }
}
```

- Order에서 OrderItem을 가져올 때 for문 만큼 쿼리문이 나가는 `N+1 문제`를 해결하기 위해 default-batch-size 옵션을 활성화하여 해결한다. 

```java
 ...
  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        default_batch_fetch_size: 100
        ...
```

## 주문 취소하기 

주문 취소 시 상품의 재고를 증가시키기 위해서 Item 클래스에 addStock 메소드를 생성한다.

```java
public class Item extends BaseEntity {

    ...
    
    public void addStock(int stockNumber) {
       
        this.stockNumber += stockNumber;
        
    }
    
}

```

주문을 취소할 경우 주문 수량만큼 상품의 재고를 증가시키는 메소드를 구현한다.

```java
public class OrderItem extends BaseEntity {

    ...
    
    public void cancel() {
        this.getItem().addStock(count);
    }

}

```

Item클래스에 주문 취소 시 주문 수량을 상품의 재고에 더해주는 로직과 주문 상태를 취소 상태로 바꿔주는 메소드

```java
public class Order extends BaseEntity {

    ...
    public void cancelOrder() {

        this.orderStatus = OrderStatus.CANCEL;
        this.orderItems.stream().forEach(orderItem -> orderItem.cancel());

    }
}

```

```java

public class OrderHistService {

    ...

    @Transactional
    public void cancelOrder(Long orderId, String email) {

        Order order = orderService.getOrderByIdWithMemberAndOrderItemAndItem(orderId);

        if (!StringUtils.equals(order.getMember().getEmail().getValue(), email)) { // 1)
            throw new BusinessException(ErrorCode.NOT_AUTHENTICATION_CANCEL_ORDER.getMessage());
        }

        order.cancelOrder(); // 2)
    }
}
```

1. 현재 유저가 주문을 했던 유저가 맞는지 검사
2. Order의 cancelOrder메소드 호출하여 OrderItem, Item의 cancel메소드까지 호출한다. 

### Cancel시 Order에 연관된 OrderItem, Item, Member를 fetch join을 통해 가져온다.

```java
public interface OrderRepository extends JpaRepository<Order, Long> {

    ...
    @Query("select o " +
            "from Order o join fetch o.member m " +
            "join fetch o.orderItems oi " +
            "join fetch oi.item " +
            "where o.id = :orderId")
    Optional<Order> findByOrderIdWithMemberAndOrderItemsAndItem(@Param("orderId") Long orderId);

    ...

}
```

---






