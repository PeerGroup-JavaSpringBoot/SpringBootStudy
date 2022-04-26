package eci.server.ItemModule.repository.item;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import eci.server.ItemModule.dto.item.ItemReadCondition;
import eci.server.ItemModule.dto.item.ItemSimpleDto;
import eci.server.ItemModule.entity.item.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;

import static com.querydsl.core.types.Projections.constructor;

import static eci.server.ItemModule.entity.item.QItem.item;

import static eci.server.ItemModule.entity.material.QItemMaterial.itemMaterial;
import static eci.server.ItemModule.entity.item.QImage.image;
import static eci.server.ItemModule.entity.item.QAttachment.attachment;

/**
 * CustomItemRepository의 구현체
 */
@Transactional(readOnly = true) // 1
public class CustomItemRepositoryImpl extends QuerydslRepositorySupport implements CustomItemRepository { // 2

    private final JPAQueryFactory jpaQueryFactory; // 3


    public CustomItemRepositoryImpl(JPAQueryFactory jpaQueryFactory) { // 4
        super(Item.class);

        this.jpaQueryFactory = jpaQueryFactory;

    }

    /**
     *  전달받은 ItemReadCondition(검색)으로
     *  Predicate와 PageRequest를 생성 &
     *  조회 쿼리와 카운트 쿼리를 수행한 결과를 Page의 구현체로 반환
     * @param cond
     * @return Page
     */
    @Override
    public Page<ItemSimpleDto> findAllByCondition(ItemReadCondition cond) { // 5
        Pageable pageable = PageRequest.of(cond.getPage(), cond.getSize());
        Predicate predicate = createPredicate(cond);
        System.out.println(fetchAll(predicate, pageable).toString());
        return new PageImpl<>(fetchAll(predicate, pageable), pageable, fetchCount(predicate));
    }

//    EntityManager em;
//    JPAQueryFactory jpaQueryFactory1 = new JPAQueryFactory(em);
//    JPAQuery<String> query = jpaQueryFactory1.select(
//            itemMaterial.material.name
//    ).from(itemMaterial.material)
//            .where(
//                    item.id.eq(itemMaterial.item.id)
//            );

    /**
     * 아이템 목록을 ItemSimpleDto로 조회한 결과 반환환
     * @param predicate
     * @param pageable
     * @return getQuerydsl().applyPagination (페이징 적용 쿼리)
     */
    private List<ItemSimpleDto> fetchAll(Predicate predicate, Pageable pageable) {

        List<ItemSimpleDto> itemSimpleDtos = getQuerydsl().applyPagination(
                pageable,
                jpaQueryFactory
                        .select(constructor
                                (
                                        ItemSimpleDto.class,
                                        item.id,
                                        item.name,
                                        item.type,
                                        item.itemNumber,

                                        item.width,
                                        item.height,
                                        item.weight,

                                        item.member.username,

                                        itemMaterial.material.name,
                                        //itemMaterial.material.name,

                                        item.color.color,

                                        image.imageaddress,

                                        attachment.attachmentaddress,//이건 리스트로 와야하는디..

                                        item.createdAt



                                        )
                        )
                        .from(item)
                        //.join(itemMaterial).on(item.id.eq(itemMaterial.item.id))

                        .join(itemMaterial).on(item.id.eq(itemMaterial.item.id))
                        //jqpl은 연관관계 없으면 직접 못하고 join on으로 해줘야 함

                        .join(image).on(item.id.eq(image.item.id))

                        .join(attachment).on(
                                item.id.
                                        eq(attachment.item.id).
                                        and(attachment.deleted. //삭제 안된 파일만 보여주기
                                                eq(false))
                        )

                        .join(item.color) //아이템 색깔 조회 위해 Color와 조인
                        .join(item.member) //아이템 작성자 닉네임 조회 위해 Member와 조인

                        .where(predicate)
                        .orderBy(item.id.desc())
        ).fetch(); //리스트 반환

        return itemSimpleDtos;
    }

    private Long fetchCount(Predicate predicate) { // 7
        return jpaQueryFactory.select(item.count()).from(item).where(predicate).fetchOne();
    }

    private Predicate createPredicate(ItemReadCondition cond) { // 8
        return new BooleanBuilder()
                .and(orConditionsByEqMemberIds(cond.getMemberId()));
    }

    private Predicate orConditionsByEqMemberIds(List<Long> memberIds) { // 10
        return orConditions(memberIds, item.member.id::eq);
    }

    private <T> Predicate orConditions(List<T> values, Function<T, BooleanExpression> term) { // 11
        return values.stream()
                .map(term)
                .reduce(BooleanExpression::or)
                .orElse(null);
    }
}