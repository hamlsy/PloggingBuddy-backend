package com.ploggingbuddy.application.gathering;

import com.ploggingbuddy.domain.gathering.service.GatheringService;
import com.ploggingbuddy.global.annotation.usecase.UseCase;
import com.ploggingbuddy.presentation.gathering.dto.request.UpdateGatheringAmountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class UpdateGatheringAmountUseCase {
    private final GatheringService gatheringService;

    public void execute(UpdateGatheringAmountDto updateGatheringAmountDto, Long memberId) {
        gatheringService.updatePostGatheringAmount(updateGatheringAmountDto.postId(), updateGatheringAmountDto.participantMaxNumber(), memberId);
    }
}
