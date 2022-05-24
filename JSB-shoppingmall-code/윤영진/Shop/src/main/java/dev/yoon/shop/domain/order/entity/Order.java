package dev.yoon.shop.domain.order.entity;

import dev.yoon.shop.domain.base.BaseEntity;
import dev.yoon.shop.domain.base.BaseTimeEntity;
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
public class Order  extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

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
}
