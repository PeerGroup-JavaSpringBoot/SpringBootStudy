package dev.yoon.shop.domain.item.entity;

import dev.yoon.shop.domain.base.BaseEntity;
import dev.yoon.shop.domain.item.constant.ItemSellStatus;
import dev.yoon.shop.domain.base.BaseTimeEntity;
import dev.yoon.shop.domain.member.entity.Member;
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

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;


    @Builder
    public Item(Long id, String itemNm, int price, int stockNumber, String itemDetail, ItemSellStatus itemSellStatus,Member member) {
        this.id = id;
        this.itemNm = itemNm;
        this.price = price;
        this.stockNumber = stockNumber;
        this.itemDetail = itemDetail;
        this.itemSellStatus = itemSellStatus;
        this.member = member;
    }


    public static Item createItem(Item item, Member member) {
        return Item.builder()
                .itemNm(item.getItemNm())
                .price(item.getPrice())
                .stockNumber(item.getStockNumber())
                .itemDetail(item.getItemDetail())
                .itemSellStatus(item.getItemSellStatus())
                .member(member)
                .build();

    }

    public void updateItem(Item updateItem) {
        this.itemNm = updateItem.getItemNm();
        this.price = updateItem.getPrice();
        this.stockNumber = updateItem.getStockNumber();
        this.itemDetail = updateItem.getItemDetail();
        this.itemSellStatus = updateItem.getItemSellStatus();
    }
}
