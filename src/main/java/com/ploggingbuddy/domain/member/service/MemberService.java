package com.ploggingbuddy.domain.member.service;

import com.ploggingbuddy.domain.member.entity.Member;
import com.ploggingbuddy.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void updateNickname(Member member, String nickname) {
        member.updateNickname(nickname);
    }

    public void updateDescription(Member member, String description) {
        member.updateDescription(description);
    }

    public void updateAddress(Member member, String detailAddress, Double latitude, Double longitude) {
        member.updateAddress(detailAddress, latitude, longitude);
    }

}
