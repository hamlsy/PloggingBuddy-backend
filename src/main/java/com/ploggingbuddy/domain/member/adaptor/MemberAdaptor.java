package com.ploggingbuddy.domain.member.adaptor;

import com.ploggingbuddy.domain.member.entity.Member;
import com.ploggingbuddy.domain.member.repository.MemberRepository;
import com.ploggingbuddy.global.annotation.adaptor.Adaptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Adaptor
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberAdaptor {

    private final MemberRepository memberRepository;

    public Member queryByUsername(String username) {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("username을 찾을 수 없음: " + username));
    }

    public Member queryById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("id를 찾을 수 없음: " + id));
    }

    public List<Member> queryAllByIds(List<Long> ids) {
        return memberRepository.findAllByIdIn(ids);
    }

}
