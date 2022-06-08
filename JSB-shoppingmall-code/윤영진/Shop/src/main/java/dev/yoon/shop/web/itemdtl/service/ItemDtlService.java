package dev.yoon.shop.web.itemdtl.service;

import dev.yoon.shop.domain.cart.entity.Cart;
import dev.yoon.shop.domain.cart.service.CartService;
import dev.yoon.shop.domain.cartitem.entity.CartItem;
import dev.yoon.shop.domain.cartitem.service.CartItemService;
import dev.yoon.shop.domain.item.entity.Item;
import dev.yoon.shop.domain.item.service.ItemService;
import dev.yoon.shop.domain.itemimg.entity.ItemImage;
import dev.yoon.shop.domain.itemimg.service.ItemImageService;
import dev.yoon.shop.domain.member.application.MemberService;
import dev.yoon.shop.domain.member.entity.Member;
import dev.yoon.shop.domain.order.entity.Order;
import dev.yoon.shop.domain.order.service.OrderService;
import dev.yoon.shop.domain.orderitem.entity.OrderItem;
import dev.yoon.shop.web.itemdtl.dto.CartItemDto;
import dev.yoon.shop.web.itemdtl.dto.ItemDtlDto;
import dev.yoon.shop.web.itemdtl.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ItemDtlService {

    private final ItemService itemService;
    private final ItemImageService itemImageService;
    private final OrderService orderService;
    private final MemberService memberService;
    private final CartService cartService;
    private final CartItemService cartItemService;

    public ItemDtlDto getItemDtl(Long itemId) {

        Item item = itemService.getItemById(itemId);

        List<ItemImage> itemImages = itemImageService.getItemImageByItemId(itemId);

        ItemDtlDto itemDtlDto = ItemDtlDto.of(item, itemImages);

        return itemDtlDto;
    }

    @Transactional
    public void order(OrderDto orderDto, String email) {

        // 1. 상품 조회
        Item item = itemService.getItemById(orderDto.getItemId());

        // 2. 회원 조회
        Member member = memberService.getMemberByEmail(email);

        // 3. 주문 상품 생성
        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
        orderItems.add(orderItem);

        // 4. 주문 생성
        Order savedOrder = Order.createOrder(member, orderItems);
        savedOrder.addOrderItem(orderItem);

        // 5. 주문 저장
        orderService.order(savedOrder);


    }

    @Transactional
    public Long cartOrder(CartItemDto cartItemDto, String email) {

        // 1. 상품 조회
        Item item = itemService.getItemById(cartItemDto.getItemId());

        // 2. 회원 조회
        Member member = memberService.getMemberByEmail(email);

        // 3. 카트 조회
        Cart cart = cartService.getCartByMemberId(member.getId());

        // 4. 카트 생성
        if (cart == null) {
            cart = Cart.createCart(member);
            cartService.saveCart(cart);
        }

        // 5. 카트아이템 조회
        CartItem cartItem = cartItemService.getCartItemByCartIdAndItemId(cart.getId(), item.getId());

        if (cartItem != null) {
            cartItem.addCount(cartItemDto.getCount());
            return cartItem.getId();
        }else {
            cartItem = CartItem.createCartItem(cart, item, cartItemDto.getCount());
            cartItemService.saveCartItem(cartItem);
            return cartItem.getId();
        }

    }
}
