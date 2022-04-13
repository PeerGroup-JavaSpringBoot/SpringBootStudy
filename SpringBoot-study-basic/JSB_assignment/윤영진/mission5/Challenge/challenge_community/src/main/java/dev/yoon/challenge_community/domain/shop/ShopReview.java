package dev.yoon.challenge_community.domain.shop;

import dev.yoon.challenge_community.domain.user.User;

import javax.persistence.*;

@Entity
public class ShopReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private Long grade;

    @ManyToOne(
            targetEntity = Shop.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @ManyToOne(
            targetEntity = User.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "writer")
    private User writer;
}
