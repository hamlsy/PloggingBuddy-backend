package com.ploggingbuddy.domain.enrollment.service;

import com.ploggingbuddy.domain.enrollment.entity.Enrollment;
import com.ploggingbuddy.domain.enrollment.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;

    public void saveEnrollment(Long postId, Long userId) {
        Enrollment enrollment = new Enrollment(postId, userId);
        enrollmentRepository.save(enrollment);
    }

}
