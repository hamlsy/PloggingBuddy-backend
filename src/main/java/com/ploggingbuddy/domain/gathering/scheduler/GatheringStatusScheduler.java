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

        List<Gathering> gatheringPostsToClose = gatheringRepository.findByGatheringEndTimeLessThanEqualAndPostStatus(LocalDateTime.now(), GatheringStatus.GATHERING);
        for (Gathering gathering : gatheringPostsToClose) {
            gatheredCnt = enrollmentService.getEnrolledCount(gathering.getId());
            if(gatheredCnt == 0){
                gathering.updatePostStatus(GatheringStatus.GATHERING_FAILED);
            }else if(gatheredCnt>0){
                gathering.updatePostStatus(GatheringStatus.GATHERING_PENDING);
            }
        }
    }
}
