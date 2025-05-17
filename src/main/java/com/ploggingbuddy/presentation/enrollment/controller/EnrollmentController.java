package com.ploggingbuddy.presentation.enrollment.controller;

import com.ploggingbuddy.application.enrollment.GetEnrolledMemberListUseCase;
import com.ploggingbuddy.application.enrollment.PostEnrollmentUseCase;
import com.ploggingbuddy.domain.member.entity.Member;
import com.ploggingbuddy.presentation.enrollment.dto.response.GetEnrollmentListResponse;
import com.ploggingbuddy.security.aop.CurrentMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enroll")
@RequiredArgsConstructor
public class EnrollmentController {
    private final PostEnrollmentUseCase postEnrollmentUseCase;
    private final GetEnrolledMemberListUseCase getEnrolledMemberListUseCase;

    @PostMapping("/{postId}")
    public ResponseEntity<Void> postEnrollment(
            @CurrentMember Member member,
            @PathVariable Long postId) {
        postEnrollmentUseCase.execute(postId, member.getId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/enrolled-list/{postId}")
    public ResponseEntity<GetEnrollmentListResponse> getEnrolledMemberList(
            @CurrentMember Member member,
            @PathVariable Long postId
    ){
        return ResponseEntity.ok(getEnrolledMemberListUseCase.execute(postId, member.getId()));
    }
}
