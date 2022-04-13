package dev.yoon.basic_board.dto.shop;

import dev.yoon.basic_board.domain.Address;
import dev.yoon.basic_board.domain.Category;
import dev.yoon.basic_board.domain.Shop;
import dev.yoon.basic_board.domain.ShopReview;
import dev.yoon.basic_board.dto.PostDto;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ShopDto {

    private Long userId;

    private Category category;

    private Address address;

    private List<ShopPostDto> shopPostDtos;

    private List<ShopReviewDto> shopReviewDtos;



    public ShopDto(Shop shop) {
        this.userId = shop.getUser().getId();
        this.category = shop.getCategory();
        this.address = shop.getAddress();

        this.shopPostDtos = shop.getShopPosts().stream()
                .map(shopPost -> new ShopPostDto(shopPost))
                .collect(Collectors.toList());

        this.shopReviewDtos = shop.getShopReviews().stream()
                .map(shopReview -> new ShopReviewDto(shopReview))
                .collect(Collectors.toList());
    }
}
