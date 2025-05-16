package com.ploggingbuddy.presentation.member.dto.response;

import com.ploggingbuddy.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponse {

    private String nickname;
    private String description;
    private String detailAddress;
    private String profileImageUrl;
    // 작성한 모임
    // 참석 예정인 모임

    public static MemberResponse from(Member member) {
        return MemberResponse.builder()
                .nickname(member.getNickname())
                .description(member.getDescription())
                .detailAddress(member.getAddress().getDetailAddress())
                .profileImageUrl(member.getProfileImageUrl())
                .build();
    }

}
