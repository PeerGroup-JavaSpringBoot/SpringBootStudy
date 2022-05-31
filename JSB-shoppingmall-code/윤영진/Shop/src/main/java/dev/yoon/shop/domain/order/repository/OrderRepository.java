package dev.yoon.shop.domain.order.repository;

import dev.yoon.shop.domain.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    //    @Query("select o from Order o join fetch o.member " +
//            "where o.member.email =: email " +
//            "order by o.orderDate desc ")
//    List<Order> findOrdersWithMember(@Param("email") String email, Pageable pageable);
//
//    @Query("select count(o) from Order o " +
//            "where o.member.email =: email")
//    Long countOrder(@Param("email") String email);
    @Query("select o " +
            "from Order o join fetch o.member m " +
            "join fetch o.orderItems oi " +
            "join fetch oi.item " +
            "where o.id = :orderId")
    Optional<Order> findByOrderIdWithMemberAndOrderItemsAndItem(@Param("orderId") Long orderId);

    @Query(value = "select o " +
            "from Order o " +
            "where o.member.email.value = :email " +
            "order by o.orderDate desc ",
            countQuery = "select count(o) " +
                    "from Order o " +
                    "where o.member.email = :email")
    Page<Order> findOrdersWithMember(@Param("email") String email, Pageable pageable);


}
