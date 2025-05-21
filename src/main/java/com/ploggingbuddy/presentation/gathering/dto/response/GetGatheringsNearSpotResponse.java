package com.ploggingbuddy.presentation.gathering.dto.response;

import com.ploggingbuddy.presentation.gathering.dto.GatheringPreview;

import java.util.List;

public record GetGatheringsNearSpotResponse(
    List<GatheringPreview> gatheringPreviewList
) {
}
