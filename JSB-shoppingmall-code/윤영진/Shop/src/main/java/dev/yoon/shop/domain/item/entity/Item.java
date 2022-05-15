package dev.yoon.shop.domain.item.entity;

import dev.yoon.shop.domain.base.BaseEntity;
import dev.yoon.shop.domain.item.constant.ItemSellStatus;
import dev.yoon.shop.domain.base.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Table(name = "item")
@NoArgsConstructor()
@Entity
public class Item extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "item_id")
    private Long id;

    @Column(nullable = false, length = 50) // String 필드는 default 값으로 255(=varchar(255))가 설정, length를 이용하여 varchar(50) 설정 가능
    private String itemNm;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(nullable = false)
    private int stockNumber;

    @Lob // BLOB, CLOB 타입 매핑
    @Column(nullable = false)
    private String itemDetail;

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;

    @Builder
    public Item(Long id, String itemNm, int price, int stockNumber, String itemDetail, ItemSellStatus itemSellStatus) {
        this.id = id;
        this.itemNm = itemNm;
        this.price = price;
        this.stockNumber = stockNumber;
        this.itemDetail = itemDetail;
        this.itemSellStatus = itemSellStatus;
    }


}
