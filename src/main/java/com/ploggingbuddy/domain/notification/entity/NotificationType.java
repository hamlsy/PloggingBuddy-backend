package com.ploggingbuddy.domain.notification.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {

    MEMBER_JOINED("모임에 참가했습니다."),
    NEW_MEMBER_JOINED("새로운 참가자가 모임에 참가했습니다."),
    STATUS_CHANGED("모임에 참여되었습니다.");

    private final String defaultMessage;

}
