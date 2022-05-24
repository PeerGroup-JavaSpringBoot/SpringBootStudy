package dev.yoon.shop.domain.model;

import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Password {

    @Column(name = "password", nullable = false, length = 200)
    private String value;

    @Builder
    public Password(final String value) {
        this.value = encodePassword(value);
    }

    private String encodePassword(final String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

}
