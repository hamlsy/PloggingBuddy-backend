package com.ploggingbuddy.domain.gathering.repository;

import com.ploggingbuddy.domain.gathering.entity.Gathering;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GatheringRepository extends JpaRepository<Gathering, Long> {
    void saveGathering(Gathering gathering);

    Optional<Gathering> findById(Long id);
}
