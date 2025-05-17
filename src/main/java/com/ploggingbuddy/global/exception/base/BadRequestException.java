package com.ploggingbuddy.global.exception.base;

import com.ploggingbuddy.global.exception.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BadRequestException extends RuntimeException{
    private final ErrorCode errorCode;
}
