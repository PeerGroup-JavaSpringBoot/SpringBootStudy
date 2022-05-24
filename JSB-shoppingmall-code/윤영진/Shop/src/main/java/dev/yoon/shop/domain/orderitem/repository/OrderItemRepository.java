package dev.yoon.shop.domain.orderitem.repository;

import dev.yoon.shop.domain.orderitem.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
