package dev.yoon.shop.domain.itemimg.exception;


import dev.yoon.shop.global.error.exception.EntityNotFoundException;
import dev.yoon.shop.global.error.exception.ErrorCode;

public class ItemImageNotFoundException extends EntityNotFoundException {
    public ItemImageNotFoundException() {
        super(ErrorCode.ITEM_IMAGE_NOT_EXISTS);
    }
}
