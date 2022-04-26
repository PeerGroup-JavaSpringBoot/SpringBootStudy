package eci.server.ItemModule.entity.member;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberRoleId implements Serializable {

    private Member member;
    private Role role;

}