package com.ploggingbuddy.global.auth.service;

import com.ploggingbuddy.domain.member.adaptor.MemberAdaptor;
import com.ploggingbuddy.domain.member.entity.Member;
import com.ploggingbuddy.domain.member.entity.Role;
import com.ploggingbuddy.domain.member.repository.MemberRepository;
import com.ploggingbuddy.global.auth.domain.CustomOAuthUser;
import com.ploggingbuddy.global.auth.domain.OAuth2Attributes;
import com.ploggingbuddy.global.auth.dto.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final String TEMP_NICKNAME_HEADER = "PLG_";
    private final int TEMP_NICKNAME_LENGTH = 10;

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        log.info("registrationId={}", registrationId);
        log.info("userNameAttributeName={}", userNameAttributeName);

        // 소셜에서 전달받은 정보를 가진 OAuth2User 에서 Map 을 추출하여 OAuth2Attribute 를 생성
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // 내부에서 OAuth2UserInfo 생성과 함께 OAuth2Attributes 를 생성해서 반환
        OAuth2Attributes oauth2Attributes = OAuth2Attributes.of(userNameAttributeName, attributes);
        OAuth2UserInfo oauth2UserInfo = oauth2Attributes.getOAuth2UserInfo();

        // 값 추출
        String socialId = oauth2UserInfo.getSocialId();
        String email = oauth2UserInfo.getEmail();
        String profileImageUrl = oauth2UserInfo.getProfileImageUrl();

        log.info("socialId={}", socialId);
        log.info("email={}", email);
        log.info("profileImageUrl={}", profileImageUrl);

        // 카카오 전용
        String username = socialId;
        Optional<Member> targetMember = memberRepository.findByUsername(username);
        Member member = targetMember.orElseGet(() -> signupSocialMember(username, email, profileImageUrl));

        return new CustomOAuthUser(member,
                Collections.singleton(new SimpleGrantedAuthority(member.getRole().getName())),
                attributes);
    }

    private Member signupSocialMember(String username, String email, String profileImageUrl) {
        String nickname = TEMP_NICKNAME_HEADER + UUID.randomUUID().toString().substring(0, TEMP_NICKNAME_LENGTH);
        Member member = Member.builder()
                .username(username)
                .nickname(nickname)
                .email(email)
                .profileImageUrl(profileImageUrl)
                .role(Role.USER)
                .build();
        return memberRepository.save(member);
    }
}