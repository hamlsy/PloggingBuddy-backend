package com.ploggingbuddy.domain.notification.service;

import com.ploggingbuddy.domain.enrollment.adaptor.EnrollmentAdaptor;
import com.ploggingbuddy.domain.enrollment.entity.Enrollment;
import com.ploggingbuddy.domain.gathering.entity.Gathering;
import com.ploggingbuddy.domain.member.adaptor.MemberAdaptor;
import com.ploggingbuddy.domain.member.entity.Member;
import com.ploggingbuddy.domain.notification.entity.Notification;
import com.ploggingbuddy.domain.notification.entity.NotificationType;
import com.ploggingbuddy.domain.notification.repository.NotificationRepository;
import com.ploggingbuddy.domain.notification.sender.NotificationSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationSender notificationSender;
    private final EnrollmentAdaptor enrollmentAdaptor;
    private final MemberAdaptor memberAdaptor;

    @Async
    public void notifyMemberJoined(Gathering gathering, Member newMember) {
        log.info("{} 모임에 참가했습니다. 참가자: {}", gathering.getGatheringName(), newMember.getNickname());

        List<Long> memberIds = getEnrolledMemberIds(gathering);

        List<Member> members = memberAdaptor.queryAllByIds(memberIds);
        for (Member member : members) {
            String message = newMember.getNickname() + "님이 '" + gathering.getGatheringName() + "' 모임에 참가했습니다.";
            Notification notification = Notification.create(member, gathering, message, NotificationType.MEMBER_JOINED);
            notificationRepository.save(notification);

            //알림 전송
            notificationSender.sendNotification(notification);

        }

    }

    @Async
    public void notifyGatheringEnrolled(Gathering gathering) {
        log.info("{} 모임에 참여되었습니다.", gathering.getGatheringName());

        List<Long> memberIds = getEnrolledMemberIds(gathering);

        List<Member> members = memberAdaptor.queryAllByIds(memberIds);
        for (Member member : members) {
            String message = "'" + gathering.getGatheringName() + "' 모임에 참여되었습니다.";
            Notification notification = Notification.create(member, gathering, message, NotificationType.STATUS_CHANGED);
            notificationRepository.save(notification);

            //알림 전송
            notificationSender.sendNotification(notification);

        }

    }

    private List<Long> getEnrolledMemberIds(Gathering gathering) {
        return enrollmentAdaptor.queryByGatheringId(gathering.getId())
                .stream().map(Enrollment::getMemberId)
                .toList();
    }

}
