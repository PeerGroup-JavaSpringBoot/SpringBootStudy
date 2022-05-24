package dev.yoon.shop.web.main.dto;

import com.querydsl.core.annotations.QueryProjection;
import dev.yoon.shop.domain.item.entity.Item;
import dev.yoon.shop.domain.itemimg.entity.ItemImage;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class MainItemDto {


    private Long itemId;

    private String itemName;

    private String itemDetail;

    private String imageUrl;

    private Integer price;

    @QueryProjection
    public MainItemDto(Long id, String itemName, String itemDetail, String imageUrl, Integer price) {
        this.itemId = id;
        this.itemName = itemName;
        this.itemDetail = itemDetail;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public static MainItemDto toEntity(Item item, ItemImage itemImage) {
        return MainItemDto.builder()
                .itemId(item.getId())
                .itemName(item.getItemNm())
                .itemDetail(item.getItemDetail())
                .price(item.getPrice())
                .imageUrl(itemImage.getImgUrl())
                .build();
    }


}
