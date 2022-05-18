package dev.yoon.shop.domain.cart.repository;

import dev.yoon.shop.domain.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
