package com.abbtech.exception;

import com.abbtech.exception.base.BaseErrorService;
import lombok.Getter;

@Getter
public enum FeatureErrorEnum implements BaseErrorService {

    FEATURE_NOT_FOUND("FEATURE_NOT_FOUND-0001", "FEATURE_NOT_FOUND", 404);

    final String message;
    final int httpStatus;
    final String errorCode;

    FeatureErrorEnum(String message,  String errorCode, int httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public int getHttpStatus() {
        return 0;
    }

    @Override
    public String getErrorCode() {
        return "";
    }
}
