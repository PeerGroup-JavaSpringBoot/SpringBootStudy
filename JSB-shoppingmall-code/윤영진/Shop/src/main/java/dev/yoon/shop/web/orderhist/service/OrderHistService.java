package dev.yoon.shop.web.orderhist.service;

import dev.yoon.shop.domain.itemimg.entity.ItemImage;
import dev.yoon.shop.domain.itemimg.service.ItemImageService;
import dev.yoon.shop.domain.order.entity.Order;
import dev.yoon.shop.domain.order.service.OrderService;
import dev.yoon.shop.domain.orderitem.entity.OrderItem;
import dev.yoon.shop.global.error.exception.BusinessException;
import dev.yoon.shop.global.error.exception.ErrorCode;
import dev.yoon.shop.web.orderhist.dto.OrderHistDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.naming.AuthenticationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
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

        List<OrderHistDto.OrderItemHistDto> orderItemHistDtos = orderItems.stream().map(
                orderItem -> {

                    ItemImage itemImage = itemImageService.getItemImageByItemIdAndIsRepImg(orderItem.getItem().getId(), true);
                    return OrderHistDto.OrderItemHistDto.of(orderItem, itemImage);

                }).collect(Collectors.toList());
        return orderItemHistDtos;

    }

    @Transactional
    public void cancelOrder(Long orderId, String email) {

        Order order = orderService.getOrderByIdWithMemberAndOrderItemAndItem(orderId);

        if (!StringUtils.equals(order.getMember().getEmail().getValue(), email)) {
            throw new BusinessException(ErrorCode.NOT_AUTHENTICATION_CANCEL_ORDER.getMessage());
        }

        order.cancelOrder();
    }
}
