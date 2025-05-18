package com.ploggingbuddy.presentation.gathering.dto.request;

import java.util.List;

public record UpdateGatheringAmountDto(
        Long postId,
        Long participantMaxNumber,
        List<String> imageList
) {
}
