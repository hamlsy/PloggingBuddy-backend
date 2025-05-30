package com.ploggingbuddy.domain.gathering.adaptor;

import com.ploggingbuddy.domain.gathering.entity.Gathering;
import com.ploggingbuddy.domain.gathering.repository.GatheringRepository;
import com.ploggingbuddy.global.annotation.adaptor.Adaptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.PageRequest;
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

    public List<Gathering> queryAllByLeadUserIdOrderByDescLimit3(Long memberId) {
        return gatheringRepository.findAllByLeadUserIdOrderByDescLimit(memberId, PageRequest.of(0, 3));
    }

    public List<Gathering> queryAllByParticipatedMemberIdOrderByDescLimit3(Long memberId) {
        return gatheringRepository.findAllByParticipatedUserIdOrderByDescLimit(memberId, PageRequest.of(0, 3));
    }

    public List<Gathering> queryAllByPendingMemberIdOrderByDescLimit3(Long memberId) {
        return gatheringRepository.findAllByPendingUserIdOrderByDescLimit(memberId, PageRequest.of(0, 3));
    }

}
