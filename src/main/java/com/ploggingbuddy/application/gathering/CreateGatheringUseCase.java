package com.ploggingbuddy.application.gathering;

import com.ploggingbuddy.global.annotation.usecase.UseCase;
import com.ploggingbuddy.presentation.gathering.dto.request.PostGatheringPostDto;
import com.ploggingbuddy.domain.gathering.entity.Gathering;
import com.ploggingbuddy.domain.gathering.service.GatheringService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class CreateGatheringUseCase {
    private final GatheringService gatheringService;

    public void execute(PostGatheringPostDto postGatheringPostDto, Long userId) {
        Gathering gathering = new Gathering(userId,
                postGatheringPostDto.title(),
                postGatheringPostDto.content(),
                postGatheringPostDto.participantNumberMax(),
                postGatheringPostDto.spotName(),
                postGatheringPostDto.spotLatitude(),
                postGatheringPostDto.spotLongitude());
        gatheringService.save(gathering);
    }
}
