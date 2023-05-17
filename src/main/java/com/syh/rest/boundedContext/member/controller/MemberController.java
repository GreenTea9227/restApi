package com.syh.rest.boundedContext.member.controller;

import com.syh.rest.boundedContext.member.entity.Member;
import com.syh.rest.boundedContext.member.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/member",produces = APPLICATION_JSON_VALUE,consumes = APPLICATION_JSON_VALUE)
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

    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse resp) {
        String accessToken = memberService.getAccessToken(loginRequest.getUsername(), loginRequest.getPassword());

        resp.addHeader("Authentication",accessToken);

        return "응답본문";
    }
}
