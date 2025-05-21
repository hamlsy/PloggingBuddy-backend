package com.ploggingbuddy.application.gathering;

import com.ploggingbuddy.domain.gathering.entity.Gathering;
import com.ploggingbuddy.domain.gathering.service.GatheringService;
import com.ploggingbuddy.domain.member.entity.Member;
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

    public GetGatheringDetailResponse execute(Member member, Long postId) {

        Gathering gathering = gatheringService.getGatheringData(postId);
        String leadUserNickname = memberService.getNicknameById(gathering.getLeadUserId());
        List<String> imageList = postImageService.getPostImageList(postId);
        boolean isAuthor = member.getId().equals(gathering.getLeadUserId());
        return GetGatheringDetailResponse.of(gathering, leadUserNickname, imageList, isAuthor);
    }
}
