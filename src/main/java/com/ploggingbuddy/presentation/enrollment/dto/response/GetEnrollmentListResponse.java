package com.ploggingbuddy.presentation.enrollment.dto.response;

import com.ploggingbuddy.presentation.enrollment.dto.Enrollment;

import java.util.List;

public record GetEnrollmentListResponse(
        List<Enrollment> enrollmentDataList
) {
}
