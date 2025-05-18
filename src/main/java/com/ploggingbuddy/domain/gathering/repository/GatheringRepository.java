package com.ploggingbuddy.domain.gathering.repository;

import com.ploggingbuddy.domain.gathering.entity.Gathering;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GatheringRepository extends JpaRepository<Gathering, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT g FROM Gathering g WHERE g.id = :id")
    Optional<Gathering> findWithLockById(@Param("id") Long id);

    @Query("SELECT g FROM Gathering g WHERE g.leadUserId = :leadUserId")
    List<Gathering> findAllByLeadUserId(@Param("leadUserId") Long leadUserId);

    @Query("SELECT g FROM Gathering g JOIN Enrollment e on g.id = e.postId WHERE e.memberId = :memberId where g.gatheringStatus = 'FINISHED'")
    List<Gathering> findAllByParticipatedUserId(@Param("memberId") Long memberId);

    @Query("SELECT g FROM Gathering g JOIN Enrollment e on g.id = e.postId WHERE e.memberId = :memberId where g.gatheringStatus = 'GATHERING_PENDING'")
    List<Gathering> findAllByPendingUserId(@Param("memberId") Long memberId);

}
