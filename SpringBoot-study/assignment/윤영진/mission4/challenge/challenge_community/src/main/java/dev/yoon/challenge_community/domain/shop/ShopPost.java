package dev.yoon.challenge_community.domain.shop;

import dev.yoon.challenge_community.common.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "SHOPPOST")
public class ShopPost extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @ManyToOne(
            targetEntity = Shop.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @Builder
    public ShopPost(String title, String content, Shop shop) {
        this.title = title;
        this.content = content;
        this.shop = shop;
        shop.getShopPosts().add(this);
    }
}
