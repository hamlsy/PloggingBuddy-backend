package com.ploggingbuddy.domain.gathering.service;

import com.ploggingbuddy.domain.gathering.entity.Gathering;
import com.ploggingbuddy.domain.gathering.entity.GatheringStatus;
import com.ploggingbuddy.domain.gathering.repository.GatheringRepository;
import com.ploggingbuddy.application.validator.GatheringValidator;
import com.ploggingbuddy.global.exception.base.BadRequestException;
import com.ploggingbuddy.global.exception.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class GatheringService {
    private final GatheringRepository gatheringRepository;
    private final GatheringValidator gatheringValidator;

    private final double RADIUS_KM = 5.0; //반경
    private final int EARTH_RADIUS_KM = 6371; // 지구 반지름

    private double minLat;
    private double maxLat;
    private double minLon;
    private double maxLon;


    // 신규 생성
    public Gathering save(Gathering gathering) {
        return gatheringRepository.save(gathering);
    }

    public Gathering getGatheringData(Long postId) {
        gatheringValidator.validateGatheringPostIdExist(postId);
        return gatheringRepository.findById(postId).get();
    }

    public List<Gathering> getNearGatherings(Double latitude, Double longitude) {
        //5km 인근 모임 검색
        getMaxAndMinLonAndLat(latitude, longitude);

        //1차 연산
        List<Gathering> candidateGatheringList = gatheringRepository.findByLatitudeLongitudeInRange(minLat, maxLat, minLon, maxLon);

        //2차 연산 후 리턴
        return candidateGatheringList.stream()
                .filter(g -> calculateDistance(latitude, longitude, g.getSpotLatitude(), g.getSpotLongitude()) <= RADIUS_KM)
                .toList();

    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_KM * c;
    }

    // 쿼리문에서 사용할 max, min 위경도 값 계산
    private void getMaxAndMinLonAndLat(Double latitude, Double longitude) {
        double latDelta = RADIUS_KM / 111.0;
        double lonDelta = RADIUS_KM / (111.0 * Math.cos(Math.toRadians(latitude)));

        minLat = latitude - latDelta;
        maxLat = latitude + latDelta;
        minLon = longitude - lonDelta;
        maxLon = longitude + lonDelta;
    }

    // 게시글 상태 수정
    // 유저에 의해 조기 마감시 GatheringStatus 값을 null로 전달
    public void updatePostStatus(Long postId, GatheringStatus postStatus, Long requestUserId, Long enrolledCount) {
        Gathering gathering = getGatheringPost(postId);

        gatheringValidator.validateWriteUser(requestUserId, gathering);

        if (postStatus == null) {
            if (enrolledCount == 0) {
                gathering.updatePostStatus(GatheringStatus.GATHERING_FAILED);
            } else if (gathering.getParticipantMaxNumber() > enrolledCount) {
                gathering.updatePostStatus(GatheringStatus.GATHERING_PENDING);
            }
        } else {
            gathering.updatePostStatus(postStatus);
        }
    }

    // 인원 수정
    public void updatePostGatheringAmount(Long postId, Long participantMaxNumber, Long memberId) {
        Gathering gathering = getGatheringPost(postId);
        gatheringValidator.validateWriteUser(memberId, gathering);
        if (gathering.getParticipantMaxNumber() > participantMaxNumber) {
            throw new BadRequestException(ErrorCode.INVALID_UPDATE_GATHERING_AMOUNT_SIZE);
        }
        gathering.updateParticipantMaxNumber(participantMaxNumber);
    }

    private Gathering getGatheringPost(Long postId) {
        return gatheringRepository.findById(postId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.INVALID_POST_ID));
    }

}
