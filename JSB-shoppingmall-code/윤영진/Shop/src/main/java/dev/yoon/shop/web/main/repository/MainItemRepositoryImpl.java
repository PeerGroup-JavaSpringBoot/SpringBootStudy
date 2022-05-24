package dev.yoon.shop.web.main.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.yoon.shop.domain.item.constant.ItemSellStatus;
import dev.yoon.shop.domain.item.entity.QItem;
import dev.yoon.shop.domain.itemimg.entity.QItemImage;
import dev.yoon.shop.web.main.dto.MainItemDto;
import dev.yoon.shop.web.main.dto.QMainItemDto;
import dev.yoon.shop.web.manageitem.dto.ItemSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class MainItemRepositoryImpl implements MainItemRepository {

    private JPAQueryFactory queryFactory;

    public MainItemRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression searchByLike(String searchQuery) {

        return StringUtils.isEmpty(searchQuery) ? null : QItem.item.itemNm.like("%" + searchQuery + "%");

    }

    @Override
    public Page<MainItemDto> findMainItemDto(ItemSearchDto itemSearchDto, Pageable pageable) {
        QItem qItem = QItem.item;
        QItemImage qItemImage = QItemImage.itemImage;

        List<MainItemDto> results = queryFactory
                .select(
                        new QMainItemDto(
                                qItem.id,
                                qItem.itemNm,
                                qItem.itemDetail,
                                qItemImage.imgUrl,
                                qItem.price
                        )
                )
                .from(qItemImage)
                .join(qItemImage.item, qItem)
                .where(
                        qItem.stockNumber.ne(0),
                        qItem.itemSellStatus.ne(ItemSellStatus.SOLD_OUT),
                        qItemImage.isRepImg.eq(true),
                        searchByLike(itemSearchDto.getSearchQuery())
                )
                .orderBy(qItem.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        int totalSize = queryFactory
                .selectFrom(qItem)
                .where(
                        qItem.stockNumber.ne(0),
                        qItem.itemSellStatus.ne(ItemSellStatus.SOLD_OUT),
                        searchByLike(itemSearchDto.getSearchQuery())
                )
                .fetch().size();

        return new PageImpl<>(results, pageable, totalSize);
    }
}
