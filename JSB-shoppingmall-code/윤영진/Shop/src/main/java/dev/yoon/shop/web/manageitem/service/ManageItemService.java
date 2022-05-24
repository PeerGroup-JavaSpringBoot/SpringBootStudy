package dev.yoon.shop.web.manageitem.service;

import dev.yoon.shop.domain.item.entity.Item;
import dev.yoon.shop.domain.item.repository.ItemRepository;
import dev.yoon.shop.domain.item.service.ItemService;
import dev.yoon.shop.web.manageitem.dto.ItemSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ManageItemService {

    private final ItemRepository itemRepository;

    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable){
        return itemRepository.getAdminItemPage(itemSearchDto, pageable);
    }

}
