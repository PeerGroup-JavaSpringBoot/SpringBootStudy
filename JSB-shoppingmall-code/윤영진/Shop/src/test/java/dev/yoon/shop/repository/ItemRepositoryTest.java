package dev.yoon.shop.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.yoon.shop.domain.item.constant.ItemSellStatus;
import dev.yoon.shop.domain.item.entity.Item;
import dev.yoon.shop.domain.item.entity.QItem;
import dev.yoon.shop.domain.item.repository.ItemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 실제 애플리케이션을 구동할 때처럼 모든 Bean을 IoC 컨테이너에 등록
 */
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
class ItemRepositoryTest {

    /**
     * Bean 주입
     */
    @Autowired
    ItemRepository itemRepository;

    /**
     * 영속성 컨텍스트를 사용하기 위해 @PersistenceContext 어노테이션을 이용해 EntityManager 빈을 주입
     */
    @PersistenceContext
    EntityManager em;

    /**
     * 해당 메소드를 테스트 대상으로 지정
     */
//    @Test
//    @DisplayName("상품 저장 테스트")
    public void createItemTest() {
        for (int i = 1; i <= 10; i++) {
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);

            if (i >= 5) {
                item.setItemDetail("테스트 상품 상세 설명" + i);
            }else {
                item.setItemDetail("테스트 테스트 테스트 테스트" + i);
            }
            if(i % 2 ==0) {
                item.setItemSellStatus(ItemSellStatus.SELL);
            }
            else {
                item.setItemSellStatus(ItemSellStatus.SOLD_OUT);
            }
            item.setStockNumber(100);
            itemRepository.save(item);

        }
    }

    @Test
    @DisplayName("상품명 조회 테스트")
    public void findByItemNmTest() {

        this.createItemTest();
        List<Item> 테스트_상품1 = itemRepository.findByItemNm("테스트 상품1");

        for (Item item : 테스트_상품1) {
            System.out.println(item.toString());

        }
    }

    @Test
    @DisplayName("상품명, 상품상세설명 or 테스트")
    public void 상품상세설명_or_테스트() {
        this.createItemTest();
        List<Item> itemList = itemRepository.findByItemNmOrItemDetail("테스트 상품1", "테스트 상품 상세 설명5");
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("가격 LessThan 테스트")
    public void 가격_LessThan_테스트() {
        this.createItemTest();
        List<Item> itemList = itemRepository.findByPriceLessThan(10005);
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("가격 내림차순 조회 테스트")
    public void 가격_내림차순_조회_테스트() {
        this.createItemTest();
        List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(10005);

        for (Item item : itemList) {

            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("가격 오름차순 조회 테스트")
    public void 가격_오름차순_조회_테스트() {
        this.createItemTest();
        List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceAsc(10005);

        for (Item item : itemList) {

            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("@Query를 이용한 상품 조회 테스트")
    public void Query를_이용한_상품_조회_테스트() {
        this.createItemTest();
        List<Item> itemList = itemRepository.findByItemDetail("테스트 상품 상세 설명");

        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("QueryDSL 조회 테스트1")
    public void QueryDSL_조회_테스트() {
        this.createItemTest();

        /**
         * JPAQueryFactory를 이용하여 쿼리를 동적으로 생성
         */
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
        /**
         * QueryDSL을 통해 쿼리를 생성하기 위해 플러그인을 통해 자동으로 생성된 QItem 객체를 이용
         */
        QItem qItem = QItem.item;
        JPAQuery<Item> query = jpaQueryFactory.selectFrom(qItem)
                .where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
                .where(qItem.itemDetail.like("%" + "테스트 상품 상세 설명" + "%"))
                .orderBy(qItem.price.desc());

        /**
         * JPAQuery 메소드중 하나인 fetch를 이용해서 쿼리 결과를 리스트로 반환
         * fetch 메소드 실행 시점에 쿼리문이 실행
         */
        List<Item> itemList = query.fetch();

        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    public void createItemList2(){
        for(int i=1;i<=5;i++){
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            itemRepository.save(item);
        }

        for(int i=6;i<=10;i++){
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SOLD_OUT);
            item.setStockNumber(0);
            itemRepository.save(item);
        }
    }
    @Test
    @DisplayName("상품 QueryDsl 조회 테스트2")
    public void 상품_QueryDsl_조회_테스트2() {
        this.createItemList2();

        /**
         * BooleanBuilder는 쿼리에 들어갈 조건을 만들어주는 빌더
         * Predicate를 구현하고 있으며 메소드 체인 형식으로 사용할 수 있다.
         */
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QItem item = QItem.item;
        String itemDetail = "테스트 상품 상세 설명";
        int price = 10003;
        String itemSellStat = "SELL";

        /**
         * 필요한 상품을 조회하는데 필요한 "and" 조건을 추가
         * 아래 소스에서 상품의 판매상태가 SELL일 때만 booleanBuilder에 판매상태 조건을 동적으로 추가
         */
        booleanBuilder.and(item.itemDetail.like("%" + itemDetail + "%")); // itemDetail에 "테스트 상품 상세 설명"이 포함
        booleanBuilder.and(item.price.gt(price)); // price > 10003
        System.out.println(ItemSellStatus.SELL);
        if(StringUtils.equals(itemSellStat, ItemSellStatus.SELL)){
            booleanBuilder.and(item.itemSellStatus.eq(ItemSellStatus.SELL)); // ItemSellStatus가 SELL 상태인 경우
        }

        /**
         * 데이터를 페이징해 조회하도록 PageRequest.of() 메소드를 이용해 Pageable 객체를 생성
         * 첫 번쨰 인자는 조회할 페이지 번호, 두 번째 인자는 한 페이지당 조회할 데이터 개수
         */
        Pageable pageable = PageRequest.of(0, 5); // 0번 페이지에서 5개
        /**
         * 조건에 맞는 데이터를 Page 객체로 받아옴
         */
        Page<Item> itemPagingResult = itemRepository.findAll(booleanBuilder, pageable);
        System.out.println("total elements : " + itemPagingResult.getTotalElements());

        List<Item> resultItemList = itemPagingResult.getContent();
        for(Item resultItem: resultItemList){
            System.out.println(resultItem.toString());
        }
    }



}