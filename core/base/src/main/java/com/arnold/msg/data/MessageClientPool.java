package com.arnold.msg.data;

public interface MessageClientPool {

    MessageConsumer getConsumer(String consumer);
    MessageProducer getProducer(String producer);

}
