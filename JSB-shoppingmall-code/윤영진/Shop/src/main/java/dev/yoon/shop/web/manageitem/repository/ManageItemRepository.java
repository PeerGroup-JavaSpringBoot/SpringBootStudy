package dev.yoon.shop.web.manageitem.repository;

import dev.yoon.shop.domain.item.entity.Item;
import dev.yoon.shop.web.manageitem.dto.ItemSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ManageItemRepository {

    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}
