package com.ploggingbuddy.global.exception.code;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum ErrorCode implements PloggingBuddyErrorCode{

    // 400 BAD REQUEST
    INVALID_POST_ID(HttpStatus.BAD_REQUEST, 400, "모임 글 id가 올바르지 않습니다."),
    INVALID_UPDATE_GATHERING_AMOUNT_SIZE(HttpStatus.BAD_REQUEST, 400, "수정 후 모임 인원이 수정 전 모임 인원보다 작을 수 없습니다."),

    // 403 forbidden
    FORBIDDEN_EDIT_POST(HttpStatus.FORBIDDEN, 403, "모임 글 수정 권한이 없는 유저입니다.");


    @JsonIgnore
    private final HttpStatus httpStatus;
    private final int code;
    private final String message;
}
