package com.ploggingbuddy.domain.gathering.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Gathering {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long leadUserId;

    private String gatheringName;

    private Long participantNumberMin;

    private Long participantNumberMax;

    // TODO 추후 프론트와 주소관련 로직 결정 후 반영 예정
    private String spotName;

    private String content;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private GatheringStatus postStatus;

    public void updatePostStatus(Long postId, GatheringStatus postStatus){
        this.postStatus = postStatus;
    }

    public Gathering(Long leadUserId, String gatheringName, String content, Long participantNumberMin, Long participantNumberMax, String spotName) {
        this.leadUserId = leadUserId;
        this.gatheringName = gatheringName;
        this.content = content;
        this.participantNumberMin = participantNumberMin;
        this.participantNumberMax = participantNumberMax;
        this.spotName = spotName;
        this.createdAt = LocalDateTime.now();
    }

}
