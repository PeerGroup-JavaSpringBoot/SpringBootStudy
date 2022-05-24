package dev.yoon.shop.domain.item.repository;

import dev.yoon.shop.domain.item.entity.Item;
import dev.yoon.shop.web.manageitem.repository.ManageItemRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long>, QuerydslPredicateExecutor<Item>, ManageItemRepository {

    List<Item> findByItemNm(String name);

    List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);

    List<Item> findByPriceLessThan(Integer price);

    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);

    List<Item> findByPriceLessThanOrderByPriceAsc(Integer price);

    /**
     * 상품 상세 설명을 파라미터로 받아 해당 내용을 상품 상세 설명에 포함하고 있는 데이터를 조회
     * 정렬 순서는 가격이 높은 순으로 조회
     */
    @Query("select i from Item i where i.itemDetail like " +
            "%:itemDetail% order by i.price desc")
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);

    @Query(value = "select i from Item i where i.itemDetail like " +
            "%:itemDetail% order by i.price desc", nativeQuery = true)
    List<Item> findByItemDetailByNative(@Param("itemDetail") String itemDetail);

}
