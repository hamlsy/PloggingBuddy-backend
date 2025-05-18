package com.ploggingbuddy.application.member;

import com.ploggingbuddy.domain.gathering.adaptor.GatheringAdaptor;
import com.ploggingbuddy.domain.gathering.entity.Gathering;
import com.ploggingbuddy.domain.member.entity.Member;
import com.ploggingbuddy.global.annotation.usecase.UseCase;
import com.ploggingbuddy.presentation.member.dto.response.MemberResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@UseCase
@Transactional
@RequiredArgsConstructor
public class GetMyInfoUseCase {

    private final GatheringAdaptor gatheringAdaptor;

    //todo 모임 정보 추가
    public MemberResponse execute(Member member) {
        List<Gathering> pendingPosts = gatheringAdaptor.queryAllByPendingMemberId(member.getId());
        List<Gathering> participatedPosts = gatheringAdaptor.queryAllByParticipatedMemberId(member.getId());
        List<Gathering> createdPosts = gatheringAdaptor.queryAllByLeadUserId(member.getId());

        return MemberResponse.from(member, pendingPosts, participatedPosts, createdPosts);
    }

}
