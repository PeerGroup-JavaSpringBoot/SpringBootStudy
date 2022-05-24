package dev.yoon.shop.domain.item.exception;


import dev.yoon.shop.global.error.exception.BusinessException;

public class OutOfStockException extends BusinessException {

    public OutOfStockException(String msg) {
        super(msg);
    }
}
