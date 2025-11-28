package com.arnold.msg.data;

import com.arnold.msg.data.model.Message;
import com.arnold.msg.data.model.MessageBatch;
import com.arnold.msg.data.model.MessageBatchAck;
import com.arnold.msg.metadata.model.ClusterKind;
import com.arnold.msg.metadata.model.ClusterMetadata;
import com.arnold.msg.metadata.model.QueueMetadata;
import com.arnold.msg.metadata.opeartor.BackendOperatorRegistry;
import com.arnold.msg.metadata.operator.InMemoryBackendOperator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryMessageDataTest {

    @Test
    public void test() {
        String queue = "test";
        QueueMetadata queueMetadata = new QueueMetadata();
        queueMetadata.setId(queue);
        InMemoryBackendOperator.initialize();
        BackendOperatorRegistry.getOperator(ClusterKind.IN_MEMORY)
                .createQueue(new ClusterMetadata(), queueMetadata);

        Message msg = new Message();
        msg.setData("test".getBytes());
        msg.setQueue(queue);
        MessageProducer producer = new InMemoryMessageProducer();
        producer.send(msg);

        MessageConsumer consumer = new InMemoryAtMostOnceMessageConsumer("testConsumer1", queue);
        MessageBatch batch = consumer.poll();
        assertNotNull(batch.getMessages());
        assertEquals(1, batch.getMessages().size());
        assertEquals(msg, batch.getMessages().get(0));

        // poll again by the same consumer, nothing it can poll
        batch = consumer.poll();
        assertNotNull(batch.getMessages());
        assertTrue(batch.getMessages().isEmpty());

        // poll again by a new consumer id, it should poll 1 message from the beginning as expect
        consumer = new InMemoryAtMostOnceMessageConsumer("testConsumer2", queue);
        batch = consumer.poll();
        assertNotNull(batch.getMessages());
        assertEquals(1, batch.getMessages().size());
    }
}
