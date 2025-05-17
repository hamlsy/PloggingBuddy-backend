package com.ploggingbuddy.domain.postImage.repository;

import com.ploggingbuddy.domain.postImage.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostImageRepository extends JpaRepository<PostImage, Long> {
}