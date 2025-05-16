package com.ploggingbuddy.presentation.member.controller;

import com.ploggingbuddy.domain.member.entity.Member;
import com.ploggingbuddy.security.aop.CurrentMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    @GetMapping("/test")
    public ResponseEntity<?> testMember(@CurrentMember Member member) {
        return ResponseEntity.ok(member.getNickname() + "" + member.getEmail());
    }

}
