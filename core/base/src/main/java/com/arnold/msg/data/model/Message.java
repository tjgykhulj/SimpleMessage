package com.arnold.msg.data.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {
    private byte[] data;
    private String queue;
}
