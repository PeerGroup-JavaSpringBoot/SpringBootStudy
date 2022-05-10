package dev.yoon.shop.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Address {

    @Transient
    private String value;

    @NotEmpty(message = "주소는 필수 입력 값입니다.")
    @Column(name = "county")
    private String county;

    @NotEmpty(message = "주소는 필수 입력 값입니다.")
    @Column(name = "state")
    private String state;

    @NotEmpty(message = "주소는 필수 입력 값입니다.")
    @Column(name = "city")
    private String city;

    @Builder
    public Address(final String value) {
        String[] s = value.split(" ");
        this.county = s[0];
        this.state = s[1];
        this.city = s[2];
    }

    public String getFullAddress() {
        return value;
//        return String.format("%s %s %s", this.county, this.state, this.city);
    }
}
