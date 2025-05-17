package com.ploggingbuddy.application.enrollment;

import com.ploggingbuddy.application.validator.GatheringValidator;
import com.ploggingbuddy.domain.enrollment.service.EnrollmentService;
import com.ploggingbuddy.global.annotation.usecase.UseCase;
import com.ploggingbuddy.presentation.enrollment.dto.response.GetEnrollmentListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class GetEnrolledMemberListUseCase {
    private final EnrollmentService enrollmentService;
    private final GatheringValidator gatheringValidator;

    public GetEnrollmentListResponse execute(Long postId, Long memberId) {
        gatheringValidator.validateGatheringPostIdExist(postId);
        gatheringValidator.validateWriteUser(memberId, postId);
        return new GetEnrollmentListResponse(enrollmentService.getEnrollmentList(postId));
    }
}
