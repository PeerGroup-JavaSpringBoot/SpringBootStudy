package dev.yoon.basic_board.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.yoon.basic_board.dto.PostDto;
import dev.yoon.basic_board.dto.shop.ShopPostDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "SHOPPOST")
public class ShopPost {
// 해당 shop 주인user만 작성 가능
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Shop shop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    public ShopPost(ShopPostDto shopPostDto) {
        this.title = shopPostDto.getTitle();
        this.content = shopPostDto.getContent();
    }


    public static ShopPostDto createShopPostDto(ShopPost shopPost) {

        ShopPostDto shopPostDto = new ShopPostDto();
        shopPostDto.setShopId(shopPost.getShop().getId());
        shopPostDto.setUserId(shopPost.getUser().getId());
        shopPostDto.setTitle(shopPost.getTitle());
        shopPostDto.setContent(shopPost.getContent());
        return shopPostDto;

    }

    public void update(ShopPostDto shopPostDto) {

        this.setTitle(shopPostDto.getTitle());
        this.setContent(shopPostDto.getContent());

    }
}
