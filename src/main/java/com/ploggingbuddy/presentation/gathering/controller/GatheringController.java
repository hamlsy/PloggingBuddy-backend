package com.ploggingbuddy.presentation.gathering.controller;

import com.ploggingbuddy.application.gathering.CreateGatheringUseCase;
import com.ploggingbuddy.domain.member.entity.Member;
import com.ploggingbuddy.presentation.gathering.dto.request.PostGatheringPostDto;
import com.ploggingbuddy.security.aop.CurrentMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gathering")
@RequiredArgsConstructor
public class GatheringController {
    private final CreateGatheringUseCase createGatheringUseCase;

    @PostMapping
    public ResponseEntity<Void> postNewGathering(
            @CurrentMember Member member,
            @RequestBody PostGatheringPostDto requestBody
            ){
        createGatheringUseCase.execute(requestBody, member.getId());
        return ResponseEntity.ok().build();
    }
}
