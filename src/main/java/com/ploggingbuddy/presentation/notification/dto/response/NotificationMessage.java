package com.ploggingbuddy.presentation.notification.dto.response;

import com.ploggingbuddy.domain.notification.entity.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationMessage {
    private Long id;
    private String message;
    private String type;
    private LocalDateTime createdAt;
    private Long gatheringId;
    private String gatheringName;

    public static NotificationMessage from(Notification notification) {
        return NotificationMessage.builder()
                .id(notification.getId())
                .message(notification.getMessage())
                .type(notification.getNotificationType().name())
                .createdAt(notification.getCreatedAt())
                .gatheringId(notification.getGathering().getId())
                .gatheringName(notification.getGathering().getGatheringName())
                .build();
    }

}
