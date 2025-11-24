package com.arnold.msg.register.exceptions;

public class JsonRuntimeException extends RuntimeException {

    public JsonRuntimeException(String cause, Throwable t) {
        super(cause, t);
    }
}
