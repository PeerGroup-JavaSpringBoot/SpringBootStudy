package dev.yoon.challenge_community.dto.shop;

import dev.yoon.challenge_community.domain.Address;
import dev.yoon.challenge_community.domain.Location;
import dev.yoon.challenge_community.domain.shop.Category;
import dev.yoon.challenge_community.domain.shop.Shop;
import dev.yoon.challenge_community.dto.common.DateTime;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ShopDto {

    @Getter
    @ToString
    public static class Req {

        @NotNull
        private Long userId;

        @NotNull
        private Category category;

        @NotNull
        private Address address;

        @NotNull
        private Location location;

        @Builder
        public Req(Long userId, Category category, Address address, Location location) {
            this.userId = userId;
            this.category = category;
            this.address = address;
            this.location = location;
        }

//        public Shop toEntity() {
//            return Shop.builder()
//                    .category(category)
//
//                    .username(this.name)
//                    .password(this.password)
//                    .userCategory(this.userCategory)
//                    .build();
//        }

    }

    @Getter
    public static class Res {

//        private UserDto.Res user;

        @NotNull
        private Address address;

        @NotNull
        private Location location;

        private DateTime dateTime;

        private Category category;

        private List<ShopPostDto.Res> shopPosts = new ArrayList<>();

        public Res(Shop shop) {
//            this.user = new UserDto.Res(shop.getUser());
            this.location = shop.getLocation().getLocation();
            this.address = shop.getLocation().getAddress();
            this.dateTime = new DateTime(shop.getCreatedDate(), shop.getModifiedDate());
            this.category = shop.getCategory();
            this.shopPosts = shop.getShopPosts().stream()
                    .parallel().map(shopPost -> new ShopPostDto.Res(shopPost))
                    .collect(Collectors.toList());
        }

    }
}
