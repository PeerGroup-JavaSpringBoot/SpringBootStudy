package dev.yoon.shop.web.adminitem.service;


import dev.yoon.shop.domain.item.entity.Item;
import dev.yoon.shop.domain.item.service.ItemService;
import dev.yoon.shop.domain.itemimg.entity.ItemImage;
import dev.yoon.shop.domain.itemimg.service.ItemImageService;
import dev.yoon.shop.domain.member.application.MemberService;
import dev.yoon.shop.domain.member.entity.Member;
import dev.yoon.shop.web.adminitem.dto.ItemFormDto;
import dev.yoon.shop.web.adminitem.dto.UpdateItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminItemService {

    private final ItemService itemService;
    private final MemberService memberService;
    private final ItemImageService itemImageService;


    @Transactional
    public Item saveItem(ItemFormDto dto, String email) throws IOException {

        // 회원 조회
        Member member = memberService.getMemberByEmail(email);

        // 상품 등록
        Item item = dto.toEntity();
        Item saveItem = Item.createItem(item, member);
        saveItem = itemService.saveItem(saveItem);

        // 상품 이미지 등록
        itemImageService.saveItemImages(saveItem, dto.getItemImageFiles());

        return saveItem;
    }

    public UpdateItemDto getUpdateItemDto(Long itemId) {
        List<ItemImage> itemImages = itemImageService.getItemImageByItemId(itemId);
        Item item = itemImages.get(0).getItem();
        return UpdateItemDto.of(item, itemImages);
    }

    public List<UpdateItemDto.ItemImageDto> getItemImageDtos(Long itemId) {

        List<ItemImage> itemImages = itemImageService.getItemImageByItemId(itemId);
        return UpdateItemDto.ItemImageDto.of(itemImages);

    }


    @Transactional
    public void updateItem(UpdateItemDto updateItemDto) throws IOException {

        // 상품 업데이트
        updateItemInfo(updateItemDto);

        // 상품 이미지 업데이트
        updateItemImages(updateItemDto);

    }

    private void updateItemInfo(UpdateItemDto updateItemDto) {
        Item updateItem = updateItemDto.toEntity();
        itemService.updateItem(updateItemDto.getItemId(), updateItem);
    }

    private void updateItemImages(UpdateItemDto updateItemDto) throws IOException {

        // 데이터베이스에 저장된 상품 이미지 정보
        List<ItemImage> itemImages = itemImageService.getItemImageByItemId(updateItemDto.getItemId());
        List<String> originalImageNames = updateItemDto.getOriginalImageNames(); // 상품 수정 화면 조회 시에 있던 상품 이미지명 정보
        List<MultipartFile> itemImageFiles = updateItemDto.getItemImageFiles(); // 상품 파일 이미지 정보


        for (int i = 0; i < itemImages.size(); i++) {
            ItemImage itemImage = itemImages.get(i);
            String originalImageName = originalImageNames.get(i);
            MultipartFile itemImageFile = itemImageFiles.get(i);

            if (!itemImageFile.isEmpty()) {  // 기존 파일 수정 or 신규 파일 등록 처리
                itemImageService.updateItemImage(itemImage, itemImageFile);
            } else if (!StringUtils.hasText(originalImageName) &&
                    StringUtils.hasText(itemImage.getOriImgName())) { // 기존 파일 삭제
                itemImageService.deleteItemImage(itemImage);
            }
        }
    }
}
