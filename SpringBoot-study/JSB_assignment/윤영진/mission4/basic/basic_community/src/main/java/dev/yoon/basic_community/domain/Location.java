package dev.yoon.basic_community.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor
@ToString
public class Location {

    private Double latitude;

    private Double longtitude;

    @Builder
    public Location(Double latitude, Double longtitude) {
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

}
