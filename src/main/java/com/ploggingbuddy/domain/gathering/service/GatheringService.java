package com.ploggingbuddy.domain.gathering.service;

import com.ploggingbuddy.domain.gathering.entity.Gathering;
import com.ploggingbuddy.domain.gathering.entity.GatheringStatus;
import com.ploggingbuddy.domain.gathering.repository.GatheringRepository;
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

    // 신규 생성
    public void save(Gathering gathering) {
        gatheringRepository.save(gathering);
    }

    //삭제 처리
    public void updatePostStatus(Long postId, GatheringStatus postStatus, Long requestUserId) {
        Gathering gathering = getGatheringPost(postId);

        validateWriteUser(requestUserId, gathering);
        gathering.updatePostStatus(postStatus);

    }

    private Gathering getGatheringPost(Long postId) {
        return gatheringRepository.findById(postId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.INVALID_POST_ID));
    }

    private void validateWriteUser(Long requestUserId, Gathering gathering) {
        if (!gathering.getLeadUserId().equals(requestUserId)) {
            throw new BadRequestException(ErrorCode.FORBIDDEN_EDIT_POST);
        }
    }
}
