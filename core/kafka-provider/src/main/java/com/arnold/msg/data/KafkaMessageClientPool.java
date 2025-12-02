package com.arnold.msg.data;

import com.arnold.msg.metadata.model.ProducerMetadata;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class KafkaMessageClientPool implements MessageClientPool {

    private final Map<String, MessageProducer> producerPool = new ConcurrentHashMap<>();
    private final Map<String, MessageConsumer> consumerPool = new ConcurrentHashMap<>();

    @Override
    public MessageConsumer getConsumer(String consumer) {
        // TODO
        return consumerPool.computeIfAbsent(consumer, c -> null);
    }

    @Override
    public MessageProducer getProducer(String producer) {
        return producerPool.computeIfAbsent(producer, p -> {
            ProducerMetadata metadata = new ProducerMetadata();
            metadata.setId(producer);
            return new KafkaMessageProducer(metadata);
        });
    }
}
