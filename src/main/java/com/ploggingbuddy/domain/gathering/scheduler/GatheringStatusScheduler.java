package com.ploggingbuddy.domain.gathering.scheduler;

import com.ploggingbuddy.domain.enrollment.service.EnrollmentService;
import com.ploggingbuddy.domain.gathering.entity.Gathering;
import com.ploggingbuddy.domain.gathering.entity.GatheringStatus;
import com.ploggingbuddy.domain.gathering.repository.GatheringRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GatheringStatusScheduler {
    private final GatheringRepository gatheringRepository;
    private final EnrollmentService enrollmentService;

    private Long gatheredCnt;

    @Transactional
    @Scheduled(cron = "0 0 * * * *") // 매시 정각기준
    public void updatePostStatus() {
        System.out.println("scheduler running..." + LocalDateTime.now());

        // 모집 종료시각에 따른 모집 마감 자동 스케줄러
        // 0명 신청시 모임불가(모집 실패) GATHERING_FAILED로 이동
        // 1명 이상 신청시 모임개설유저의 모임 여부 결정을 기다리는 GATHERING_PENDING 값
        // GATHERING_PENDING 상태에서 모임개설유저가 모임 강행으로 결정시 GATHERING_CONFIRMED 값
        List<Gathering> gatheringPostsToClose = gatheringRepository.findByGatheringEndTimeLessThanEqualAndPostStatus(LocalDateTime.now(), GatheringStatus.GATHERING);
        for (Gathering gathering : gatheringPostsToClose) {
            gatheredCnt = enrollmentService.getEnrolledCount(gathering.getId());
            if(gatheredCnt == 0){
                gathering.updatePostStatus(GatheringStatus.GATHERING_FAILED);
            }else if(gatheredCnt>0){
                gathering.updatePostStatus(GatheringStatus.GATHERING_PENDING);
            }
        }

        // 모임시각이 지난 gathering 게시글 상태 자동 변동 스케줄러
        // 성사되지 않은 모임(모집인원 0명(이 경우 자동스케줄러에서 진행됨)/ N명 신청했으나 모임개설유저가 모임 미진행 선택시/ 모임 개최 여부 대기 중 모임시각이 지날시), GATHERING_FAILED 값
        // 성공적으로 성사된 모임(GATHERING_CONFIRMED)일때, FINISHED 값
        List<GatheringStatus> excludeStatus = List.of(GatheringStatus.FINISHED, GatheringStatus.GATHERING_FAILED);
        List<Gathering> gatheringPostToEnd = gatheringRepository.findByGatheringTimeLessThanAndPostStatusNotIn(LocalDateTime.now(), excludeStatus);
        for (Gathering gathering : gatheringPostToEnd) {
            if(gathering.getPostStatus().equals(GatheringStatus.GATHERING_PENDING)){
                gathering.updatePostStatus(GatheringStatus.GATHERING_FAILED);
            }else if(gathering.getPostStatus().equals(GatheringStatus.GATHERING_CONFIRMED)){
                gathering.updatePostStatus(GatheringStatus.FINISHED);
            }
        }
    }
}
