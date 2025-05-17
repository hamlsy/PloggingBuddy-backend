package com.ploggingbuddy.presentation.notification.controller;

import com.ploggingbuddy.domain.gathering.adaptor.GatheringAdaptor;
import com.ploggingbuddy.domain.gathering.entity.Gathering;
import com.ploggingbuddy.domain.member.entity.Member;
import com.ploggingbuddy.domain.notification.service.NotificationService;
import com.ploggingbuddy.security.aop.CurrentMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
//테스트 용도입니다
public class NotificationController {

    private final NotificationService notificationService;
    private final GatheringAdaptor gatheringAdaptor;

    @GetMapping("/test/{postId}")
    public ResponseEntity<?> testNotification(@CurrentMember Member member, @PathVariable("postId") Long postId) {
        Gathering gathering = gatheringAdaptor.queryById(postId);
        notificationService.notifyMemberJoined(gathering, member);
        return ResponseEntity.ok("Test notification sent successfully.");
    }

}
