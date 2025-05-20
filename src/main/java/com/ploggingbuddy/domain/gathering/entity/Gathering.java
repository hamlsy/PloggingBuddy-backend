package com.ploggingbuddy.domain.gathering.entity;

import jakarta.persistence.*;
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

    private String spotStringAddress;

    private Double spotLatitude;

    private Double spotLongitude;

    @Lob
    private String content;

    private LocalDateTime gatheringEndTime;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private GatheringStatus postStatus = GatheringStatus.GATHERING;

    public void updatePostStatus(GatheringStatus postStatus) {
        this.postStatus = postStatus;
    }

    public void updateParticipantMaxNumber(Long participantNumberMax) {
        this.participantMaxNumber = participantNumberMax;
    }

    public Gathering(Long leadUserId, String gatheringName, String content, Long participantMaxNumber, String spotStringAddress, Double spotLatitude, Double spotLongitude, LocalDateTime gatheringEndTime) {
        this.leadUserId = leadUserId;
        this.gatheringName = gatheringName;
        this.content = content;
        this.participantMaxNumber = participantMaxNumber;
        this.spotStringAddress = spotStringAddress;
        this.createdAt = LocalDateTime.now();
        this.spotLatitude = spotLatitude;
        this.spotLongitude = spotLongitude;
        this.gatheringEndTime = gatheringEndTime;
    }

}
