package com.ploggingbuddy.domain.enrollment.repository;

import com.ploggingbuddy.domain.enrollment.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findAllByPostId(Long postId);
}
