package com.ploggingbuddy.application.member;

import com.ploggingbuddy.domain.member.entity.Member;
import com.ploggingbuddy.domain.member.service.MemberService;
import com.ploggingbuddy.global.annotation.usecase.UseCase;
import com.ploggingbuddy.presentation.member.dto.request.MemberRequest;
import com.ploggingbuddy.presentation.member.dto.response.MemberResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@UseCase
@Transactional
@RequiredArgsConstructor
public class GetMyInfoUseCase {

    //todo 모임 정보 추가
    public MemberResponse execute(Member member) {
        return MemberResponse.from(member);
    }

}
