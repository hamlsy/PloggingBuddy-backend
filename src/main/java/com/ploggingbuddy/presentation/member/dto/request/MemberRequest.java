package com.ploggingbuddy.presentation.member.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class MemberRequest {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateNickname {
        private String nickname;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateDescription {
        private String description;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateAddress {
        private String address;
    }

}
