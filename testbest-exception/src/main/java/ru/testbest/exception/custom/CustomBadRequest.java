package ru.testbest.exception.custom;

import lombok.Getter;
import lombok.Setter;
import ru.testbest.exception.ErrorType;

@Getter
@Setter
public class CustomBadRequest extends RuntimeException {

    private ErrorType errorType;

    public CustomBadRequest() {
        this.errorType = ErrorType.BAD_REQUEST;
    }
}
