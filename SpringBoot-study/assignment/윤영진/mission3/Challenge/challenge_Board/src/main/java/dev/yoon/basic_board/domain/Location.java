package dev.yoon.basic_board.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Location {

    private Long latitude;

    private Long longtitude;

}
