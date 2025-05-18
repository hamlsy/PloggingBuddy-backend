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

    // 게시글 상태 수정
    // 유저에 의해 조기 마감시 GatheringStatus 값을 null로 전달
    public void updatePostStatus(Long postId, GatheringStatus postStatus, Long requestUserId, Long enrolledCount) {
        Gathering gathering = getGatheringPost(postId);

        gatheringValidator.validateWriteUser(requestUserId, gathering);

        if (postStatus == null) {
            if (enrolledCount == 0) {
                gathering.updatePostStatus(GatheringStatus.GATHERING_FAILED);
            } else if (gathering.getParticipantMaxNumber() > enrolledCount) {
                gathering.updatePostStatus(GatheringStatus.GATHERING_PENDING);
            }
        } else {
            gathering.updatePostStatus(postStatus);
        }
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
