package com.ploggingbuddy.application.validator;

import com.ploggingbuddy.domain.gathering.entity.Gathering;
import com.ploggingbuddy.domain.gathering.entity.GatheringStatus;
import com.ploggingbuddy.domain.gathering.repository.GatheringRepository;
import com.ploggingbuddy.global.exception.base.BadRequestException;
import com.ploggingbuddy.global.exception.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GatheringValidator {
    private final GatheringRepository gatheringRepository;

    public void validateWriteUser(Long requestUserId, Gathering gathering) {
        if (!gathering.getLeadUserId().equals(requestUserId)) {
            throw new BadRequestException(ErrorCode.FORBIDDEN_EDIT_POST);
        }
    }

    public void validateGatheringPostIdExist(Long gatheringPostId){
        if(!gatheringRepository.existsById(gatheringPostId)){
            throw new BadRequestException(ErrorCode.INVALID_POST_ID);
        }
    }

    public void validateEnrollableByPostId(Long gatheringPostId) {
        if(!gatheringRepository.findById(gatheringPostId)
                .get().getPostStatus().equals(GatheringStatus.GATHERING)){
            throw new BadRequestException(ErrorCode.POST_NOT_GATHERING_NOW);
        }
    }
}
