package com.arnold.msg.data;

import com.arnold.msg.data.model.Message;
import com.arnold.msg.data.model.MessageBatch;
import com.arnold.msg.data.model.MessageBatchAck;

import java.util.*;

import static com.arnold.msg.data.InMemoryMessageData.DATA;
import static com.arnold.msg.data.InMemoryMessageData.OFFSETS;

public class InMemoryAtMostOnceMessageConsumer implements MessageConsumer {

    private final static int BATCH_MESSAGE_COUNT = 10;
    private final List<Message> queue;
    private final String consumer;


    public InMemoryAtMostOnceMessageConsumer(String consumer, String queue) {
        this.consumer = consumer;
        this.queue = DATA.get(queue);
    }

    @Override
    public synchronized MessageBatch poll() {
        List<Message> messages = new ArrayList<>();
        for (int i=0; i<BATCH_MESSAGE_COUNT; i++) {
            Integer off = OFFSETS.getOrDefault(consumer, 0);
            if (queue.size() <= off) {
                break;
            }
            messages.add(queue.get(off));
            OFFSETS.put(consumer, off + 1);
        }
        MessageBatch batch = new MessageBatch();
        batch.setMessages(messages);
        batch.setBatchId(UUID.randomUUID().toString());
        return batch;
    }

    @Override
    public void acknowledge(MessageBatchAck batchAck) {
        // do nothing
    }
}
