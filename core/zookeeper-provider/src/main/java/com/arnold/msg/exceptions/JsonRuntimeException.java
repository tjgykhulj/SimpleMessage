package com.arnold.msg.exceptions;

public class JsonRuntimeException extends RuntimeException {

    public JsonRuntimeException(String cause, Throwable t) {
        super(cause, t);
    }
}
