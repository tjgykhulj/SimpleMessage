package com.arnold.msg.exceptions;

public class KafkaAdminOperationException extends RuntimeException {

    public KafkaAdminOperationException(String msg, Exception e) {
        super(msg, e);
    }
}
