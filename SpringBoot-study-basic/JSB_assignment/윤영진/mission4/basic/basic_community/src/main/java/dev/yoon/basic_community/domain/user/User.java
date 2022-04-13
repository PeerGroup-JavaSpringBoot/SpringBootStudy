package dev.yoon.basic_community.domain.user;

import dev.yoon.basic_community.common.BaseTimeEntity;
import dev.yoon.basic_community.domain.Area;
import dev.yoon.basic_community.dto.UserDto;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Entity
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "community_user")
public class User extends BaseTimeEntity implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    private UserCategory isShopOwner;

    @ManyToOne(
            targetEntity = Area.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "area_id")
    private Area residence;

    @Builder
    public User(String username, String password, Area residence, UserCategory isShopOwner) {
        this.username = username;
        this.residence = residence;
        this.password = password;
        this.isShopOwner = isShopOwner;
    }

    public User(UserDto.SignUpReq dto) {
        this.username = dto.getName();
        this.password = dto.getPassword();
        this.isShopOwner = dto.getUserCategory();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void updateUser(UserDto.Req userDto) {

        this.username = userDto.getName();
        this.isShopOwner = userDto.getUserCategory();

    }

}
