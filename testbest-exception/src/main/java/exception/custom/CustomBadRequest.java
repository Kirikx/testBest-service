package exception.custom;

import lombok.Getter;
import lombok.Setter;
import exception.ErrorType;

@Getter
@Setter
public class CustomBadRequest extends RuntimeException {

    private ErrorType errorType;

    public CustomBadRequest() {
        this.errorType = ErrorType.BAD_REQUEST;
    }
}
