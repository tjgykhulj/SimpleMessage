package com.arnold.msg.exceptions;

public class KafkaClusterValidationException extends RuntimeException {

    public KafkaClusterValidationException(String msg, Exception e) {
        super(msg, e);
    }
}
