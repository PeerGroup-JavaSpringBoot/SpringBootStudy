package dev.yoon.shop.domain.order.entity;

import dev.yoon.shop.domain.item.constant.ItemSellStatus;
import dev.yoon.shop.domain.item.entity.Item;
import dev.yoon.shop.domain.item.repository.ItemRepository;
import dev.yoon.shop.domain.member.entity.Member;
import dev.yoon.shop.domain.member.repository.MemberRepository;
import dev.yoon.shop.domain.model.Password;
import dev.yoon.shop.domain.order.repository.OrderRepository;
import dev.yoon.shop.domain.orderitem.entity.OrderItem;
import dev.yoon.shop.domain.orderitem.repository.OrderItemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
class OrderTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    MemberRepository memberRepository;

    @PersistenceContext
    EntityManager em;

    public Item createItem() {
        Item item = Item.builder()
                .itemNm("테스트 상품")
                .price(10000)
                .itemDetail("상세설명")
                .itemSellStatus(ItemSellStatus.SELL)
                .build();
        return item;
    }

    public Order createOrder() {
        Order order = new Order();
        for (int i = 0; i < 3; i++) {
            Item item = createItem();
            itemRepository.save(item);
            OrderItem orderItem = OrderItem.builder()
                    .item(item)
                    .count(10)
                    .orderPrice(1000)
                    .order(order)
                    .build();
            order.getOrderItems().add(orderItem);
        }
        Member member = Member.builder()
                .email("dudwls143@gmail.com")
                .password(Password.builder()
                        .value("password")
                        .build())
                .build();

        memberRepository.save(member);
        order.setMember(member);
        orderRepository.save(order);
        return order;
    }

    @Test
    @DisplayName("영속성 전이 테스트")
    @Transactional
    public void 영속성_전이_테스트() {
        Order order = new Order();

        for (int i = 0; i < 3; i++) {
            Item item = createItem();
            itemRepository.save(item);
            OrderItem orderItem = OrderItem.builder()
                    .item(item)
                    .count(10)
                    .orderPrice(1000)
                    .order(order)
                    .build();

            order.getOrderItems().add(orderItem);
        }

        orderRepository.save(order);
        em.clear();

        Order savedOrder = orderRepository.findById(order.getId())
                .orElseThrow(EntityNotFoundException::new);
        assertEquals(3, savedOrder.getOrderItems().size());

    }

    @Test
    @DisplayName("고아객체 제거 테스트")
    @Transactional
    public void 고아객체_제거_테스트() {
        Order order = createOrder();

        order.getOrderItems().remove(0);
        em.flush();
    }

    @Test
    @DisplayName("Cascade REMOVE 테스트")
    @Transactional
    public void Cascade_REMOVE_테스트() {
        Order order = createOrder();

        orderRepository.delete(order);
        em.flush();
    }

    @Test
    @DisplayName("지연 로딩 테스트")
    @Transactional
    public void lazyLoadingTest(){
        Order order = this.createOrder();
        Long orderItemId = order.getOrderItems().get(0).getId();
        em.flush();
        em.clear();
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(EntityNotFoundException::new);
        System.out.println("Order class : " + orderItem.getOrder().getClass());
        System.out.println("===========================");
        orderItem.getOrder().getOrderStatus();
        System.out.println("===========================");
    }


}