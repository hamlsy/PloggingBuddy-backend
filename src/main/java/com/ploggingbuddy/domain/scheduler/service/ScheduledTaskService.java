package com.ploggingbuddy.domain.scheduler.service;

import com.ploggingbuddy.domain.gathering.scheduler.GatheringStatusScheduler;
import com.ploggingbuddy.domain.scheduler.entity.ScheduledStatus;
import com.ploggingbuddy.domain.scheduler.entity.ScheduledTask;
import com.ploggingbuddy.domain.scheduler.repository.ScheduledTaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduledTaskService {
    @Qualifier("taskScheduler")
    private final TaskScheduler taskScheduler;
    private final ScheduledTaskRepository scheduledTaskRepository;
    private final GatheringStatusScheduler gatheringStatusScheduler;

    private final ScheduledTaskProcessor scheduledTaskProcessor;

    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void initScheduledTask() {
        List<ScheduledTask> scheduledTaskList =
                scheduledTaskRepository.findByStatus(ScheduledStatus.PENDING);

        scheduledTaskList.forEach(task -> {
            long delaySeconds = calculateDelaySeconds(LocalDateTime.now(), task.getExecuteTime());

            scheduledTaskProcessor.processInitScheduling(task, delaySeconds);

            if (delaySeconds > 0) {
                Runnable updateStatus = updateStatusAsDone(task.getGatheringId());

                ZoneId zone = ZoneId.systemDefault();
                Instant instant = task.getExecuteTime().plusHours(1).atZone(zone).toInstant();

                taskScheduler.schedule(updateStatus, instant);
            }
        });
    }

    @Transactional
    public void addTask(Long postId, LocalDateTime gatheringTime) {
        LocalDateTime doneScheduledTime = gatheringTime.plusHours(1);

        ScheduledTask scheduledTask = new ScheduledTask(postId, doneScheduledTime, ScheduledStatus.PENDING);
        scheduledTaskRepository.save(scheduledTask);

        Runnable updateStatus = updateStatusAsDone(postId);

        ZoneId zone = ZoneId.systemDefault();
        Instant instant = doneScheduledTime.atZone(zone).toInstant();

        taskScheduler.schedule(updateStatus, instant);

    }

    public Runnable updateStatusAsDone(Long postId) {
        return () -> {
            gatheringStatusScheduler.updateGatheringStatusDone(postId);
        };
    }

    private long calculateDelaySeconds(LocalDateTime now, LocalDateTime targetDateTime) {
        Duration duration = Duration.between(now, targetDateTime);
        return duration.getSeconds();
    }

}

