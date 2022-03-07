package dev.yoon.basic_board.dto.shop;

import dev.yoon.basic_board.domain.ShopReview;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class ShopReviewDto {

    private Long userId;

    private Long shopId;

    private String title;

    private String content;

    public ShopReviewDto(ShopReview shopReview) {
        this.userId = shopReview.getUser().getId();
        this.shopId = shopReview.getShop().getId();
        this.title = shopReview.getTitle();
        this.content = shopReview.getContent();

    }
}
