package com.ploggingbuddy.global.exception.code;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum ErrorCode implements PloggingBuddyErrorCode{

    // 400 BAD REQUEST
    INVALID_POST_ID(HttpStatus.BAD_REQUEST, 400, "모임 글 id가 올바르지 않습니다.");


    @JsonIgnore
    private final HttpStatus httpStatus;
    private final int code;
    private final String message;
}
