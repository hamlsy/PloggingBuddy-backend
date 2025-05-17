package com.ploggingbuddy.domain.member.repository;

import com.ploggingbuddy.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsername(String username);

    @Query("select m from Member m where m.id In :ids")
    List<Member> findAllByIdIn(@Param("ids") List<Long> ids);
}
