package ru.testbest.exception;

public enum ErrorType {

    GLOBAL_EXCEPTION("500", "внутренняя ошибка сервера"),
    BAD_REQUEST("400", "неправильный, некорректный запрос"),
    NOT_FOUND_EXCEPTION("404", "не найдено");

    private String code;
    private String msg;

    ErrorType(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
