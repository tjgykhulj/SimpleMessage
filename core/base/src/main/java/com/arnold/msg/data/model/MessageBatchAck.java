package com.arnold.msg.data.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MessageBatchAck {
    private String batchId;
    List<Message> discardMessages;
    List<Message> retryMessages;
    long retryDelay;
}
