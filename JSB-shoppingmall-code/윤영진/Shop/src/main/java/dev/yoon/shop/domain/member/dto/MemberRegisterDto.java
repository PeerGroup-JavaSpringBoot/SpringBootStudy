package dev.yoon.shop.domain.member.dto;

import dev.yoon.shop.domain.member.constant.Role;
import dev.yoon.shop.domain.member.entity.Member;
import dev.yoon.shop.domain.model.Address;
import dev.yoon.shop.domain.model.Password;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberRegisterDto {

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    @NotEmpty(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    @Length(min=8, max=16, message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요")
    private String password;

    private String address;

    public Member toEntity() {
        return Member.builder()
                .email(this.email)
                .name(this.name)
                .password(
                        Password.builder()
                                .value(this.getPassword())
                                .build()
                )
                .address(this.address)
//                .address(
//                        Address.builder()
//                                .value(this.getAddress())
//                                .build()
//                )
                .role(Role.ADMIN)
                .build();
    }
}
