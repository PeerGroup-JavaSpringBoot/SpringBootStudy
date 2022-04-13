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
public class Address {

    private String province;

    private String city;

    private String street;

    @Builder
    public Address(String province, String city, String street) {
        this.province = province;
        this.city = city;
        this.street = street;
    }

}
