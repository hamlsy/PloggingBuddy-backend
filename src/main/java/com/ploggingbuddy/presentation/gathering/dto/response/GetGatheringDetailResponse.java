package com.ploggingbuddy.presentation.gathering.dto.response;

import com.ploggingbuddy.domain.gathering.entity.Gathering;
import com.ploggingbuddy.domain.gathering.entity.GatheringStatus;

import java.time.LocalDateTime;
import java.util.List;

public record GetGatheringDetailResponse(
        Long postId,
        Long leadUserId,
        String leadUserNickname,
        GatheringStatus gatheringStatus,
        Long participantMaxNumber,
        String title,
        String address,
        Double longitude,
        Double latitude,
        LocalDateTime gatheringEndTime,
        LocalDateTime gatheringTime,
        String content,
        List<String> imageList,
        boolean isAuthor
) {
    public static GetGatheringDetailResponse of(Gathering gathering, String leadUserNickname, List<String> imageList, boolean isAuthor) {
        return new GetGatheringDetailResponse(gathering.getId(),
                gathering.getLeadUserId(),
                leadUserNickname,
                gathering.getPostStatus(),
                gathering.getParticipantMaxNumber(),
                gathering.getGatheringName(),
                gathering.getSpotStringAddress(),
                gathering.getSpotLongitude(),
                gathering.getSpotLatitude(),
                gathering.getGatheringEndTime(),
                gathering.getGatheringTime(),
                gathering.getContent(),
                imageList,
                isAuthor
        );
    }
}
