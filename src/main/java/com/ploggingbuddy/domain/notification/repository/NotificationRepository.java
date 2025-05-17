package com.ploggingbuddy.domain.notification.repository;

import com.ploggingbuddy.domain.gathering.entity.Gathering;
import com.ploggingbuddy.domain.member.entity.Member;
import com.ploggingbuddy.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByMemberOrderByCreatedAtDesc(Member member);
    List<Notification> findByGatheringOrderByCreatedAtDesc(Gathering gathering);

}
