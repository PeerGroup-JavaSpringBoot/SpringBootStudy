package dev.yoon.basic_community.repository;

import dev.yoon.basic_community.domain.shop.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Long> {

}

