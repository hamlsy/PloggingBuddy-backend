package com.ploggingbuddy.global.auth.controller;

import com.ploggingbuddy.global.auth.service.KakaoClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/kakao/test")
public class KakaoController {

    private final KakaoClientService kakaoClientService;

    //test
    @GetMapping("/unlink/{id}")
    public ResponseEntity<?> unlink(@PathVariable("id") Long memberId) {
        kakaoClientService.unlinkKakaoAccount(memberId);
        return ResponseEntity.ok("Kakao account unlinked successfully.");
    }

}
