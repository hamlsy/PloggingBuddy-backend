package com.ploggingbuddy.presentation.gathering.dto.response;

import com.ploggingbuddy.domain.gathering.entity.Gathering;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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
    private LocalDateTime gatheringEndTime;
    private List<String> imageUrlList;

    public static GatheringResponse from(Gathering gathering, Long participantCurrentNumber, List<String> imageUrlList) {
        return GatheringResponse.builder()
                .postId(gathering.getId())
                .gatheringName(gathering.getGatheringName())
                .participantCurrentNumber(participantCurrentNumber)
                .participantMaxNumber(gathering.getParticipantMaxNumber())
                .detailAddress(gathering.getSpotStringAddress())
                .content(gathering.getContent())
                .imageUrlList(imageUrlList)
                .postStatus(gathering.getPostStatus().name())
                .gatheringEndTime(gathering.getGatheringEndTime())
                .build();
    }
}
