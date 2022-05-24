package dev.yoon.shop.domain.member.entity;

import dev.yoon.shop.domain.member.repository.MemberRepository;
import dev.yoon.shop.domain.model.Password;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.yml")
class MemberTest {

    @Autowired
    MemberRepository memberRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("Auditing 테스트")
    @WithMockUser(username = "yoon", roles = "USER")
    public void auditingTest() {

        Member member = Member.builder()
                .email("dudwls143@gmail.com")
                .password(Password.builder()
                        .value("password")
                        .build())
                .build();
        memberRepository.save(member);

        em.flush();
        em.clear();

        Member member1 = memberRepository.findById(member.getId())
                .orElseThrow(EntityExistsException::new);

        System.out.println("register time : " + member1.getRegTime());
        System.out.println("update time : " + member1.getUpdateTime());
        System.out.println("create member : " + member1.getCreatedBy());
        System.out.println("modify member : " + member1.getModifiedBy());

    }

}