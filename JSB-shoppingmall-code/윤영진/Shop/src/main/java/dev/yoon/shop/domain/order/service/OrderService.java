package dev.yoon.shop.domain.order.service;

import dev.yoon.shop.domain.model.Email;
import dev.yoon.shop.domain.order.entity.Order;
import dev.yoon.shop.domain.order.exception.OrderNotFoundException;
import dev.yoon.shop.domain.order.repository.OrderRepository;
import dev.yoon.shop.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public void order(Order order) {
       orderRepository.save(order);
    }

    public Page<Order> getOrdersWithMember(String email, Pageable pageable) {

        return orderRepository.findOrdersWithMember(email, pageable);

    }
    public Order getOrderByIdWithMemberAndOrderItemAndItem(Long orderId) {
        return orderRepository.findByOrderIdWithMemberAndOrderItemsAndItem(orderId)
                .orElseThrow(() -> new OrderNotFoundException(ErrorCode.ORDER_NOT_EXISTS));
    }




//    public Orders getOrderById(Long orderId) {
//        return orderRepository.findById(orderId)
//                .orElseThrow(()-> new OrderNotFoundException());
//    }
//
//    public Page<Order> getOrdersWithMember(String email, Pageable pageable) {
//
//        return orderRepository.findOrdersWithMember(Email.of(email), pageable);
//
//    }
//

}
