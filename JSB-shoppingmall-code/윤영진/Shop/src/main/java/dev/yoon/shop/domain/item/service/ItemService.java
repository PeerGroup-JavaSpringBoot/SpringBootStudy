package dev.yoon.shop.domain.item.service;

import dev.yoon.shop.domain.item.entity.Item;
import dev.yoon.shop.domain.item.exception.ItemNotFoundException;
import dev.yoon.shop.domain.item.repository.ItemRepository;
import dev.yoon.shop.global.error.exception.ErrorCode;
import dev.yoon.shop.web.manageitem.dto.ItemSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public Item saveItem(Item item)  {
        return itemRepository.save(item);
    }

    public Item getItemById(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(ErrorCode.ITEM_NOT_EXISTS.getMessage()));
    }

    public Item updateItem(Long itemId, Item updateItem) {
        Item savedItem = getItemById(itemId);
        savedItem.updateItem(updateItem);
        return savedItem;
    }


}
