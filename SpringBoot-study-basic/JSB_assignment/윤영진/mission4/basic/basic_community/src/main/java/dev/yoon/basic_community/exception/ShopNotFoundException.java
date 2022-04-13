package dev.yoon.basic_community.exception;

public class ShopNotFoundException extends RuntimeException {

    private Long id;

    public ShopNotFoundException(Long shopId) {
        this.id = shopId;
    }
}
