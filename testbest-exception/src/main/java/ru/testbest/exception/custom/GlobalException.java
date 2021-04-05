package ru.testbest.exception.custom;

import lombok.Getter;
import lombok.Setter;
import ru.testbest.exception.ErrorType;

@Getter
@Setter
public class GlobalException extends RuntimeException {

    private ErrorType errorType;

    public GlobalException(String message) {
        super(message);
    }

    public GlobalException() {
        this.errorType = ErrorType.GLOBAL_EXCEPTION;
    }

}

