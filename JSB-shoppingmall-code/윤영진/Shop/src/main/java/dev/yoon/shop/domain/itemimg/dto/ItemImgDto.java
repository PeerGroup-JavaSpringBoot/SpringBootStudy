package dev.yoon.shop.domain.itemimg.dto;

import dev.yoon.shop.domain.itemimg.entity.ItemImage;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class ItemImgDto {

    private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private Boolean repImg;

    private static ModelMapper modelMapper = new ModelMapper();

    public static ItemImgDto of(ItemImage itemImage) {
        return modelMapper.map(itemImage,ItemImgDto.class);
    }

}
