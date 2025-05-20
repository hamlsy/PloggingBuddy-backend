package com.ploggingbuddy.presentation.member.dto.response;

import com.ploggingbuddy.domain.gathering.entity.Gathering;
import com.ploggingbuddy.domain.member.entity.Member;
import com.ploggingbuddy.presentation.gathering.dto.response.GatheringResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponse {

    private String nickname;
    private String email;
    private String detailAddress;
    private String profileImageUrl;

    // 참석 예정인 모임
    private List<GatheringResponse> pendingPosts;
    // 내가 생성한 모임
    private List<GatheringResponse> createdPosts;
    // 참여했던 모임
    private List<GatheringResponse> participatedPosts;

    public static MemberResponse from(Member member, List<GatheringResponse> pendingPosts,
                                      List<GatheringResponse> participatedPosts, List<GatheringResponse> createdPosts) {
        return MemberResponse.builder()
                .nickname(member.getNickname())
                .detailAddress(member.getAddress().getDetailAddress())
                .profileImageUrl(member.getProfileImageUrl())
                .pendingPosts(pendingPosts)
                .participatedPosts(participatedPosts)
                .createdPosts(createdPosts)
                .email(member.getEmail())
                .build();
    }

}
