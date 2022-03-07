package dev.yoon.basic_board.repository;

import dev.yoon.basic_board.domain.ShopPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShopPostRepository extends JpaRepository<ShopPost,Long> {

    @Query("select sp from ShopPost sp where sp.shop.Id =:shopId")
    List<ShopPost> findShopPostsByShop(Long shopId);

    @Query("select sp from ShopPost sp where sp.Id =:shopPostId and sp.shop.Id =:shopId")
    ShopPost findShopPostByShop(Long shopId, Long shopPostId);


}
