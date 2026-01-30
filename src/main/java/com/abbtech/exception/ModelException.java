package com.abbtech.exception;

import com.abbtech.exception.base.BaseErrorService;
import com.abbtech.exception.base.BaseException;
import lombok.Getter;

@Getter
public class ModelException extends BaseException {

    private static final long serialVersionUID = 1L;

    public ModelException(BaseErrorService baseErrorService, Throwable throwable, Object... args) {
        super(baseErrorService, throwable, args);
    }

    public ModelException(BaseErrorService baseErrorService, Object... args) {
        super(baseErrorService, args);
    }
}
