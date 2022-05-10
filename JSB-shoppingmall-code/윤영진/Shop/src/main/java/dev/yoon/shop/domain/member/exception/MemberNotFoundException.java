package dev.yoon.shop.domain.member.exception;


import dev.yoon.shop.global.error.exception.EntityNotFoundException;
import dev.yoon.shop.global.error.exception.ErrorCode;

public class MemberNotFoundException extends EntityNotFoundException {

    public MemberNotFoundException() {
        super(ErrorCode.MEMBER_NOT_EXISTS);
    }
}
