package com.ploggingbuddy.domain.scheduler.repository;

import com.ploggingbuddy.domain.scheduler.entity.ScheduledStatus;
import com.ploggingbuddy.domain.scheduler.entity.ScheduledTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduledTaskRepository extends JpaRepository<ScheduledTask, Long> {
    List<ScheduledTask> findByStatus(ScheduledStatus status);
}
