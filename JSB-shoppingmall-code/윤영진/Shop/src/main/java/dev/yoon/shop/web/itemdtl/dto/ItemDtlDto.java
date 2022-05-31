package dev.yoon.shop.web.itemdtl.dto;

import dev.yoon.shop.domain.item.constant.ItemSellStatus;
import dev.yoon.shop.domain.item.entity.Item;
import dev.yoon.shop.domain.itemimg.entity.ItemImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ItemDtlDto {

    private Long itemId;

    private String itemName;

    private Integer price;

    private String itemDetail;

    private Integer stockNumber;

    private ItemSellStatus itemSellStatus;

    private List<ItemImageDto> itemImageDtos = new ArrayList<>();


    @Builder
    public ItemDtlDto(
            Long itemId, String itemName, Integer price, String itemDetail, Integer stockNumber,
            ItemSellStatus itemSellStatus, List<ItemImageDto> itemImageDtos) {

        this.itemId = itemId;
        this.itemName = itemName;
        this.price = price;
        this.itemDetail = itemDetail;
        this.stockNumber = stockNumber;
        this.itemSellStatus = itemSellStatus;
        this.itemImageDtos = itemImageDtos;

    }

    public static ItemDtlDto of(Item item, List<ItemImage> itemImages) {

        List<ItemImageDto> itemImageDtos = ItemImageDto.of(itemImages);

        return ItemDtlDto.builder()
                .itemId(item.getId())
                .itemName(item.getItemNm())
                .itemDetail(item.getItemDetail())
                .itemSellStatus(item.getItemSellStatus())
                .price(item.getPrice())
                .stockNumber(item.getStockNumber())
                .itemImageDtos(itemImageDtos)
                .build();
    }

    @Getter @Setter
    @Builder
    @AllArgsConstructor
    public static class ItemImageDto {
        private String imageUrl;

        public static List<ItemImageDto> of(List<ItemImage> itemImages) {
            return itemImages.stream().map(
                    itemImage -> ItemImageDto.builder()
                            .imageUrl(itemImage.getImgUrl())
                            .build()).collect(Collectors.toList());
        }
    }
}
