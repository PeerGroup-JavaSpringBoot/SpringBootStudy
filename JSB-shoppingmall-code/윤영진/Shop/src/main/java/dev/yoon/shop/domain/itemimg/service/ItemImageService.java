package dev.yoon.shop.domain.itemimg.service;

import dev.yoon.shop.domain.item.entity.Item;
import dev.yoon.shop.domain.itemimg.entity.ItemImage;
import dev.yoon.shop.domain.itemimg.exception.ItemImageNotFoundException;
import dev.yoon.shop.domain.itemimg.repository.ItemImageRepository;
import dev.yoon.shop.infra.FileService;
import dev.yoon.shop.infra.UploadFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemImageService {

    private final ItemImageRepository itemImageRepository;
    private final FileService fileService;
    private final String IMAGE_URL_PREFIX = "/images/";

    public void saveItemImages(Item item, List<MultipartFile> itemImgFileList) throws IOException {

        for (int i = 0; i < itemImgFileList.size(); i++) {
            Boolean isRepImage = i == 0;
            saveItemImage(item, itemImgFileList.get(i), isRepImage);
        }

    }

    private void saveItemImage(Item item, MultipartFile itemImgFile, Boolean isRepImage) throws IOException {

        UploadFile uploadFile = fileService.storeFile(itemImgFile);
        String storeFileName = uploadFile != null ? uploadFile.getStoreFileName() : "";
        String originFileName = uploadFile != null ? uploadFile.getOriginalFileName() : "";
        /**
         * images라는 경로로 요청이 올 경우 설정 파일에서 등록한 path 아래에서 파일을 찾을 수 있도록 하기 위함
         */
        String imageUrl = uploadFile != null ? IMAGE_URL_PREFIX + storeFileName : "";

        ItemImage itemImage = ItemImage.builder()
                .imgName(storeFileName)
                .imgUrl(imageUrl)
                .originImgName(originFileName)
                .isRepImg(isRepImage)
                .build();

        ItemImage saveItemImage = ItemImage.createItemImage(itemImage, item);
        itemImageRepository.save(saveItemImage);

    }

    public List<ItemImage> getItemImageByItemId(Long itemId) {
        return itemImageRepository.findItemImageByItemId(itemId);
    }

    @Transactional
    public void updateItemImage(ItemImage itemImage, MultipartFile itemImageFile) throws IOException {

        // 기존 상품 이미지 파일이 존재하는 경우 파일 삭제
        if (StringUtils.hasText(itemImage.getImgName())) {
            fileService.deleteFile(fileService.getFullFileUploadPath(itemImage.getImgName()));
        }

        // 새로운 이미지 파일 등록
        UploadFile uploadFile = fileService.storeFile(itemImageFile);
        String originalFilename = uploadFile.getOriginalFileName();
        String storeFileName = uploadFile.getStoreFileName();
        String imageUrl = IMAGE_URL_PREFIX + storeFileName;

        // 상품 이미지 파일 정보 업데이트
        itemImage.updateItemImg(originalFilename, storeFileName, imageUrl);
    }

    @Transactional
    public void deleteItemImage(ItemImage itemImage) {
        // 기존 이미지 파일 삭제
        String fileUploadUrl = fileService.getFullFileUploadPath(itemImage.getImgName());
        fileService.deleteFile(fileUploadUrl);
        // 이미지 정보 초기화
        itemImage.initImageInfo();
    }

    public ItemImage getItemImageByItemIdAndIsRepImg(Long itemId, boolean isRepImage) {

        return itemImageRepository.findByItemIdAndIsRepImg(itemId, isRepImage)
                .orElseThrow(()-> new ItemImageNotFoundException());

    }

}
