package dev.yoon.shop.web.cart.service;

import dev.yoon.shop.domain.cart.service.CartService;
import dev.yoon.shop.domain.cartitem.entity.CartItem;
import dev.yoon.shop.domain.cartitem.service.CartItemService;
import dev.yoon.shop.domain.item.entity.Item;
import dev.yoon.shop.domain.item.service.ItemService;
import dev.yoon.shop.domain.member.application.MemberService;
import dev.yoon.shop.domain.member.entity.Member;
import dev.yoon.shop.domain.order.entity.Order;
import dev.yoon.shop.domain.order.service.OrderService;
import dev.yoon.shop.domain.orderitem.entity.OrderItem;
import dev.yoon.shop.web.cart.dto.CartOrderDto;
import dev.yoon.shop.web.itemdtl.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
        for (CartOrderDto cartOrderDto : cartOrderDtoList) {
            CartItem cartItem = cartItemService.getCartItemById(cartOrderDto.getCartItemId());

            OrderDto orderDto = OrderDto.of(cartItem);
            orderDtoList.add(orderDto);
        }

        Long orderId = cartOrders(orderDtoList, email);

        for (CartOrderDto cartOrderDto : cartOrderDtoList) {
            CartItem cartItem = cartItemService.getCartItemById(cartOrderDto.getCartItemId());
            cartItemService.deleteCartItem(cartItem.getId());
        }

        return orderId;

    }
}
