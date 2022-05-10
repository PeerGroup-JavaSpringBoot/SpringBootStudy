package dev.yoon.shop.domain.member.exception;


import dev.yoon.shop.global.error.exception.BusinessException;
import dev.yoon.shop.global.error.exception.ErrorCode;

public class EmailDuplicateException extends BusinessException {

    public EmailDuplicateException() {
        super(ErrorCode.ALREADY_REGISTERED_MEMBER.getMessage());
    }
}
