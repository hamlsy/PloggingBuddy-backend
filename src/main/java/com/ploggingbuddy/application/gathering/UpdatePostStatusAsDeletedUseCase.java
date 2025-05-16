package com.ploggingbuddy.application.gathering;

import com.ploggingbuddy.domain.gathering.entity.GatheringStatus;
import com.ploggingbuddy.domain.gathering.service.GatheringService;
import com.ploggingbuddy.global.annotation.usecase.UseCase;
import com.ploggingbuddy.presentation.gathering.dto.request.UpdatePostStatusAsDeletedDto;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class UpdatePostStatusAsDeletedUseCase {
    private final GatheringService gatheringService;

    public void execute(UpdatePostStatusAsDeletedDto postGatheringPostDto, Long userId) {
        gatheringService.updatePostStatus(postGatheringPostDto.postId(), GatheringStatus.DELETED, userId);
    }
}
