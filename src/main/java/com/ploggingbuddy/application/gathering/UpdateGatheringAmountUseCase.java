package com.ploggingbuddy.application.gathering;

import com.ploggingbuddy.application.validator.GatheringValidator;
import com.ploggingbuddy.domain.gathering.service.GatheringService;
import com.ploggingbuddy.domain.postImage.service.PostImageService;
import com.ploggingbuddy.global.annotation.usecase.UseCase;
import com.ploggingbuddy.presentation.gathering.dto.request.UpdateGatheringAmountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class UpdateGatheringAmountUseCase {
    private final GatheringService gatheringService;
    private final PostImageService postImageService;
    private final GatheringValidator gatheringValidator;

    public void execute(UpdateGatheringAmountDto updateGatheringAmountDto, Long memberId) {
        gatheringValidator.validateGatheringPostIdExist(updateGatheringAmountDto.postId());
        gatheringValidator.validateWriteUser(memberId, updateGatheringAmountDto.postId());

        gatheringService.updatePostGatheringAmount(updateGatheringAmountDto.postId(), updateGatheringAmountDto.participantMaxNumber(), memberId);
        postImageService.deletePreviousImages(updateGatheringAmountDto.postId());

        if(!updateGatheringAmountDto.imageList().isEmpty()) {
            postImageService.saveAll(updateGatheringAmountDto.imageList(), memberId);
        }
    }
}
