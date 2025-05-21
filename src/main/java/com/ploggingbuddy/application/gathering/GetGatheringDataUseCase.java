package com.ploggingbuddy.application.gathering;

import com.ploggingbuddy.domain.enrollment.service.EnrollmentService;
import com.ploggingbuddy.domain.gathering.entity.Gathering;
import com.ploggingbuddy.domain.gathering.service.GatheringService;
import com.ploggingbuddy.domain.member.service.MemberService;
import com.ploggingbuddy.domain.postImage.service.PostImageService;
import com.ploggingbuddy.global.annotation.usecase.UseCase;
import com.ploggingbuddy.presentation.gathering.dto.response.GetGatheringDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
@Transactional
@RequiredArgsConstructor
public class GetGatheringDataUseCase {
    private final GatheringService gatheringService;
    private final MemberService memberService;
    private final PostImageService postImageService;
    private final EnrollmentService enrollmentService;

    public GetGatheringDetailResponse execute(Long postId) {

        Gathering gathering = gatheringService.getGatheringData(postId);
        Long enrolledCount = enrollmentService.getEnrolledCount(postId);
        String leadUserNickname = memberService.getNicknameById(gathering.getLeadUserId());
        List<String> imageList = postImageService.getPostImageList(postId);

        return GetGatheringDetailResponse.of(gathering, enrolledCount, leadUserNickname, imageList);
    }
}
