package com.arnold.msg.data;

import com.arnold.msg.InMemoryProviderBootstrap;
import com.arnold.msg.data.model.Message;
import com.arnold.msg.data.model.MessageBatch;
import com.arnold.msg.metadata.model.*;
import com.arnold.msg.metadata.opeartor.BackendOperator;
import com.arnold.msg.metadata.opeartor.BackendOperatorRegistry;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryMessageDataTest {

    @Test
    public void test() {
        InMemoryProviderBootstrap.initAll();
        BackendOperator operator = BackendOperatorRegistry.getOperator(ClusterKind.IN_MEMORY);
        MessageClientPool pool = MessageClientPoolRegistry.getPool(ClusterKind.IN_MEMORY);

        String queue = "test";
        QueueMetadata queueMetadata = new QueueMetadata();
        queueMetadata.setId(queue);
        operator.createQueue(new ClusterMetadata(), queueMetadata);

        Message msg = new Message();
        msg.setData("test".getBytes());
        msg.setQueue(queue);
        MessageProducer producer = pool.getProducer("p");
        producer.send(msg);

        MessageConsumer consumer = pool.getConsumer("testConsumer1");
        MessageBatch batch = consumer.poll(queue);
        assertNotNull(batch.getMessages());
        assertEquals(1, batch.getMessages().size());
        assertEquals(msg, batch.getMessages().get(0));

        // poll again by the same consumer, nothing it can poll
        batch = consumer.poll(queue);
        assertNotNull(batch.getMessages());
        assertTrue(batch.getMessages().isEmpty());

        // poll again by a new consumer id, it should poll 1 message from the beginning as expect
        consumer = pool.getConsumer("testConsumer2");
        batch = consumer.poll(queue);
        assertNotNull(batch.getMessages());
        assertEquals(1, batch.getMessages().size());
    }
}
