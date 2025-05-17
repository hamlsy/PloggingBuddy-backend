package com.ploggingbuddy.domain.enrollment.adaptor;

import com.ploggingbuddy.domain.enrollment.entity.Enrollment;
import com.ploggingbuddy.domain.enrollment.repository.EnrollmentRepository;
import com.ploggingbuddy.global.annotation.adaptor.Adaptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Adaptor
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EnrollmentAdaptor {

    private final EnrollmentRepository repository;

    public List<Enrollment> queryByGatheringId(Long gatheringId) {
        return repository.findAllByPostId(gatheringId);
    }

}
