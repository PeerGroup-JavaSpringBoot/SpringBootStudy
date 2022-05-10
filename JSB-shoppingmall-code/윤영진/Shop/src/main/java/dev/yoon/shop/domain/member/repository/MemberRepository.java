package dev.yoon.shop.domain.member.repository;

import dev.yoon.shop.domain.member.entity.Member;
import dev.yoon.shop.domain.model.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(Email email);
}
