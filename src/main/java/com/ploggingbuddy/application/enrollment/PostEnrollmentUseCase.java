package com.ploggingbuddy.application.enrollment;

import com.ploggingbuddy.application.validator.GatheringValidator;
import com.ploggingbuddy.domain.enrollment.service.EnrollmentService;
import com.ploggingbuddy.global.annotation.usecase.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class PostEnrollmentUseCase {
    private final EnrollmentService enrollmentService;
    private final GatheringValidator gatheringValidator;

    public void execute(Long postId, Long userId) {
        gatheringValidator.validateGatheringPostIdExist(postId);
        gatheringValidator.validateEnrollableByPostId(postId);
        enrollmentService.saveEnrollment(postId, userId);
    }
}
