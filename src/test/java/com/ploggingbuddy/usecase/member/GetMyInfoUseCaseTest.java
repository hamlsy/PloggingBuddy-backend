package com.ploggingbuddy.usecase.member;

import com.ploggingbuddy.application.member.GetMyInfoUseCase;
import com.ploggingbuddy.domain.enrollment.entity.Enrollment;
import com.ploggingbuddy.domain.enrollment.repository.EnrollmentRepository;
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
                "서울시 강남구", 37.5665, 126.9780
        );
        return Member.builder()
                .username("testUser")
                .nickname("testNickname")
                .address(address)
                .email("test@example.com")
                .profileImageUrl("http://example.com/profile.jpg")
                .build();
    }

    private void setupTestData() {
        createdGathering = createGathering("Created Gathering", testMember.getId());
        gatheringService.save(createdGathering);

        Member otherMember = Member.builder()
                .username("otherUser")
                .nickname("otherNickname")
                .address(new Address("서울시 서초구", 3, 3))
                .build();
        memberRepository.save(otherMember);
        
        participatedGathering = createGathering("Participated Gathering", otherMember.getId());
        gatheringService.save(participatedGathering);

        Enrollment enrollment = new Enrollment(testMember.getId(), participatedGathering.getId());
        enrollmentRepository.save(enrollment);

        pendingGathering1 = createGathering("Pending Gathering 1", otherMember.getId());
        pendingGathering2 = createGathering("Pending Gathering 2", otherMember.getId());
        gatheringService.save(pendingGathering1);
        gatheringService.save(pendingGathering2);

        Enrollment pendingEnrollment1 = new Enrollment(testMember.getId(), pendingGathering1.getId());
        Enrollment pendingEnrollment2 = new Enrollment(testMember.getId(), pendingGathering2.getId());
        enrollmentRepository.save(pendingEnrollment1);
        enrollmentRepository.save(pendingEnrollment2);
    }

    private Gathering createGathering(String name, Long leadUserId) {
        return Gathering.builder()
                .leadUserId(leadUserId)
                .gatheringName(name)
                .content("Test content for " + name)
                .participantMaxNumber(10L)
                .spotName("Test Location")
                .spotLatitude(4.0)
                .spotLongitude(4.0)
                .postStatus(GatheringStatus.GATHERING)
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
        assertEquals(2, response.getPendingPosts().size());
        assertEquals(1, response.getParticipatedPosts().size());
        assertEquals(1, response.getCreatedPosts().size());

        // Verify content of gatherings in each list
        boolean foundPending1 = false;
        boolean foundPending2 = false;
        
        for (int i = 0; i < response.getPendingPosts().size(); i++) {
            String name = response.getPendingPosts().get(i).getGatheringName();
            if (name.equals("Pending Gathering 1")) {
                foundPending1 = true;
            } else if (name.equals("Pending Gathering 2")) {
                foundPending2 = true;
            }
        }
        
        assertEquals(true, foundPending1, "Pending Gathering 1 should be in the response");
        assertEquals(true, foundPending2, "Pending Gathering 2 should be in the response");
        assertEquals("Participated Gathering", response.getParticipatedPosts().get(0).getGatheringName());
        assertEquals("Created Gathering", response.getCreatedPosts().get(0).getGatheringName());
    }
}
