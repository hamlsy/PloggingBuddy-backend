package com.ploggingbuddy.global.auth.service;

import com.ploggingbuddy.domain.member.adaptor.MemberAdaptor;
import com.ploggingbuddy.domain.member.entity.Member;
import com.ploggingbuddy.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoClientService {

    private final MemberAdaptor memberAdaptor;
    private final MemberRepository memberRepository;
    private final RestTemplate restTemplate;

    private final String UNLINK_URL = "https://kapi.kakao.com/v1/user/unlink";

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String adminKey;

    @Transactional
    public void unlinkKakaoAccount(Long memberId) {
        Member member = memberAdaptor.queryById(memberId);
        unlinkFromKakao(member.getUsername());
        memberRepository.delete(member); //회원 탈퇴
    }

    private void unlinkFromKakao(String kakaoId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "KakaoAK " + adminKey);

        // 요청 본문에 target_id_type=user_id와 target_id=회원번호 값 전달
        String requestBody = "target_id_type=user_id&target_id=" + kakaoId;

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        try {
            restTemplate.postForObject(UNLINK_URL, request, String.class);
            log.info("카카오 연동 해제 API 호출 성공: {}", kakaoId);
        } catch (Exception e) {
            log.error("카카오 연동 해제 API 호출 실패: {}", e.getMessage());
            throw new RuntimeException("카카오 연동 해제에 실패했습니다.", e);
        }
    }
}
