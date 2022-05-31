package dev.yoon.shop.global.error.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // 인증
    ALREADY_REGISTERED_MEMBER(400, "이미 가입된 회원입니다."),
    MISMATCHED_PASSWORD(401, "패스워드가 일치하지 않습니다."),
    LOGIN_ERROR(401, "아이디 또는 비밀번호를 확인해주세요"),

    // 회원
    MEMBER_NOT_EXISTS(400, "해당 회원은 존재하지 않습니다."),

    // 상품
    REQUIRED_REPRESENT_IMAGE(400,"첫번째 상품 이미지는 필수 입력 값 입니다."),
    ITEM_NOT_EXISTS(400, "해당 아이템을 찾을 수 없습니다."),

    OUT_OF_STOCK(400, "상품의 재고가 부족합니다."),
    // 배송
    DELIVERY_NOT_EXISTS(400, "배송지를 찾을 수 없습니다."),

    // 주문
    ORDER_NOT_EXISTS(400, "주문정보를 찾을 수 없습니다."),

    // 상품 이미지
    ITEM_IMAGE_NOT_EXISTS(400, "해당 아이템의 이미지를 찾을 수 없습니다."),

    NOT_AUTHENTICATION_CANCEL_ORDER(403, "주문 취소 권한이 없습니다");



    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

    private int status;
    private String message;

}