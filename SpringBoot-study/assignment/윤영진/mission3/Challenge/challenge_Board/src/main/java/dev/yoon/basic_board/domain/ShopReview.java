package dev.yoon.basic_board.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.yoon.basic_board.dto.shop.ShopReviewDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "SHOPREVIEW")
public class ShopReview {
    // 어떤 사용자든 작성 가능

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Shop shop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    public ShopReview(ShopReviewDto shopReviewDto) {
        this.title = shopReviewDto.getTitle();
        this.content = shopReviewDto.getContent();

    }

    public static ShopReviewDto createShopReviewDto(ShopReview shopReview) {

        ShopReviewDto shopReviewDto = new ShopReviewDto();
        shopReviewDto.setShopId(shopReview.getShop().getId());
        shopReviewDto.setUserId(shopReview.getUser().getId());
        shopReviewDto.setTitle(shopReview.getTitle());
        shopReviewDto.setContent(shopReview.getContent());
        return shopReviewDto;

    }

    public void update(ShopReviewDto shopReviewDto) {

        this.setTitle(shopReviewDto.getTitle());
        this.setContent(shopReviewDto.getContent());

    }
}
