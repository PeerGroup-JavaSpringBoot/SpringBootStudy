package dev.yoon.shop.web.itemdtl.service;

import dev.yoon.shop.domain.item.entity.Item;
import dev.yoon.shop.domain.item.service.ItemService;
import dev.yoon.shop.domain.itemimg.entity.ItemImage;
import dev.yoon.shop.domain.itemimg.service.ItemImageService;
import dev.yoon.shop.domain.member.application.MemberService;
import dev.yoon.shop.domain.member.entity.Member;
import dev.yoon.shop.domain.order.entity.Order;
import dev.yoon.shop.domain.order.service.OrderService;
import dev.yoon.shop.domain.orderitem.entity.OrderItem;
import dev.yoon.shop.web.itemdtl.dto.ItemDtlDto;
import dev.yoon.shop.web.itemdtl.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ItemDtlService {

    private final ItemService itemService;
    private final ItemImageService itemImageService;
    private final OrderService orderService;
    private final MemberService memberService;

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





}
