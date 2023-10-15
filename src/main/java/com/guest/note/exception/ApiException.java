package com.guest.note.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException{
    private HttpStatus httpCode;
    public ApiException(HttpStatus httpCode, String message) {
        super(message);
        this.httpCode= httpCode;
    }

    public HttpStatus getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(HttpStatus httpCode) {
        this.httpCode = httpCode;
    }
}
