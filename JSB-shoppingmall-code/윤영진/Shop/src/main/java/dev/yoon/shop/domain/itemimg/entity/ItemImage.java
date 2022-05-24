package dev.yoon.shop.domain.itemimg.entity;

import dev.yoon.shop.domain.base.BaseEntity;
import dev.yoon.shop.domain.item.entity.Item;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Table(name = "item_image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ItemImage extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private Boolean isRepImg;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Builder
    public ItemImage(String imgName, String originImgName, String imgUrl, Boolean isRepImg, Item item) {
        this.imgName = imgName;
        this.oriImgName = originImgName;
        this.imgUrl = imgUrl;
        this.isRepImg = isRepImg;
        this.item = item;
    }

    public static ItemImage createItemImage(ItemImage itemImage, Item item) {
        return ItemImage.builder()
                .imgName(itemImage.getImgName())
                .imgUrl(itemImage.getImgUrl())
                .originImgName(itemImage.getOriImgName())
                .isRepImg(itemImage.getIsRepImg())
                .item(item)
                .build();
    }

    public void updateItemImg(String oriImgName, String imgName, String imgUrl) {
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }

    public void initImageInfo() {

        this.oriImgName = "";
        this.imgName = "";
        this.imgUrl = "";
    }

}
