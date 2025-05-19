package com.ploggingbuddy.application.gathering;

import com.ploggingbuddy.domain.gathering.entity.Gathering;
import com.ploggingbuddy.domain.gathering.service.GatheringService;
import com.ploggingbuddy.global.annotation.usecase.UseCase;
import com.ploggingbuddy.presentation.gathering.dto.GatheringPreview;
import com.ploggingbuddy.presentation.gathering.dto.response.GetGatheringsNearSpotResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
@Transactional
@RequiredArgsConstructor
public class GetGatheringsNearSpotUseCase {
    private final GatheringService gatheringService;

    public GetGatheringsNearSpotResponse execute(Double latitude, Double longitude) {
        List<Gathering> nearGtheringList = gatheringService.getNearGatherings(latitude, longitude);
        List<GatheringPreview> nearGatheringList = nearGtheringList.stream()
                .map(gathering -> new GatheringPreview(gathering.getId(), gathering.getSpotLongitude(), gathering.getSpotLatitude()))
                .toList();
        return new GetGatheringsNearSpotResponse(nearGatheringList);
    }
}
