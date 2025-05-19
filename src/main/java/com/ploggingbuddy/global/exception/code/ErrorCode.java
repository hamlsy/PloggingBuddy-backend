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
    POST_NOT_GATHERING_NOW(HttpStatus.BAD_REQUEST, 400, "현재 해당 모임 글이 모집하고 있지 않습니다."),
    EXCEED_PARTICIPANT_LIMIT(HttpStatus.BAD_REQUEST, 400, "신청가능한 인원이 초과하였습니다."),
    DUPLICATED_ENROLLMENT(HttpStatus.BAD_REQUEST, 400, "이미 신청한 모임 글 입니다."),
    INVALID_GATHERING_END_TIME(HttpStatus.BAD_REQUEST, 400, "현시간보다 모집 마감시간이 이를 수 없습니다."),

    // 403 forbidden
    FORBIDDEN_EDIT_POST(HttpStatus.FORBIDDEN, 403, "모임 글 수정 권한이 없는 유저입니다."),

    // 500 internal server error
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "서버 내부 오류입니다. 서버 관리자에게 연락해주세요");

    @JsonIgnore
    private final HttpStatus httpStatus;
    private final int code;
    private final String message;
}
