package dev.yoon.basic_community.dto.shop;

import dev.yoon.basic_community.domain.shop.ShopPost;
import dev.yoon.basic_community.dto.common.DateTime;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ShopPostDto {

    @Getter
    public static class Req {

        private String title;

        private String content;

        private Long writerId;

//        @Builder
//        public Req(String title, String content, Long shopId, Long writer) {
//            this.title = title;
//            this.content = content;
//            this.shopId = shopId;
//            this.writer = writer;
//        }
    }

    @Getter
    public static class Res {

        private Long shopId;

        private String title;

        private String content;

        private DateTime dateTime;

        public Res(ShopPost shopPost) {
            this.shopId = shopPost.getShop().getId();
            this.title = shopPost.getTitle();
            this.content = shopPost.getContent();
            this.dateTime = new DateTime(shopPost.getCreatedDate(), shopPost.getModifiedDate());
        }

    }


}
