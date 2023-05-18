package com.syh.rest.boundedContext.member.controller;

import com.syh.rest.base.rsData.RsData;
import com.syh.rest.boundedContext.member.dto.MemberDto;
import com.syh.rest.boundedContext.member.entity.Member;
import com.syh.rest.boundedContext.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/member", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
@Tag(name = "ApiV1MemberController" , description = "로그인, 로그인 된 사용자 정보")
public class MemberController {

    private final MemberService memberService;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginRequest {
        @NotBlank
        private String username;
        @NotBlank
        private String password;
    }

    @Getter
    @RequiredArgsConstructor
    public static class LoginResponse {
        private final String accessToken;
    }

    @PostMapping("/login")
    @Operation(summary = "로그인, 액세스 토큰 발급")
    public RsData<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        String accessToken = memberService.getAccessToken(loginRequest.getUsername(), loginRequest.getPassword());

        return RsData.of(
                "S-1",
                "엑세스토큰이 생성되었습니다.",
                new LoginResponse(accessToken)
        );
    }

    @Getter
    @AllArgsConstructor
    public static class MeResponse {
        private final MemberDto member;
    }

    @GetMapping(value = "/me", consumes = ALL_VALUE)
    @Operation(summary = "로그인된 사용자 정보", security = @SecurityRequirement(name = "bearerAuth"))
    public RsData<MeResponse> me(@AuthenticationPrincipal User user) {
        Member member = memberService.findByUsername(user.getUsername()).get();

        return RsData.of(
                "S-1",
                "성공",
                new MeResponse(MemberDto.of(member))
        );
    }
}
