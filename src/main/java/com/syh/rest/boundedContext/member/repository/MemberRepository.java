package com.syh.rest.boundedContext.member.repository;

import com.syh.rest.boundedContext.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member , Long> {
    Optional<Member> findByUsername(String username);

}
