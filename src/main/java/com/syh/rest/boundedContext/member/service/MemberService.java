package com.syh.rest.boundedContext.member.service;

import com.syh.rest.base.jwt.JwtProvider;
import com.syh.rest.boundedContext.member.entity.Member;
import com.syh.rest.boundedContext.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public Member join(String username, String password, String email) {
        Member member = Member.builder()
                .username(username)
                .password(password)
                .email(email)
                .build();

        memberRepository.save(member);

        return member;
    }

    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    public String getAccessToken(String username, String password) {
        Member member = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("no user"));

        if (member == null)
            return null;

        if (!passwordEncoder.matches(password, member.getPassword()))
            return null;

        return jwtProvider.genToken(member.toClaims(), 60 * 60 * 24 * 365);
    }
}
