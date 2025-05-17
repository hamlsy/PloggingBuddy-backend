package com.ploggingbuddy.presentation.enrollment.dto;

public record EnrollmentData(
        Long enrolledMemberId,
        String name,
        String profilePictureUrl
) {
}
