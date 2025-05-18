package com.ploggingbuddy.usecase.member;

import com.ploggingbuddy.application.member.GetMyInfoUseCase;
import com.ploggingbuddy.application.member.UpdateMemberAddressUseCase;
import com.ploggingbuddy.application.member.UpdateMemberNicknameUseCase;
import com.ploggingbuddy.domain.member.entity.Member;
import com.ploggingbuddy.domain.member.repository.MemberRepository;
import com.ploggingbuddy.global.vo.Address;
import com.ploggingbuddy.presentation.member.dto.request.MemberRequest;
import com.ploggingbuddy.presentation.member.dto.response.MemberResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SpringBootTest
public class MemberUseCaseTest {

    @Autowired
    private UpdateMemberNicknameUseCase updateMemberNicknameUseCase;

    @Autowired
    private UpdateMemberAddressUseCase updateMemberAddressUseCase;

    @Autowired
    private GetMyInfoUseCase myInfoUseCase;

    @Autowired
    private MemberRepository memberRepository;

    private Member member;

    @BeforeEach
    public void setUp() {
        member = createTestMember();
        memberRepository.save(member);
    }

    private Member createTestMember() {
        Address address = new Address(
                "testDetailAddress", 1.0, 1.0
        );
        return Member.builder()
                .username("testUser")
                .nickname("testNickname")
                .address(address)
                .email("testEmail")
                .profileImageUrl("testProfileImageUrl")
                .build();
    }

    @Test
    @DisplayName("Update Member Nickname")
    public void updateMemberNicknameUseCaseTest() {
        //given
        MemberRequest.UpdateNickname request = MemberRequest.UpdateNickname.builder()
                .nickname("newNickname")
                .build();

        //when
        updateMemberNicknameUseCase.execute(member, request);

        //then
        assertEquals("newNickname", member.getNickname());

    }

    @Test
    @DisplayName("update member address")
    public void updateMemberAddressUseCaseTest() {
        //given
        MemberRequest.UpdateAddress request = MemberRequest.UpdateAddress.builder()
                .detailAddress(
                        "newDetailAddress"
                )
                .latitude(2.0)
                .latitude(2.0)
                .build();

        //when
        updateMemberAddressUseCase.execute(member, request);

        //then
        assertEquals(2.0, member.getAddress().getLatitude());
    }

    @Test
    @DisplayName("get MyInfo test")
    public void getMyInfoUseCaseTest() {
        //given

        //when
        MemberResponse response = myInfoUseCase.execute(member);

        //then
        assertEquals("testNickname", response.getNickname());
    }

}
