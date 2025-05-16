package com.ploggingbuddy.global.exception.code;

import org.springframework.http.HttpStatus;

public interface PloggingBuddyErrorCode {
    HttpStatus getHttpStatus();
    int getCode();
    String getMessage();
}
