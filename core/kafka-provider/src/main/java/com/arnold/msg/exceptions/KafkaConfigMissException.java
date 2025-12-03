package com.arnold.msg.exceptions;

public class KafkaConfigMissException extends RuntimeException {

    public KafkaConfigMissException(String msg) {
        super(msg);
    }
}
