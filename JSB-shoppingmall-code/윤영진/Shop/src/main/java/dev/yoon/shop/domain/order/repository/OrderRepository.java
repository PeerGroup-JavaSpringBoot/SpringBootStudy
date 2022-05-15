package dev.yoon.shop.domain.order.repository;

import dev.yoon.shop.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
