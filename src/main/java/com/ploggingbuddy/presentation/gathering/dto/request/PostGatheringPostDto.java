package com.ploggingbuddy.presentation.gathering.dto.request;

public record PostGatheringPostDto(
        String title,
        String content,
        Long participantNumberMin,
        Long participantNumberMax,
        String spotName,
        Float spotLongitude,
        Float spotLatitude
) {
}
