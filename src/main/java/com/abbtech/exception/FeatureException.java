package com.abbtech.exception;

import com.abbtech.exception.base.BaseErrorService;
import com.abbtech.exception.base.BaseException;

public class FeatureException extends BaseException {

    private static final long serialVersionUID = 1L;

    public FeatureException(BaseErrorService baseErrorService, Throwable throwable, Object... args) {
        super(baseErrorService, throwable, args);
    }

    public FeatureException(BaseErrorService baseErrorService, Object... args) {
        super(baseErrorService, args);
    }
}
