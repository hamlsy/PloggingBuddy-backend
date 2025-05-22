package com.ploggingbuddy.usecase.member;

import com.ploggingbuddy.application.member.GetMyInfoUseCase;
import com.ploggingbuddy.domain.enrollment.entity.Enrollment;
import com.ploggingbuddy.domain.enrollment.repository.EnrollmentRepository;
import com.ploggingbuddy.domain.enrollment.service.EnrollmentService;
import com.ploggingbuddy.domain.gathering.entity.Gathering;
import com.ploggingbuddy.domain.gathering.entity.GatheringStatus;
import com.ploggingbuddy.domain.gathering.repository.GatheringRepository;
import com.ploggingbuddy.domain.gathering.service.GatheringService;
import com.ploggingbuddy.domain.member.entity.Member;
import com.ploggingbuddy.domain.member.repository.MemberRepository;
import com.ploggingbuddy.global.vo.Address;
import com.ploggingbuddy.presentation.member.dto.response.MemberResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
public class GetMyInfoUseCaseTest {

    @Autowired
    private GetMyInfoUseCase getMyInfoUseCase;

    @Autowired
    private MemberRepository memberRepository;
    
    @Autowired
    private GatheringRepository gatheringRepository;
    
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    
    @Autowired
    private GatheringService gatheringService;

    @Autowired
    private EnrollmentService enrollmentService;
    

    private Member testMember;
    private Gathering pendingGathering1;
    private Gathering pendingGathering2;
    private Gathering participatedGathering;
    private Gathering createdGathering;

    @BeforeEach
    public void setUp() {
        // Create test member
        testMember = createTestMember();
        memberRepository.save(testMember);

        // Create gatherings and enrollments
        setupTestData();
    }
    
    @AfterEach
    public void tearDown() {
        // Clean up test data
        enrollmentRepository.deleteAll();
        gatheringRepository.deleteAll();
        memberRepository.deleteAll();
    }

    private Member createTestMember() {
        Address address = new Address(
                "서울시 강남구", 3, 3
        );
        return Member.builder()
                .username("testUser")
                .nickname("testNickname")
                .address(address)
                .build();
    }

    private void setupTestData() {
        // 1. Created gathering - Current user is the leader
        createdGathering = createGathering("Created Gathering", testMember.getId());
        // Set status to GATHERING (모집 중)
        createdGathering.updatePostStatus(GatheringStatus.GATHERING);
        gatheringService.save(createdGathering);

        // Create another member for other gatherings
        Member otherMember = Member.builder()
                .username("otherUser")
                .nickname("otherNickname")
                .address(new Address("서울시 서초구", 3, 3))
                .build();
        memberRepository.save(otherMember);
        
        // 2. Participated gathering - Completed gathering that user participated in
        participatedGathering = createGathering("Participated Gathering", otherMember.getId());
        // Set status to FINISHED (완료된 모임)
        participatedGathering.updatePostStatus(GatheringStatus.FINISHED);
        gatheringService.save(participatedGathering);
        
        // Enroll test member in the participated gathering
        enrollmentService.saveEnrollment(participatedGathering.getId(), testMember.getId());

        // 3. Pending gatherings - Gatherings user applied to but waiting approval
        pendingGathering1 = createGathering("Pending Gathering 1", otherMember.getId());
        pendingGathering2 = createGathering("Pending Gathering 2", otherMember.getId());
        
        // Set status to GATHERING_PENDING (대기 중인 모임)
        pendingGathering1.updatePostStatus(GatheringStatus.GATHERING_PENDING);
        pendingGathering2.updatePostStatus(GatheringStatus.GATHERING_PENDING);
        
        gatheringService.save(pendingGathering1);
        gatheringService.save(pendingGathering2);

        // Enroll test member in the pending gatherings
        enrollmentService.saveEnrollment(pendingGathering1.getId(), testMember.getId());
        enrollmentService.saveEnrollment(pendingGathering2.getId(), testMember.getId());
    }

    private Gathering createGathering(String name, Long leadUserId) {
        return Gathering.builder()
                .leadUserId(leadUserId)
                .gatheringName(name)
                .content("Test content for " + name)
                .participantMaxNumber(10L)
                .spotStringAddress("Test Location")
                .spotLatitude(4.0)
                .spotLongitude(4.0)
                .postStatus(GatheringStatus.GATHERING) // Default status
                .build();
    }

    @Test
    @DisplayName("GetMyInfoUseCase should return correct member information with gatherings")
    public void getMyInfoUseCaseTest() {
        // When
        MemberResponse response = getMyInfoUseCase.execute(testMember);

        // Then
        // Verify member information
        assertNotNull(response);
        assertEquals(testMember.getNickname(), response.getNickname());
        assertEquals(testMember.getAddress().getDetailAddress(), response.getDetailAddress());
        assertEquals(testMember.getProfileImageUrl(), response.getProfileImageUrl());

        // Verify gatherings are present in the response
        assertEquals(2, response.getPendingPosts().size(), "Should have 2 pending gatherings");
        assertEquals(1, response.getParticipatedPosts().size(), "Should have 1 participated gathering");
        assertEquals(1, response.getCreatedPosts().size(), "Should have 1 created gathering");

        // Verify content of gatherings in each list
        boolean foundPending1 = false;
        boolean foundPending2 = false;
        
        for (int i = 0; i < response.getPendingPosts().size(); i++) {
            String name = response.getPendingPosts().get(i).getGatheringName();
            if (name.equals("Pending Gathering 1")) {
                foundPending1 = true;
                assertEquals("GATHERING_PENDING", response.getPendingPosts().get(i).getPostStatus());
            } else if (name.equals("Pending Gathering 2")) {
                foundPending2 = true;
                assertEquals("GATHERING_PENDING", response.getPendingPosts().get(i).getPostStatus());
            }
        }
        
        assertEquals(true, foundPending1, "Pending Gathering 1 should be in the response");
        assertEquals(true, foundPending2, "Pending Gathering 2 should be in the response");
        
        // Verify participated gathering
        assertEquals("Participated Gathering", response.getParticipatedPosts().get(0).getGatheringName());
        assertEquals("FINISHED", response.getParticipatedPosts().get(0).getPostStatus(), "Participated gathering should have FINISHED status");
        
        // Verify created gathering
        assertEquals("Created Gathering", response.getCreatedPosts().get(0).getGatheringName());
        assertEquals("GATHERING", response.getCreatedPosts().get(0).getPostStatus(), "Created gathering should have GATHERING status");
    }
}
