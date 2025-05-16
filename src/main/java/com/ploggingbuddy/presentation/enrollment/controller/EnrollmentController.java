package com.ploggingbuddy.presentation.enrollment.controller;

import com.ploggingbuddy.application.enrollment.PostEnrollmentUseCase;
import com.ploggingbuddy.domain.member.entity.Member;
import com.ploggingbuddy.security.aop.CurrentMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enroll")
@RequiredArgsConstructor
public class EnrollmentController {
    private final PostEnrollmentUseCase postEnrollmentUseCase;

    @PostMapping("/{postId}")
    public ResponseEntity<Void> postEnrollment(
            @CurrentMember Member member,
            @PathVariable Long postId) {
        postEnrollmentUseCase.execute(postId, member.getId());
        return ResponseEntity.ok().build();
    }
}
