package com.abbtech.exception;

import com.abbtech.exception.base.BaseErrorService;

public enum ModelErrorEnum implements BaseErrorService {

    MODEL_NOT_FOUND("MODEL_NOT_FOUND-0001", "MODEL_NOT_FOUND", 404);

    final String message;
    final int httpStatus;
    final String errorCode;

    ModelErrorEnum(String errorCode, String message, int httpStatus) {
        this.errorCode = errorCode;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }
}
