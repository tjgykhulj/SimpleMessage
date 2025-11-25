package com.arnold.msg.registry.exceptions;

public class JsonRuntimeException extends RuntimeException {

    public JsonRuntimeException(String cause, Throwable t) {
        super(cause, t);
    }
}
