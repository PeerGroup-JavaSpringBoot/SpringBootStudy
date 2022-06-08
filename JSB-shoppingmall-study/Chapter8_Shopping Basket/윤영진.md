# Chapter 8: 장바구니

## 장바구니 담기

상품 상세 페이지에서 장부구니에 담을 수량을 선택하고 장바구니 담기 버튼을 클릭할 때 상품이 장바구니에 담기는 기능을 구현

```java
@Entity
@Getter
@Table(name = "cart_item")
@NoArgsConstructor
public class CartItem extends BaseEntity {

    ...
    
    public static CartItem createCartItem(Cart cart, Item item, int count) {
        return CartItem.builder()
                .cart(cart)
                .item(item)
                .count(count)
                .build();
    }

    public void addCount(int count) {
        this.count += count; // 1)
    }
}

```

1. 장바구니에 기존에 담겨 있는 상품인데, 해당 상품을 추가로 장바구니에 담을 때 기존 수량에 현재 담을 수량을 더 해줄 때 사용할 메소드 

### 카트에 추가하기

```java
    @Transactional
    public Long cartOrder(CartItemDto cartItemDto, String email) {

        // 1. 상품 조회
        Item item = itemService.getItemById(cartItemDto.getItemId());

        // 2. 회원 조회
        Member member = memberService.getMemberByEmail(email);

        // 3. 카트 조회
        Cart cart = cartService.getCartByMemberId(member.getId());

        // 4. 카트 생성
        if (cart == null) { // 1)
            cart = Cart.createCart(member);
            cartService.saveCart(cart);
        }

        // 5. 카트아이템 조회
        CartItem cartItem = cartItemService.getCartItemByCartIdAndItemId(cart.getId(), item.getId());

        if (cartItem != null) { // 2)
            cartItem.addCount(cartItemDto.getCount());
            return cartItem.getId();
        }else {
            cartItem = CartItem.createCartItem(cart, item, cartItemDto.getCount());
            cartItemService.saveCartItem(cartItem);
            return cartItem.getId();
        }

    }
```

1. 상품을 처음으로 장바구니에 담을 경우 해당 회원의 장바구니 엔티티를 생성
2. 장바구니에 이미 있던 상품의 경우 기존 수량에 현재 장바구니에 담을 수량 만큼 더해준다.

## 장바구니 조회하기

```java
@Getter @Setter
public class CartDetailDto {

    private Long cartItemId; //장바구니 상품 아이디

    private String itemNm; //상품명

    private int price; //상품 금액

    private int count; //수량

    private String imgUrl; //상품 이미지 경로

    public CartDetailDto(Long cartItemId, String itemNm, int price, int count, String imgUrl){
        this.cartItemId = cartItemId;
        this.itemNm = itemNm;
        this.price = price;
        this.count = count;
        this.imgUrl = imgUrl;
    }

}

```

### DTO의 생성자를 이용하여 반환 값으로 DTO 객체를 생성 

```java
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Query("select new dev.yoon.shop.web.cart.dto.CartDetailDto(ci.id, i.itemNm, i.price, ci.count, im.imgUrl) " +
            "from CartItem ci, ItemImage im " +
            "join ci.item i " +
            "where ci.cart.id = :cartId " +
            "and im.item.id = ci.item.id " +
            "and im.isRepImg = TRUE " +
            "order by ci.regTime desc"
    )
    List<CartDetailDto> findCartDetailDtoList(Long cartId);

    ...
}

```

### 장바구니 상품 주문하기

```java
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartOrderService {

    private final MemberService memberService;
    private final ItemService itemService;
    private final OrderService orderService;
    private final CartItemService cartItemService;

    public Long cartOrders(List<OrderDto> orderDtoList, String email) {

        Member member = memberService.getMemberByEmail(email);
        List<OrderItem> orderItemList = new ArrayList<>();

        for (OrderDto orderDto : orderDtoList) {

            Item item = itemService.getItemById(orderDto.getItemId());

            OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
            orderItemList.add(orderItem);
        }

        Order order = Order.createOrder(member, orderItemList);
        orderService.order(order);

        return order.getId();

    }

    @Transactional
    public Long orderCartItem(List<CartOrderDto> cartOrderDtoList, String email) {

        List<OrderDto> orderDtoList = new ArrayList<>();
        for (CartOrderDto cartOrderDto : cartOrderDtoList) { // 1)
            CartItem cartItem = cartItemService.getCartItemById(cartOrderDto.getCartItemId());

            OrderDto orderDto = OrderDto.of(cartItem);
            orderDtoList.add(orderDto);
        }

        Long orderId = cartOrders(orderDtoList, email);

        for (CartOrderDto cartOrderDto : cartOrderDtoList) { // 2)
            CartItem cartItem = cartItemService.getCartItemById(cartOrderDto.getCartItemId());
            cartItemService.deleteCartItem(cartItem.getId());
        }

        return orderId;

    }
}
```
1. 장바구니에 존재하는 아이템을 가지고 Order 객체 생성
2. order를 마무리한 장바구니 아이템들 삭제


---

