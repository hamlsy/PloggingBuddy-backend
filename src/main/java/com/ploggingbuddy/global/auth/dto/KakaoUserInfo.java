package com.ploggingbuddy.global.auth.dto;

import java.util.Map;

public class KakaoUserInfo extends OAuth2UserInfo {
    private final Map<String, Object> account;

    public KakaoUserInfo(Map<String, Object> attributes) {
        super(attributes);
        this.account = (Map<String, Object>) attributes.get("kakao_account");
    }

    @Override
    public String getSocialId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getEmail() {
        return account.get("email").toString();
    }

    @Override
    public String getProfileImageUrl() {
        Map<String, Object> profile = (Map<String, Object>) account.get("profile");
        return (String) profile.get("profile_image_url");
    }

    @Override
    public String getNickname() {
        Map<String, Object> profile = (Map<String, Object>) account.get("profile");
        return (String) profile.get("nickname");
    }
}