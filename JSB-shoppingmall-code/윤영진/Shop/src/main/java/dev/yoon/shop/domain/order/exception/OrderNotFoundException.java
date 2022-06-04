package dev.yoon.shop.domain.order.exception;

import dev.yoon.shop.global.error.exception.EntityNotFoundException;
import dev.yoon.shop.global.error.exception.ErrorCode;

public class OrderNotFoundException extends EntityNotFoundException {

    public OrderNotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
