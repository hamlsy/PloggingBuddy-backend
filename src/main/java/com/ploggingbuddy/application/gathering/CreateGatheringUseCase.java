package com.ploggingbuddy.application.gathering;

import com.ploggingbuddy.application.validator.GatheringValidator;
import com.ploggingbuddy.domain.gathering.entity.Gathering;
import com.ploggingbuddy.domain.gathering.service.GatheringService;
import com.ploggingbuddy.domain.postImage.service.PostImageService;
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
    private final GatheringValidator gatheringValidator;

    public void execute(PostGatheringPostDto postGatheringPostDto, Long userId) {
        gatheringValidator.validateGatheringEndTime(postGatheringPostDto.gatheringEndTime());
        Gathering gathering = new Gathering(userId,
                postGatheringPostDto.title(),
                postGatheringPostDto.content(),
                postGatheringPostDto.participantNumberMax(),
                postGatheringPostDto.spotName(),
                postGatheringPostDto.spotLatitude(),
                postGatheringPostDto.spotLongitude(),
                postGatheringPostDto.gatheringEndTime());
        Long postId = gatheringService.save(gathering).getId();

        if (postGatheringPostDto.imageList() != null && !postGatheringPostDto.imageList().isEmpty()) {
            postImageService.saveAll(postGatheringPostDto.imageList(), postId);
        }
    }
}
