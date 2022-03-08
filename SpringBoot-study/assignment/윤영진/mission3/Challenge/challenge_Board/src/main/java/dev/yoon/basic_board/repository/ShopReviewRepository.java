package dev.yoon.basic_board.repository;

import dev.yoon.basic_board.domain.ShopPost;
import dev.yoon.basic_board.domain.ShopReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopReviewRepository extends JpaRepository<ShopReview,Long> {

    @Query("select sr from ShopReview sr where sr.shop.Id =:shopId")
    List<ShopReview> findShopReviewsByShop(Long shopId);

    @Query("select sr from ShopReview sr where sr.Id =:shopReviewId and sr.shop.Id =:shopId")
    ShopReview findShopReviewByShop(Long shopId, Long shopReviewId);
}
