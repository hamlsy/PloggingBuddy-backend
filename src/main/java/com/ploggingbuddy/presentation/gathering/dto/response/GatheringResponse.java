package com.ploggingbuddy.presentation.gathering.dto.response;

import com.ploggingbuddy.domain.gathering.entity.Gathering;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GatheringResponse {

    private Long postId;
    private String gatheringName;
    private Long participantCurrentNumber;
    private Long participantMaxNumber;
    private String content;
    private String postStatus;
    private String detailAddress;

    public static GatheringResponse from(Gathering gathering, Long participantCurrentNumber) {
        return GatheringResponse.builder()
                .postId(gathering.getId())
                .gatheringName(gathering.getGatheringName())
                .participantCurrentNumber(participantCurrentNumber)
                .participantMaxNumber(gathering.getParticipantMaxNumber())
                .detailAddress(gathering.getSpotName())
                .content(gathering.getContent())
                .postStatus(gathering.getPostStatus().name())
                .build();
    }
}
