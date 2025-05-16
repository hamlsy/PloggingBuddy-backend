package com.ploggingbuddy.presentation.enrollment.dto;

public record Enrollment(
        Long enrolledMemberId,
        String name,
        String profilePictureUrl
) {
}
