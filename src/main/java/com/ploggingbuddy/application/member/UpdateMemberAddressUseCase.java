package com.ploggingbuddy.application.member;

import com.ploggingbuddy.domain.member.entity.Member;
import com.ploggingbuddy.domain.member.service.MemberService;
import com.ploggingbuddy.global.annotation.usecase.UseCase;
import com.ploggingbuddy.global.vo.Address;
import com.ploggingbuddy.presentation.member.dto.request.MemberRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@UseCase
@Transactional
@RequiredArgsConstructor
public class UpdateMemberAddressUseCase {

    private final MemberService memberService;

    public void execute(Member member, MemberRequest.UpdateAddress request) {
        Address address = new Address(
                request.getDetailAddress(), request.getLatitude(), request.getLongitude()
        );
        memberService.updateAddress(member, address);
    }

}
