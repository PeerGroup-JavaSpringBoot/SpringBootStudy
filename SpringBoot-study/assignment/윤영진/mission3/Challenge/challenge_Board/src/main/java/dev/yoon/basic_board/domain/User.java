package dev.yoon.basic_board.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.yoon.basic_board.dto.UserDto;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "area")
    private Area area;

    private Boolean verify;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Shop> shopList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<ShopPost> shopPosts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ShopReview> shopReviews;

    public User(UserDto userDto) {
        this.name = userDto.getName();
        this.verify = userDto.getVerify();
    }

    public void update(UserDto userDto) {
        this.name = userDto.getName();
    }

    public void addShop(Shop shop) {
        shopList.add(shop);
        shop.setUser(this);
    }

    public void addPost(Post post) {
        postList.add(post);
        post.setUser(this);
    }

    public void addShopPost(ShopPost shopPost) {
        shopPosts.add(shopPost);
        shopPost.setUser(this);
    }

    public void addShopReview(ShopReview shopReview) {
        shopReviews.add(shopReview);
        shopReview.setUser(this);
    }

    public void addArea(Area area) {
        this.area = area;
    }
}
