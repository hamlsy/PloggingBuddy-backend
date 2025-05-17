package com.ploggingbuddy.domain.gathering.repository;

import com.ploggingbuddy.domain.gathering.entity.Gathering;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GatheringRepository extends JpaRepository<Gathering, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT g FROM Gathering g WHERE g.id = :id")
    Optional<Gathering> findWithLockById(@Param("id") Long id);

}
