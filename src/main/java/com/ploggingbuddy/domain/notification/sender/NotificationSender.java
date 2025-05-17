package com.ploggingbuddy.domain.notification.sender;

import com.ploggingbuddy.domain.notification.entity.Notification;

public interface NotificationSender {
    void sendNotification(Notification notification);
}
