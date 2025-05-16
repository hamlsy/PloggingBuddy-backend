package com.ploggingbuddy.presentation.member.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class MemberRequest {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateNickname {
        private String nickname;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateDescription {
        private String description;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateAddress {
        private String detailAddress;
        private double latitude;
        private double longitude;
    }

}
