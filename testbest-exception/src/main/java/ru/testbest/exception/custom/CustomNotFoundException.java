package ru.testbest.exception.custom;

import lombok.Getter;
import lombok.Setter;
import ru.testbest.exception.ErrorType;

@Setter
@Getter
public class CustomNotFoundException extends RuntimeException {

    private ErrorType errorType;

    public CustomNotFoundException() {
        this.errorType = ErrorType.NOT_FOUND_EXCEPTION;
    }

}
