package dev.yoon.shop.domain.member.application;

import dev.yoon.shop.domain.member.entity.Member;
import dev.yoon.shop.domain.member.exception.EmailDuplicateException;
import dev.yoon.shop.domain.member.repository.MemberRepository;
import dev.yoon.shop.domain.model.Email;
import dev.yoon.shop.global.config.security.UserDetailsImpl;
import dev.yoon.shop.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member saveMember(Member member){
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member){
        Optional<Member> findMember = memberRepository.findByEmail(member.getEmail());
        if(findMember.isPresent()){
            throw new EmailDuplicateException();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmail(Email.of(email))
                .orElseThrow(() -> new UsernameNotFoundException(ErrorCode.LOGIN_ERROR.getMessage()));

        return new UserDetailsImpl(member);
    }
}
