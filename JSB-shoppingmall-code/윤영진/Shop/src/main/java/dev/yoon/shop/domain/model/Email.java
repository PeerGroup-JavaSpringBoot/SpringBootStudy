package dev.yoon.shop.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@JsonIgnoreProperties(value = {"id","host"})
@ToString
public class Email {

    @javax.validation.constraints.Email(message = "NOT_VALID_EMAIL")
    @Column(name = "email", nullable = false, unique = true, length = 50)
    private String value;

    @Builder
    public Email(String value) {
        this.value = value;
    }

    public static Email of(String email) {
        return new Email(email);
    }

    public String getHost() {
        int index = value.indexOf("@");
        return value.substring(index);
    }

    public String getId() {
        int index = value.indexOf("@");
        return value.substring(0, index);
    }
}

