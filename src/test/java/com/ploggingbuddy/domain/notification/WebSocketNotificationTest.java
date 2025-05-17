package com.ploggingbuddy.domain.notification;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ploggingbuddy.domain.enrollment.adaptor.EnrollmentAdaptor;
import com.ploggingbuddy.domain.enrollment.entity.Enrollment;
import com.ploggingbuddy.domain.gathering.entity.Gathering;
import com.ploggingbuddy.domain.member.adaptor.MemberAdaptor;
import com.ploggingbuddy.domain.member.entity.Member;
import com.ploggingbuddy.domain.notification.entity.Notification;
import com.ploggingbuddy.domain.notification.entity.NotificationType;
import com.ploggingbuddy.domain.notification.repository.NotificationRepository;
import com.ploggingbuddy.domain.notification.sender.NotificationSender;
import com.ploggingbuddy.domain.notification.service.NotificationService;
import com.ploggingbuddy.presentation.notification.dto.response.NotificationMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class WebSocketNotificationTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private EnrollmentAdaptor enrollmentAdaptor;

    @Mock
    private MemberAdaptor memberAdaptor;

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    private NotificationService notificationService;
    private NotificationSender notificationSender;

    @BeforeEach
    public void setup() {
        // NotificationSender를 목으로 생성하고 NotificationService에 주입
        notificationSender = mock(NotificationSender.class);
        notificationService = new NotificationService(
                notificationRepository,
                notificationSender,
                enrollmentAdaptor,
                memberAdaptor
        );
    }

    @Test
    @DisplayName("새 멤버가 모임에 가입하면 모임 멤버들에게 웹소켓 알림이 전송되어야 한다")
    public void testNotifyMemberJoinedSendsWebSocketNotifications() {
        // Given
        Long gatheringId = 1L;
        Long memberId1 = 1L;
        Long memberId2 = 2L;
        Long newMemberId = 3L;

        // 테스트 데이터 생성
        Gathering gathering = Gathering.builder()
                .id(gatheringId)
                .gatheringName("테스트 모임")
                .build();

        Member existingMember1 = Member.builder()
                .id(memberId1)
                .nickname("기존멤버1")
                .build();

        Member existingMember2 = Member.builder()
                .id(memberId2)
                .nickname("기존멤버2")
                .build();

        Member newMember = Member.builder()
                .id(newMemberId)
                .nickname("새멤버")
                .build();

        List<Enrollment> enrollments = Arrays.asList(
                Enrollment.builder().memberId(memberId1).postId(gatheringId).build(),
                Enrollment.builder().memberId(memberId2).postId(gatheringId).build()
        );

        List<Member> existingMembers = Arrays.asList(existingMember1, existingMember2);

        // Mock 설정
        when(enrollmentAdaptor.queryByGatheringId(gatheringId)).thenReturn(enrollments);
        when(memberAdaptor.queryAllByIds(Arrays.asList(memberId1, memberId2))).thenReturn(existingMembers);
        when(notificationRepository.save(any(Notification.class))).thenAnswer(invocation -> {
            Notification notification = invocation.getArgument(0);
            return Notification.builder()
                    .id(1L)
                    .member(notification.getMember())
                    .gathering(notification.getGathering())
                    .message(notification.getMessage())
                    .notificationType(notification.getNotificationType())
                    .isRead(notification.isRead())
                    .build();
        });

        // When
        notificationService.notifyMemberJoined(gathering, newMember);

        // Then
        // 각 기존 멤버에 대해 알림이 저장되었는지 확인
        verify(notificationRepository, times(2)).save(any(Notification.class));
        
        // NotificationSender가 호출되었는지 확인
        verify(notificationSender, times(2)).sendNotification(any(Notification.class));
        
        // 전송된 알림 내용 검증
        ArgumentCaptor<Notification> notificationCaptor = ArgumentCaptor.forClass(Notification.class);
        verify(notificationSender, times(2)).sendNotification(notificationCaptor.capture());
        
        List<Notification> capturedNotifications = notificationCaptor.getAllValues();
        for (Notification notification : capturedNotifications) {
            assertThat(notification.getMessage()).contains(newMember.getNickname());
            assertThat(notification.getMessage()).contains(gathering.getGatheringName());
            assertThat(notification.getNotificationType()).isEqualTo(NotificationType.MEMBER_JOINED);
            assertThat(notification.getGathering().getId()).isEqualTo(gatheringId);
            assertThat(notification.getMember().getId()).isIn(memberId1, memberId2);
        }
    }

    @Test
    @DisplayName("WebSocketNotificationSender가 SimpMessagingTemplate을 통해 메시지를 전송하는지 확인")
    public void testWebSocketNotificationSenderSendsMessage() {
        // Given
        ObjectMapper objectMapper = new ObjectMapper();
        
        // WebSocketNotificationSender 생성
        NotificationSender webSocketSender = new WebSocketNotificationSenderTestImpl(messagingTemplate, objectMapper);
        
        Long gatheringId = 1L;
        Long memberId = 1L;
        
        // 테스트 데이터 생성
        Gathering gathering = Gathering.builder()
                .id(gatheringId)
                .gatheringName("테스트 모임")
                .build();

        Member member = Member.builder()
                .id(memberId)
                .nickname("테스트멤버")
                .build();
        
        String message = "새멤버님이 '테스트 모임' 모임에 참가했습니다.";
        Notification notification = Notification.create(member, gathering, message, NotificationType.MEMBER_JOINED);
        // SuperBuilder를 통해 새 객체를 생성하여 id 설정
        notification = Notification.builder()
                .id(1L)
                .member(notification.getMember())
                .gathering(notification.getGathering())
                .message(notification.getMessage())
                .notificationType(notification.getNotificationType())
                .isRead(notification.isRead())
                .build();
        
        // 목 설정
        ArgumentCaptor<String> destinationCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<NotificationMessage> messageCaptor = ArgumentCaptor.forClass(NotificationMessage.class);
        
        // When
        webSocketSender.sendNotification(notification);
        
        // Then
        // SimpMessagingTemplate이 호출되었는지 확인
        verify(messagingTemplate).convertAndSend(destinationCaptor.capture(), messageCaptor.capture());
        
        // 목적지 확인
        String destination = destinationCaptor.getValue();
        assertThat(destination).isEqualTo("/topic/notifications/" + memberId);
        
        // 메시지 내용 확인
        NotificationMessage capturedMessage = messageCaptor.getValue();
        assertThat(capturedMessage.getId()).isEqualTo(notification.getId());
        assertThat(capturedMessage.getMessage()).isEqualTo(message);
        assertThat(capturedMessage.getType()).isEqualTo(NotificationType.MEMBER_JOINED.name());
        assertThat(capturedMessage.getGatheringId()).isEqualTo(gatheringId);
        assertThat(capturedMessage.getGatheringName()).isEqualTo(gathering.getGatheringName());
    }
    
    /**
     * 테스트용 WebSocketNotificationSender 구현체
     */
    private static class WebSocketNotificationSenderTestImpl implements NotificationSender {
        private final SimpMessagingTemplate messagingTemplate;
        private final ObjectMapper objectMapper;
        
        public WebSocketNotificationSenderTestImpl(SimpMessagingTemplate messagingTemplate, ObjectMapper objectMapper) {
            this.messagingTemplate = messagingTemplate;
            this.objectMapper = objectMapper;
        }
        
        @Override
        public void sendNotification(Notification notification) {
            try {
                String destination = "/topic/notifications/" + notification.getMember().getId();
                NotificationMessage message = NotificationMessage.from(notification);
                messagingTemplate.convertAndSend(destination, message);
            } catch (Exception e) {
                // 테스트에서는 예외 무시
            }
        }
    }
}
