package dev.yoon.shop.web.main.repository;

import dev.yoon.shop.web.main.dto.MainItemDto;
import dev.yoon.shop.web.manageitem.dto.ItemSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MainItemRepository {

    Page<MainItemDto> findMainItemDto(ItemSearchDto itemSearchDto, Pageable pageable);

}
