package com.arnold.msg.exceptions;

public class MessageSendException extends RuntimeException {

    public MessageSendException(String msg) {
        super(msg);
    }

    public MessageSendException(String msg, Exception e) {
        super(msg, e);
    }
}
