package com.ploggingbuddy.domain.scheduler.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
public class ScheduledTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long gatheringId;

    @NotNull
    private LocalDateTime executeTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ScheduledStatus status;

    public void scheduledMessageStatusUpdate() {
        this.status = ScheduledStatus.COMPLETE;
    }

    public ScheduledTask(Long gatheringId, LocalDateTime executeTime, ScheduledStatus status) {
        this.gatheringId = gatheringId;
        this.executeTime = executeTime;
        this.status = status;
    }


}