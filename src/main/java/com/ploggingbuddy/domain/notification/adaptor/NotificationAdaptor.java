package com.ploggingbuddy.domain.notification.adaptor;

import com.ploggingbuddy.domain.gathering.entity.Gathering;
import com.ploggingbuddy.domain.member.entity.Member;
import com.ploggingbuddy.domain.notification.entity.Notification;
import com.ploggingbuddy.domain.notification.repository.NotificationRepository;
import com.ploggingbuddy.global.annotation.adaptor.Adaptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Adaptor
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NotificationAdaptor {

    private final NotificationRepository repository;

    public List<Notification> queryByMemberOrderByCreatedAtDesc(Member member) {
        return repository.findByMemberOrderByCreatedAtDesc(member);
    }

    public  List<Notification> queryByGatheringOrderByCreatedAtDesc(Gathering gathering) {
        return repository.findByGatheringOrderByCreatedAtDesc(gathering);
    }

}
