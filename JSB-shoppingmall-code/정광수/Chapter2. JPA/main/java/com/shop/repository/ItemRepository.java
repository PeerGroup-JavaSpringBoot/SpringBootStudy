package com.shop.repository;

import com.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long>, QuerydslPredicateExecutor<Item>{
    List<Item> findByItemNm(String itemNm); //상품 이름으로 조회
    List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail); //상품이름 or 설명으로 조회
    List<Item> findByPriceLessThan(Integer Price); //Price값보다 작은 것 처리하기
    List<Item> findByPriceLessThanOrderByPriceDesc(Integer Price);

    @Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc")
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);


    @Query(value = "select * from item i where i.item_detail like %:itemDetail%" +
            "order by i.price desc", nativeQuery = true)
    List<Item> findByItemDetialByNative(@Param("itemDetail") String itemDetail);

}
