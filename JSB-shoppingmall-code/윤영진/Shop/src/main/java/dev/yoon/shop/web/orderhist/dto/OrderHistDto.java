package dev.yoon.shop.web.orderhist.dto;

import dev.yoon.shop.domain.itemimg.entity.ItemImage;
import dev.yoon.shop.domain.order.constant.OrderStatus;
import dev.yoon.shop.domain.order.entity.Order;
import dev.yoon.shop.domain.orderitem.entity.OrderItem;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Getter
    @Setter
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