package com.ploggingbuddy.domain.enrollment.service;

import com.ploggingbuddy.domain.enrollment.repository.EnrollmentRepository;
import com.ploggingbuddy.domain.member.entity.Member;
import com.ploggingbuddy.domain.member.repository.MemberRepository;
import com.ploggingbuddy.global.exception.base.InternalServerErrorException;
import com.ploggingbuddy.global.exception.code.ErrorCode;
import com.ploggingbuddy.presentation.enrollment.dto.EnrollmentData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final MemberRepository memberRepository;

    public void saveEnrollment(Long postId, Long userId) {
        com.ploggingbuddy.domain.enrollment.entity.Enrollment enrollment = new com.ploggingbuddy.domain.enrollment.entity.Enrollment(postId, userId);
        enrollmentRepository.save(enrollment);
    }

    public List<EnrollmentData> getEnrollmentList(Long postId) {
        return enrollmentRepository.findAllByPostId(postId)
                .stream()
                .map(enrollment -> {
                    Member member = memberRepository.findById(enrollment.getMemberId())
                            .orElseThrow(() -> new InternalServerErrorException(ErrorCode.INTERNAL_SERVER_ERROR));
                    return new EnrollmentData(member.getId(), member.getNickname(), member.getProfileImageUrl());
                })
                .toList();
    }

}
