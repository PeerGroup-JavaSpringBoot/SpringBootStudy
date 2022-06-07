package dev.yoon.shop.web.adminitem.dto;

import dev.yoon.shop.domain.item.constant.ItemSellStatus;
import dev.yoon.shop.domain.item.entity.Item;
import dev.yoon.shop.domain.itemimg.entity.ItemImage;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class UpdateItemDto {

    private Long itemId;

    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    private String itemName;

    @NotNull(message = "가격은 필수 입력 값입니다.")
    private Integer price;

    @NotBlank(message = "상품 상세는 필수 입력 값입니다.")
    private String itemDetail;

    @NotNull(message = "재고는 필수 입력 값입니다.")
    private Integer stockNumber;

    private ItemSellStatus itemSellStatus;

    private List<MultipartFile> itemImageFiles;

    private List<ItemImageDto> itemImageDtos;

    /**
     * originalImageNames에는 값이 없는데 데이터베이스에는 저장된 값이 있는 경우
     * 이미지를 삭제하는 경우라고 판단
     */
    private List<String> originalImageNames;

    @Builder
    public UpdateItemDto(Long itemId, String itemName, Integer price, String itemDetail,
                         Integer stockNumber, ItemSellStatus itemSellStatus, List<MultipartFile> itemImageFiles,
                         List<ItemImageDto> itemImageDtos, List<String> originalImageNames) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.price = price;
        this.itemDetail = itemDetail;
        this.stockNumber = stockNumber;
        this.itemSellStatus = itemSellStatus;
        this.itemImageFiles = itemImageFiles;
        this.itemImageDtos = itemImageDtos;
        this.originalImageNames = originalImageNames;
    }

    public static UpdateItemDto of(Item item, List<ItemImage> itemImages) {

        List<ItemImageDto> itemImageDtos = ItemImageDto.of(itemImages);

        UpdateItemDto updateItemDto = UpdateItemDto.builder()
                .itemId(item.getId())
                .itemName(item.getItemNm())
                .itemDetail(item.getItemDetail())
                .itemSellStatus(item.getItemSellStatus())
                .stockNumber(item.getStockNumber())
                .price(item.getPrice())
                .itemImageDtos(itemImageDtos)
                .build();

        return updateItemDto;

    }

    public Item toEntity() {
        return Item.builder()
                .itemNm(itemName)
                .itemDetail(itemDetail)
                .price(price)
                .itemSellStatus(itemSellStatus)
                .stockNumber(stockNumber)
                .build();
    }

    @Builder
    @Getter
    @Setter
    public static class ItemImageDto {
        private Long itemImageId;
        private String originalImageName;

        public static List<ItemImageDto> of(List<ItemImage> itemImages) {
            return itemImages.stream().map(
                    itemImage -> ItemImageDto.builder()
                    .itemImageId(itemImage.getId())
                    .originalImageName(itemImage.getOriImgName())
                    .build()).collect(Collectors.toList());
        }
    }


}