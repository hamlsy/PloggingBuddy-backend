package com.ploggingbuddy.presentation.gathering.controller;

import com.ploggingbuddy.application.gathering.CreateGatheringUseCase;
import com.ploggingbuddy.application.gathering.UpdateGatheringAmountUseCase;
import com.ploggingbuddy.application.gathering.UpdatePostStatusAsDeletedUseCase;
import com.ploggingbuddy.domain.member.entity.Member;
import com.ploggingbuddy.presentation.gathering.dto.request.PostGatheringPostDto;
import com.ploggingbuddy.presentation.gathering.dto.request.UpdateGatheringAmountDto;
import com.ploggingbuddy.presentation.gathering.dto.request.UpdatePostStatusAsDeletedDto;
import com.ploggingbuddy.security.aop.CurrentMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gathering")
@RequiredArgsConstructor
@Tag(name = "Gathering Api", description = "게시글 API")
public class GatheringController {
    private final CreateGatheringUseCase createGatheringUseCase;
    private final UpdatePostStatusAsDeletedUseCase updatePostStatusAsDeletedUseCase;
    private final UpdateGatheringAmountUseCase updateGatheringAmountUseCase;

    @PostMapping("/new")
    @Operation(summary = "모집 게시글 작성", description = "새 모집 게시글을 작성하는 api입니다.")
    public ResponseEntity<Void> postNewGathering(
            @CurrentMember Member member,
            @RequestBody PostGatheringPostDto requestBody
    ) {
        createGatheringUseCase.execute(requestBody, member.getId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete")
    @Operation(summary = "모집 게시글 삭제", description = "본인이 작성한 게시글을 삭제하는 api입니다.")
    public ResponseEntity<Void> updateGatheringAsDeleted(
            @CurrentMember Member member,
            @RequestBody UpdatePostStatusAsDeletedDto requestBody
    ) {
        updatePostStatusAsDeletedUseCase.execute(requestBody, member.getId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update")
    @Operation(summary = "모집 게시글 인원 수정", description = "본인이 작성한 게시글의 모집 인원을 수정하는 api입니다.")
    public ResponseEntity<Void> updateGatheringAmount(
            @CurrentMember Member member,
            @RequestBody UpdateGatheringAmountDto requestBody
    ) {
        updateGatheringAmountUseCase.execute(requestBody, member.getId());
        return ResponseEntity.ok().build();
    }

}
