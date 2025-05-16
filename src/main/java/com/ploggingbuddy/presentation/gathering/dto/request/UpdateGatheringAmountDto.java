package com.ploggingbuddy.presentation.gathering.dto.request;

public record UpdateGatheringAmountDto(
        Long postId,
        Long participantMaxNumber
) {
}
