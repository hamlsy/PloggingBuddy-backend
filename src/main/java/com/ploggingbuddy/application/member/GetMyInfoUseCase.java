package com.ploggingbuddy.application.member;

import com.ploggingbuddy.domain.enrollment.adaptor.EnrollmentAdaptor;
import com.ploggingbuddy.domain.enrollment.repository.EnrollmentRepository;
import com.ploggingbuddy.domain.gathering.adaptor.GatheringAdaptor;
import com.ploggingbuddy.domain.gathering.entity.Gathering;
import com.ploggingbuddy.domain.member.entity.Member;
import com.ploggingbuddy.domain.postImage.entity.PostImage;
import com.ploggingbuddy.domain.postImage.repository.PostImageRepository;
import com.ploggingbuddy.global.annotation.usecase.UseCase;
import com.ploggingbuddy.presentation.gathering.dto.response.GatheringResponse;
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
    private final EnrollmentRepository enrollmentRepository;
    private final PostImageRepository postImageRepository;

    public MemberResponse execute(Member member) {
        List<Gathering> pendingPosts = gatheringAdaptor.queryAllByPendingMemberIdOrderByDescLimit3(member.getId());
        List<GatheringResponse> pendingPostResponses = pendingPosts.stream()
                .map(p -> GatheringResponse.from(p, enrollmentRepository.countByPostId(p.getId()),
                        postImageRepository.findAllByPostId(p.getId()).stream()
                                .map(PostImage::getUrl)
                                .toList()
                )).toList();

        List<Gathering> participatedPosts = gatheringAdaptor.queryAllByParticipatedMemberIdOrderByDescLimit3(member.getId());
        List<GatheringResponse> participatedPostResponses = participatedPosts.stream()
                .map(p -> GatheringResponse.from(p, enrollmentRepository.countByPostId(p.getId()),
                        postImageRepository.findAllByPostId(p.getId()).stream()
                        .map(PostImage::getUrl)
                        .toList())
                ).toList();

        List<Gathering> createdPosts = gatheringAdaptor.queryAllByLeadUserIdOrderByDescLimit3(member.getId());
        List<GatheringResponse> createdPostResponses = createdPosts.stream()
                .map(p -> GatheringResponse.from(p, enrollmentRepository.countByPostId(p.getId()),
                        postImageRepository.findAllByPostId(p.getId()).stream()
                        .map(PostImage::getUrl)
                        .toList())
                ).toList();

        return MemberResponse.from(member, pendingPostResponses, participatedPostResponses, createdPostResponses);
    }

}
