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

    @Query("SELECT g FROM Gathering g WHERE g.leadUserId = :leadUserId order by g.createdAt desc limit 3")
    List<Gathering> findAllByLeadUserIdOrderByDescLimit3(@Param("leadUserId") Long leadUserId);

    @Query("SELECT g FROM Gathering g JOIN Enrollment e on g.id = e.postId WHERE e.memberId = :memberId where g.gatheringStatus = 'FINISHED' order by g.createdAt desc limit 3")
    List<Gathering> findAllByParticipatedUserIdOrderByDescLimit3(@Param("memberId") Long memberId);

    @Query("SELECT g FROM Gathering g JOIN Enrollment e on g.id = e.postId WHERE e.memberId = :memberId where g.gatheringStatus = 'GATHERING_PENDING' order by g.createdAt desc limit 3")
    List<Gathering> findAllByPendingUserIdOrderByDescLimit3(@Param("memberId") Long memberId);

}
