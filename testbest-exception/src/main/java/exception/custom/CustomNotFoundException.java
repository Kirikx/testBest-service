package exception.custom;

import lombok.Getter;
import lombok.Setter;
import exception.ErrorType;

@Setter
@Getter
public class CustomNotFoundException extends RuntimeException {

    private ErrorType errorType;

    public CustomNotFoundException() {
        this.errorType = ErrorType.NOT_FOUND_EXCEPTION;
    }

}
