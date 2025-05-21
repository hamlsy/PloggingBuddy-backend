package com.ploggingbuddy.presentation.gathering.dto.request;

import java.time.LocalDateTime;
import java.util.List;

public record PostGatheringPostDto(
        String title,
        String content,
        Long participantNumberMax,
        String spotName,
        Double spotLongitude,
        Double spotLatitude,
        LocalDateTime gatheringEndTime,
        LocalDateTime gatheringTime,
        List<String> imageList
) {
}
