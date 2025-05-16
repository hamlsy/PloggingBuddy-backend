package com.ploggingbuddy.presentation.member.controller;

import com.ploggingbuddy.application.member.UpdateMemberAddressUseCase;
import com.ploggingbuddy.application.member.UpdateMemberDescriptionUseCase;
import com.ploggingbuddy.application.member.UpdateMemberNicknameUseCase;
import com.ploggingbuddy.domain.member.entity.Member;
import com.ploggingbuddy.presentation.member.dto.request.MemberRequest;
import com.ploggingbuddy.security.aop.CurrentMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Tag(name = "Member Api", description = "회원 API")
public class MemberController {

    private final UpdateMemberNicknameUseCase updateMemberNicknameUseCase;
     private final UpdateMemberDescriptionUseCase updateMemberDescriptionUseCase;
     private final UpdateMemberAddressUseCase updateMemberAddressUseCase;

    @GetMapping("/test")
    public ResponseEntity<?> testMember(@CurrentMember Member member) {
        return ResponseEntity.ok(member.getNickname() + "" + member.getEmail());
    }

    @Operation(summary = "닉네임 수정", description = "회원 닉네임을 수정합니다.")
    @PostMapping("/nickname")
    public ResponseEntity<?> updateNickname(@CurrentMember Member member, @RequestBody MemberRequest.UpdateNickname request) {
        updateMemberNicknameUseCase.execute(member, request);
        return ResponseEntity.ok("Nickname updated successfully.");
    }

    @Operation(summary = "자기소개 수정", description = "회원 자기소개를 수정합니다.")
    @PostMapping("/description")
    public ResponseEntity<?> updateDescription(@CurrentMember Member member, @RequestBody MemberRequest.UpdateDescription request) {
         updateMemberDescriptionUseCase.execute(member, request);
        return ResponseEntity.ok("Description updated successfully.");
    }

    @Operation(summary = "주소 수정", description = "회원 주소를 수정합니다.")
    @PostMapping("/address")
    public ResponseEntity<?> updateAddress(@CurrentMember Member member, @RequestBody MemberRequest.UpdateAddress request) {
         updateMemberAddressUseCase.execute(member, request);
        return ResponseEntity.ok("Address updated successfully.");
    }

}
