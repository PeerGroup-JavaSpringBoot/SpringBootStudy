package dev.yoon.shop.domain.item.exception;


import dev.yoon.shop.global.error.exception.EntityNotFoundException;
import dev.yoon.shop.global.error.exception.ErrorCode;

public class ItemNotFoundException extends EntityNotFoundException {

    public ItemNotFoundException(String msg) {
        super(msg);
    }
}
