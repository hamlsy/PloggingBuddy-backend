package com.ploggingbuddy.application.gathering;

import com.ploggingbuddy.application.validator.GatheringValidator;
import com.ploggingbuddy.domain.enrollment.service.EnrollmentService;
import com.ploggingbuddy.domain.gathering.entity.GatheringStatus;
import com.ploggingbuddy.domain.gathering.service.GatheringService;
import com.ploggingbuddy.global.annotation.usecase.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class FinishGatheringUseCase {
    private final GatheringService gatheringService;
    private final GatheringValidator gatheringValidator;
    private final EnrollmentService enrollmentService;

    public void execute(Long postId, Long memberId) {
        gatheringValidator.validateEnrollableByPostId(postId);

        Long enrolledCount = enrollmentService.getEnrolledCount(postId);
        gatheringService.updatePostStatus(postId, null, memberId, enrolledCount);
    }
}
