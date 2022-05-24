package dev.yoon.shop.domain.itemimg.repository;

import dev.yoon.shop.domain.itemimg.entity.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {

    @Query("select im from ItemImage im join fetch im.item i where i.id=:itemId")
    List<ItemImage> findItemImageByItemId(@Param("itemId") Long itemId);

//    @Query("select im from ItemImage im join fetch im.item i where i.id=:itemId AND im.isRepImg=true")
//    Optional<ItemImage> findByItemIdAndIsRepImgTrue(Long itemId);

    Optional<ItemImage> findByItemIdAndIsRepImg(Long itemId, boolean isRepImage);
}
