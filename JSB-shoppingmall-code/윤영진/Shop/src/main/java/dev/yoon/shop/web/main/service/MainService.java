package dev.yoon.shop.web.main.service;

import dev.yoon.shop.web.main.dto.MainItemDto;
import dev.yoon.shop.web.main.repository.MainItemRepository;
import dev.yoon.shop.web.manageitem.dto.ItemSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MainService {

    private final MainItemRepository mainItemRepository;

    public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        return mainItemRepository.findMainItemDto(itemSearchDto, pageable);

    }
}
