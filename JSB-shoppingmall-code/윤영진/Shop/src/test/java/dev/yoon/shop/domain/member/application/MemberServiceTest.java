package dev.yoon.shop.domain.member.application;

import dev.yoon.shop.domain.member.dto.MemberRegisterDto;
import dev.yoon.shop.domain.member.entity.Member;
import dev.yoon.shop.domain.member.exception.EmailDuplicateException;
import dev.yoon.shop.domain.model.Address;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.yml")
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    public Member createMember() {
        MemberRegisterDto memberRegisterDto = new MemberRegisterDto();
        memberRegisterDto.setEmail("test@email.com");
        memberRegisterDto.setName("홍길동");
        memberRegisterDto.setAddress("서울시 송파구 가락동");
        memberRegisterDto.setPassword("1234");

        return memberRegisterDto.toEntity();
    }

    @Test
    @DisplayName("회원가입 테스트")
    public void 회원가입_테스트(){
        Member member = createMember();
        Member savedMember = memberService.saveMember(member);
        assertEquals(member.getEmail(), savedMember.getEmail());
        assertEquals(member.getName(), savedMember.getName());
        assertEquals(member.getAddress(), savedMember.getAddress());
        assertEquals(member.getPassword(), savedMember.getPassword());
        assertEquals(member.getRole(), savedMember.getRole());
    }

    @Test
    @DisplayName("중복 회원가입 테스트")
    public void 중복_회원가입_테스트(){
        Member member1 = createMember();
        Member member2 = createMember();
        memberService.saveMember(member1);

        /**
         * Junit의 Assertion 클래스의 assertThrows 메소드를 이용하면 예외 처리 테스트가 가능, 첫 번째 파라미터에는 발생할 예외 타입을 넣어준다.
         */
        Throwable e = assertThrows(EmailDuplicateException.class, () -> {
            memberService.saveMember(member2);
        });

        /**
         * 발생한 예외 메시지가 예상 결과와 맞는지 검증
         */
        assertEquals("이미 가입된 회원입니다.", e.getMessage());

    }


}