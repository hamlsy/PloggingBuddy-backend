package com.ploggingbuddy.domain.gathering.service;

import com.ploggingbuddy.domain.gathering.entity.Gathering;
import com.ploggingbuddy.domain.gathering.entity.GatheringStatus;
import com.ploggingbuddy.domain.gathering.repository.GatheringRepository;
import com.ploggingbuddy.application.validator.GatheringValidator;
import com.ploggingbuddy.global.exception.base.BadRequestException;
import com.ploggingbuddy.global.exception.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class GatheringService {
    private final GatheringRepository gatheringRepository;
    private final GatheringValidator gatheringValidator;

    // 신규 생성
    public Gathering save(Gathering gathering) {
        return gatheringRepository.save(gathering);
    }

    //삭제 처리
    public void updatePostStatus(Long postId, GatheringStatus postStatus, Long requestUserId) {
        Gathering gathering = getGatheringPost(postId);

        gatheringValidator.validateWriteUser(requestUserId, gathering);
        gathering.updatePostStatus(postStatus);

    }

    // 인원 수정
    public void updatePostGatheringAmount(Long postId, Long participantMaxNumber, Long memberId) {
        Gathering gathering = getGatheringPost(postId);
        gatheringValidator.validateWriteUser(memberId, gathering);
        if (gathering.getParticipantMaxNumber() > participantMaxNumber) {
            throw new BadRequestException(ErrorCode.INVALID_UPDATE_GATHERING_AMOUNT_SIZE);
        }
        gathering.updateParticipantMaxNumber(participantMaxNumber);
    }

    private Gathering getGatheringPost(Long postId) {
        return gatheringRepository.findById(postId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.INVALID_POST_ID));
    }

}
