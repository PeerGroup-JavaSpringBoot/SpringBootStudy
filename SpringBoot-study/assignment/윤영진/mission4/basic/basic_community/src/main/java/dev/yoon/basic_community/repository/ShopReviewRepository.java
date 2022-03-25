package dev.yoon.basic_community.repository;

import dev.yoon.basic_community.domain.shop.ShopReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopReviewRepository extends JpaRepository<ShopReview, Long> {

}
