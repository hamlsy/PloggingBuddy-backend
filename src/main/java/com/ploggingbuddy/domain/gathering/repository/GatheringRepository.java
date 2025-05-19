package com.ploggingbuddy.domain.gathering.repository;

import com.ploggingbuddy.domain.gathering.entity.Gathering;
import com.ploggingbuddy.domain.gathering.entity.GatheringStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface GatheringRepository extends JpaRepository<Gathering, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT g FROM Gathering g WHERE g.id = :id")
    Optional<Gathering> findWithLockById(@Param("id") Long id);

    @Query("SELECT g FROM Gathering g WHERE g.leadUserId = :leadUserId order by g.createdAt desc")
    List<Gathering> findAllByLeadUserIdOrderByDescLimit(@Param("leadUserId") Long leadUserId, Pageable pageable);

    @Query("SELECT g FROM Gathering g JOIN Enrollment e on g.id = e.postId WHERE e.memberId = :memberId and g.postStatus = 'FINISHED' order by g.createdAt desc")
    List<Gathering> findAllByParticipatedUserIdOrderByDescLimit(@Param("memberId") Long memberId, Pageable pageable);

    @Query("SELECT g FROM Gathering g JOIN Enrollment e on g.id = e.postId WHERE e.memberId = :memberId and " +
            "g.postStatus = 'GATHERING' or g.postStatus = 'GATHERING_PENDING' " +
            "or g.postStatus = 'GATHERING_CONFIRMED' order by g.createdAt desc")
    List<Gathering> findAllByPendingUserIdOrderByDescLimit(@Param("memberId") Long memberId, Pageable pageable);

    List<Gathering> findByGatheringEndTimeLessThanEqualAndPostStatus(LocalDateTime now, GatheringStatus postStatus);
}
