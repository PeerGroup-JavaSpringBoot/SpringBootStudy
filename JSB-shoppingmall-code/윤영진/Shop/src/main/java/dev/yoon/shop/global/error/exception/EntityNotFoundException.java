package dev.yoon.shop.global.error.exception;

public class EntityNotFoundException extends BusinessException{


    public EntityNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public EntityNotFoundException(String msg) {
        super(msg);
    }
}
