package dev.yoon.shop.web.itemdtl.service;

import dev.yoon.shop.domain.item.entity.Item;
import dev.yoon.shop.domain.item.service.ItemService;
import dev.yoon.shop.domain.itemimg.entity.ItemImage;
import dev.yoon.shop.domain.itemimg.service.ItemImageService;
import dev.yoon.shop.domain.member.application.MemberService;
import dev.yoon.shop.domain.orderitem.entity.OrderItem;
import dev.yoon.shop.web.itemdtl.dto.ItemDtlDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ItemDtlService {

    private final ItemService itemService;
    private final ItemImageService itemImageService;

    public ItemDtlDto getItemDtl(Long itemId) {

        Item item = itemService.getItemById(itemId);

        List<ItemImage> itemImages = itemImageService.getItemImageByItemId(itemId);

        ItemDtlDto itemDtlDto = ItemDtlDto.of(item, itemImages);

        return itemDtlDto;
    }


}
