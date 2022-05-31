package dev.yoon.shop.domain.order.entity;

import dev.yoon.shop.domain.base.BaseEntity;
import dev.yoon.shop.domain.base.BaseTimeEntity;
import dev.yoon.shop.domain.member.entity.Member;
import dev.yoon.shop.domain.order.constant.OrderStatus;
import dev.yoon.shop.domain.orderitem.entity.OrderItem;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime orderDate; //주문일

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    public void setMember(Member member) {
        this.member = member;
    }

    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.updateOrder(this);
    }

    @Builder
    public Order(OrderStatus orderStatus, Member member, List<OrderItem> orderItems) {
        this.orderStatus = orderStatus;
        this.member = member;
        this.orderDate = LocalDateTime.now();
        for (OrderItem orderItem : orderItems) {
            this.addOrderItem(orderItem);
        }
    }

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

    public void cancelOrder() {

        this.orderStatus = OrderStatus.CANCEL;
        this.orderItems.stream().forEach(orderItem -> orderItem.cancel());

    }
}
