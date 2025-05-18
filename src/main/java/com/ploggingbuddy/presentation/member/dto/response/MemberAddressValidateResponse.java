package com.ploggingbuddy.presentation.member.dto.response;

import com.ploggingbuddy.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberAddressValidateResponse {
    private boolean hasAddress;
    public static MemberAddressValidateResponse from(Member member) {
        return MemberAddressValidateResponse.builder()
                .hasAddress(
                        member.getAddress() != null && member.getAddress().getDetailAddress() != null
                                && !member.getAddress().getDetailAddress().isEmpty()
                )
                .build();
    }
}
