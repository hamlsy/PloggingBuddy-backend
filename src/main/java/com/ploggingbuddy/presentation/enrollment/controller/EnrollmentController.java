package com.ploggingbuddy.presentation.enrollment.controller;

import com.ploggingbuddy.application.enrollment.GetEnrolledMemberListUseCase;
import com.ploggingbuddy.application.enrollment.PostEnrollmentUseCase;
import com.ploggingbuddy.domain.member.entity.Member;
import com.ploggingbuddy.presentation.enrollment.dto.response.GetEnrollmentListResponse;
import com.ploggingbuddy.security.aop.CurrentMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enroll")
@RequiredArgsConstructor
@Tag(name = "Enrollment Api", description = "참가 신청 관련 API")
public class EnrollmentController {
    private final PostEnrollmentUseCase postEnrollmentUseCase;
    private final GetEnrolledMemberListUseCase getEnrolledMemberListUseCase;

    @PostMapping("/{postId}")
    @Operation(summary = "참가 신청", description = "모집 게시글에 참가신청하는 api입니다.")
    public ResponseEntity<Void> postEnrollment(
            @CurrentMember Member member,
            @PathVariable Long postId) {
        postEnrollmentUseCase.execute(postId, member.getId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/enrolled-list/{postId}")
    @Operation(summary = "참가 신청자 리스트 조회", description = "모집 게시글에 대한 신청자를 조회하는 api입니다.")
    public ResponseEntity<GetEnrollmentListResponse> getEnrolledMemberList(
            @CurrentMember Member member,
            @PathVariable Long postId
    ){
        return ResponseEntity.ok(getEnrolledMemberListUseCase.execute(postId, member.getId()));
    }
}
