package dev.yoon.basic_community.domain.shop;

import dev.yoon.basic_community.common.BaseTimeEntity;
import dev.yoon.basic_community.domain.Area;
import dev.yoon.basic_community.domain.user.User;
import dev.yoon.basic_community.dto.shop.ShopDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "SHOP")
public class Shop extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne(
            fetch = FetchType.LAZY,
            targetEntity = User.class
    )
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(
            targetEntity = Area.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "located_at")
    private Area location;

    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToMany(
            targetEntity = ShopPost.class,
            mappedBy = "shop",
            orphanRemoval = true)
    private List<ShopPost> shopPosts = new ArrayList<>();

    @Builder
    public Shop(User user, Area location,Category category) {
        this.user = user;
        this.location = location;
        this.category = category;
    }


    public void updateShop(ShopDto.Req shopDto) {

        this.category = shopDto.getCategory();
        //TODO location 변경
//        this.location = shopDto.getLocation()

    }
}
