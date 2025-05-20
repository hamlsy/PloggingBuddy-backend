package com.ploggingbuddy.application.gathering;

import com.ploggingbuddy.application.validator.GatheringValidator;
import com.ploggingbuddy.domain.gathering.entity.Gathering;
import com.ploggingbuddy.domain.gathering.service.GatheringService;
import com.ploggingbuddy.domain.postImage.service.PostImageService;
import com.ploggingbuddy.domain.scheduler.service.ScheduledTaskService;
import com.ploggingbuddy.global.annotation.usecase.UseCase;
import com.ploggingbuddy.presentation.gathering.dto.request.PostGatheringPostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class CreateGatheringUseCase {
    private final GatheringService gatheringService;
    private final PostImageService postImageService;
    private final ScheduledTaskService scheduledTaskService;
    private final GatheringValidator gatheringValidator;

    @Transactional
    public void execute(PostGatheringPostDto postGatheringPostDto, Long userId) {
        gatheringValidator.validateGatheringEndTime(postGatheringPostDto.gatheringEndTime());
        Gathering gathering = new Gathering(userId,
                postGatheringPostDto.title(),
                postGatheringPostDto.content(),
                postGatheringPostDto.participantNumberMax(),
                postGatheringPostDto.spotName(),
                postGatheringPostDto.spotLatitude(),
                postGatheringPostDto.spotLongitude(),
                postGatheringPostDto.gatheringEndTime(),
                postGatheringPostDto.gatheringTime());
        gathering = gatheringService.save(gathering);
        scheduledTaskService.addTask(gathering.getId(), gathering.getGatheringTime());

        if (postGatheringPostDto.imageList() != null && !postGatheringPostDto.imageList().isEmpty()) {
            postImageService.saveAll(postGatheringPostDto.imageList(), gathering.getId());
        }
    }
}
