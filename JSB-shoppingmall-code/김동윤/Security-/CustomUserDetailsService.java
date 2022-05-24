package eci.server.config.security;

import eci.server.ItemModule.entity.member.Member;
import eci.server.ItemModule.entity.member.MemberRole;
import eci.server.ItemModule.entity.member.Role;
import eci.server.ItemModule.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    /**
     * 인증된 사용자의 정보를 CustomUserDetails로 반환
     */
    private final MemberRepository memberRepository;

    @Override
    /**
     * 유저의 Role, RoleType 확인
     */
    public CustomUserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        /**
         * 사용자의 id 값으로 사용자 정보 조회
         */
        Member member = memberRepository.findById(Long.valueOf(userId))
                .orElseGet(() -> new Member(null, null, null, null, null ,List.of(), null));
        return new CustomUserDetails(
                String.valueOf(member.getId()),
                member.getRoles().stream().map(MemberRole::getRole)
                        .map(Role::getRoleType)
                        .map(Enum::toString)
                        //권한 등급은 String 인식, Enum 타입 RoleType을 String 변환
                        .map(SimpleGrantedAuthority::new).collect(Collectors.toSet())
                //권한 등급을 GrantedAuthority 인터페이스로 받음
        );
    }
}