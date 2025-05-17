package com.ploggingbuddy.domain.notification.sender;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ploggingbuddy.domain.notification.entity.Notification;
import com.ploggingbuddy.presentation.notification.dto.response.NotificationMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketNotificationSender implements NotificationSender{
    private final SimpMessagingTemplate messagingTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void sendNotification(Notification notification) {
        try {
            String destination = "/topic/notifications/" + notification.getMember().getId();
            NotificationMessage message = NotificationMessage.from(notification);
            messagingTemplate.convertAndSend(destination, message);
            log.info("웹소켓 알림 전송 완료 - 수신자: {}, 알림 ID: {}", notification.getMember().getId(), notification.getId());
        } catch (Exception e) {
            log.error("웹소켓 알림 전송 실패", e);
        }
    }

}
