package com.ploggingbuddy.domain.member.service;

import com.ploggingbuddy.domain.member.entity.Member;
import com.ploggingbuddy.domain.member.repository.MemberRepository;
import com.ploggingbuddy.global.vo.Address;
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

    public void updateAddress(Member member, Address address) {
        member.updateAddress(address);
    }

    public String getNicknameById(Long userId) {
       return memberRepository.findById(userId).get().getNickname();
    }

}
