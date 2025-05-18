package com.ploggingbuddy.presentation.gathering.dto.request;

import com.ploggingbuddy.domain.gathering.entity.GatheringStatus;

public record UpdatePendingPostStatusDto(
        Long postId,
        GatheringStatus gatheringStatus     //Gathering_confirmed 또는 gathering_fail만 가능
) {
}
