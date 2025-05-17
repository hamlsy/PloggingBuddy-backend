package com.ploggingbuddy.presentation.gathering.dto.request;

import java.util.List;

public record PostGatheringPostDto(
        String title,
        String content,
        Long participantNumberMax,
        String spotName,
        Float spotLongitude,
        Float spotLatitude,
        List<String> imageList
) {
}
