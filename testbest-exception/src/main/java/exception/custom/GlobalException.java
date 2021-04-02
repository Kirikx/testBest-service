package exception.custom;

import lombok.Getter;
import lombok.Setter;
import exception.ErrorType;

@Getter
@Setter
public class GlobalException extends RuntimeException {

    private ErrorType errorType;

    public GlobalException() {
        this.errorType = ErrorType.GLOBAL_EXCEPTION;
    }

}

