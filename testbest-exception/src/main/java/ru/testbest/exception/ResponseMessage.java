package ru.testbest.exception;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseMessage {

    String status;
    String msg;
    String path;
    String method;
    Date time;
    String info;

}
