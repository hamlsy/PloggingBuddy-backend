package com.ploggingbuddy.domain.postImage.repository;

import com.ploggingbuddy.domain.postImage.entity.PostImage;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostImageRepository extends JpaRepository<PostImage, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM PostImage pi WHERE pi.postId = :postId")
    void deleteByPostId(@Param("postId") Long postId);

    @Query("SELECT pi FROM PostImage pi WHERE pi.postId = :postId")
    List<PostImage> findAllByPostId(@Param("postId") Long postId);
}