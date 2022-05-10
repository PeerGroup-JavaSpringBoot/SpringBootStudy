package dev.yoon.shop.domain.member.entity;

import dev.yoon.shop.domain.base.BaseTimeEntity;
import dev.yoon.shop.domain.member.constant.Role;
import dev.yoon.shop.domain.model.Address;
import dev.yoon.shop.domain.model.Email;
import dev.yoon.shop.domain.model.Password;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Member extends BaseTimeEntity {

    @Id
    @Column(name="member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Embedded
    private Email email;

    @Embedded
    private Password password;

//    @Embedded
    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Member(String email, String name, Password password, String address, Role role) {
        this.email = Email.of(email);
        this.name = name;
        this.password = password;
        this.address = address;
        this.role = role;
    }

    public static Member createMember(Member member) {
        return Member.builder()
                .email(member.email.getValue())
                .name(member.name)
                .password(member.password)
                .address(member.address)
                .role(Role.ADMIN)
                .build();
    }

}
