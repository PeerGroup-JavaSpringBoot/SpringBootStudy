package eci.server.ItemModule.repository.item;

import eci.server.ItemModule.dto.item.*;
import org.springframework.data.domain.Page;

/**
 * 쿼리를 구현하는 메소드
 * 검색 조건에 대한 정보가 담긴
 * ItemReadCondition 전달받음
 * 이를 Page로 반환하여
 * 페이징 결과에 대한 각종 정보 확인
 */
public interface CustomItemRepository {
    Page<ItemProjectCreateDto> findAllByCondition(ItemProjectCreateReadCondition cond);

}