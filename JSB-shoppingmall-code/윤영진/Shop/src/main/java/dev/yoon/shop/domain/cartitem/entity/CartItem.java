package dev.yoon.shop.domain.cartitem.entity;

import dev.yoon.shop.domain.base.BaseEntity;
import dev.yoon.shop.domain.base.BaseTimeEntity;
import dev.yoon.shop.domain.cart.entity.Cart;
import dev.yoon.shop.domain.item.entity.Item;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "cart_item")
public class CartItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int count;


}
