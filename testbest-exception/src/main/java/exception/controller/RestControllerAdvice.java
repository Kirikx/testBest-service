package exception.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import exception.ErrorType;
import exception.ResponseMessage;
import exception.custom.CustomBadRequest;
import exception.custom.CustomNotFoundException;
import exception.custom.GlobalException;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@org.springframework.web.bind.annotation.RestControllerAdvice
public class RestControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomBadRequest.class)
    public ResponseMessage exceptionHandler(HttpServletRequest request, CustomBadRequest exception) {
        return getResponseMessage(request, exception.getErrorType());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(GlobalException.class)
    public ResponseMessage exceptionHandler(HttpServletRequest request, GlobalException exception) {
        return getResponseMessage(request, exception.getErrorType());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CustomNotFoundException.class)
    public ResponseMessage exceptionHandler(HttpServletRequest request, CustomNotFoundException exception) {
        return getResponseMessage(request, exception.getErrorType());
    }

    private ResponseMessage getResponseMessage(HttpServletRequest request, ErrorType errorType) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setTime(new Date());
        responseMessage.setStatus(errorType.getCode());
        responseMessage.setMsg(errorType.getMsg());
        responseMessage.setMethod(request.getMethod());
        responseMessage.setPath(request.getRequestURL().toString() + "?" + request.getQueryString());
        return responseMessage;
    }


}
