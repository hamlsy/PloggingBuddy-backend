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

    private Long participantMaxNumber;

    private String spotName;

    private Float spotLatitude;

    private Float spotLongitude;

    private String content;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private GatheringStatus postStatus;

    public void updatePostStatus(GatheringStatus postStatus){
        this.postStatus = postStatus;
    }

    public Gathering(Long leadUserId, String gatheringName, String content, Long participantNumberMax, String spotName, Float spotLatitude, Float spotLongitude) {
        this.leadUserId = leadUserId;
        this.gatheringName = gatheringName;
        this.content = content;
        this.participantMaxNumber = participantMaxNumber;
        this.spotName = spotName;
        this.createdAt = LocalDateTime.now();
        this.spotLatitude = spotLatitude;
        this.spotLongitude = spotLongitude;
    }

}
