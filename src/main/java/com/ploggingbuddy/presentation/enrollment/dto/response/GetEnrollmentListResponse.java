package com.ploggingbuddy.presentation.enrollment.dto.response;

import com.ploggingbuddy.presentation.enrollment.dto.EnrollmentData;

import java.util.List;

public record GetEnrollmentListResponse(
        List<EnrollmentData> enrollmentDataDataList
) {
}
