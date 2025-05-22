package com.ploggingbuddy.domain.scheduler.service;

import com.ploggingbuddy.domain.gathering.scheduler.GatheringStatusScheduler;
import com.ploggingbuddy.domain.scheduler.entity.ScheduledTask;
import com.ploggingbuddy.domain.scheduler.repository.ScheduledTaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class ScheduledTaskProcessor {

    private final ScheduledTaskRepository scheduledTaskRepository;
    private final GatheringStatusScheduler gatheringStatusScheduler;

    @Transactional
    public void processInitScheduling(ScheduledTask task, Long delaySeconds) {
        if (delaySeconds <= 0) {
            try {
                gatheringStatusScheduler.updateGatheringStatusDone(task.getGatheringId());
                task = scheduledTaskRepository.getById(task.getId());
                task.scheduledMessageStatusUpdate();
                scheduledTaskRepository.delete(task);
            } catch (Exception e) {
                System.err.println("Error processing task: " + e.getMessage());
            }
        }
    }
}