package dev.yoon.basic_board.dto.shop;

import dev.yoon.basic_board.domain.ShopPost;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class ShopPostDto {

    private Long userId;

    private Long shopId;

    private String title;

    private String content;

    public ShopPostDto(ShopPost shopPost) {
        this.userId = shopPost.getUser().getId();
        this.shopId = shopPost.getShop().getId();
        this.title = shopPost.getTitle();
        this.content = shopPost.getContent();

    }
}
