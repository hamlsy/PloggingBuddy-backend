package com.ploggingbuddy.domain.postImage.service;

import com.ploggingbuddy.domain.postImage.entity.PostImage;
import com.ploggingbuddy.domain.postImage.repository.PostImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostImageService {
    private final PostImageRepository postImageRepository;

    public void saveAll(List<String> urlList, Long postId) {
        List<PostImage> postImages = urlList.stream()
                .map(imageUrl -> new PostImage(postId, imageUrl))
                .collect(Collectors.toList());

        postImageRepository.saveAll(postImages);
    }

    public void deletePreviousImages(Long postId) {
        postImageRepository.deleteByPostId(postId);
    }

    public List<String> getPostImageList(Long postId) {
        return postImageRepository.findAllByPostId(postId).stream().map(PostImage::getUrl).collect(Collectors.toList());
    }
}
