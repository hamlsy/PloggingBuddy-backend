package com.ploggingbuddy.global.auth.dto;

import lombok.RequiredArgsConstructor;

import java.util.Map;

public abstract class OAuth2UserInfo {
    protected Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    //제공자에서 발급해주는 아이디
    public abstract String getSocialId();

    //이메일
    public abstract String getEmail();

    //프로필 사진
    public abstract String getProfileImageUrl();

    //닉네임
    public abstract String getNickname();

}