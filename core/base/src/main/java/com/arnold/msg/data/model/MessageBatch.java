package com.arnold.msg.data.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MessageBatch {
    private String batchId;
    private List<Message> messages;
}
