package dev.yoon.basic_community.exception;

import lombok.Getter;

@Getter
public class NameDuplicationException extends RuntimeException {

    private String name;
    public NameDuplicationException(String name) {
        this.name = name;
    }
}
