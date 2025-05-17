package com.ploggingbuddy.application.gathering;

import com.ploggingbuddy.domain.gathering.service.GatheringService;
import com.ploggingbuddy.global.annotation.usecase.UseCase;
import com.ploggingbuddy.presentation.gathering.dto.request.UpdatePendingPostStatusDto;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class DecidePendingPostStatusUseCase {
    private final GatheringService gatheringService;

    public void execute(Long memberId, UpdatePendingPostStatusDto updatePendingPostStatusDto) {
        gatheringService.updatePostStatus(updatePendingPostStatusDto.postId(), updatePendingPostStatusDto.gatheringStatus(), memberId, null);
    }

}
