package dev.yoon.challenge_community.dto;

import dev.yoon.challenge_community.domain.Address;
import dev.yoon.challenge_community.domain.Location;
import lombok.*;

import javax.persistence.Embedded;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class AreaDto {

    @Embedded
    private Address address;

    @Embedded
    private Location location;
}
