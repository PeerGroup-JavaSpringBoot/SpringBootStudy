package dev.aquashdw.community.entity;

import dev.aquashdw.community.controller.dto.ShopPostDto;

import javax.persistence.*;

@Entity
@Table(name = "shop_post")
public class ShopPostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;

    @ManyToOne(
            targetEntity = ShopEntity.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "shop_id")
    private ShopEntity shop;

    public ShopPostEntity() {
    }

    public ShopPostEntity(Long id, String title, String content, ShopEntity shop) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.shop = shop;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ShopEntity getShop() {
        return shop;
    }

    public void setShop(ShopEntity shop) {
        this.shop = shop;
    }
}
