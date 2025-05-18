package com.ploggingbuddy.domain.gathering.adaptor;

import com.ploggingbuddy.domain.gathering.entity.Gathering;
import com.ploggingbuddy.domain.gathering.repository.GatheringRepository;
import com.ploggingbuddy.global.annotation.adaptor.Adaptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Adaptor
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GatheringAdaptor {

    private final GatheringRepository gatheringRepository;

    public Gathering queryById(Long id) {
        return gatheringRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("id를 찾을 수 없음: " + id));
    }

    public List<Gathering> queryAllByLeadUserId(Long memberId) {
        return gatheringRepository.findAllByLeadUserId(memberId);
    }

    public List<Gathering> queryAllByParticipatedMemberId(Long memberId) {
        return gatheringRepository.findAllByParticipatedUserId(memberId);
    }

    public List<Gathering> queryAllByPendingMemberId(Long memberId) {
        return gatheringRepository.findAllByPendingUserId(memberId);
    }

}
