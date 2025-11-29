package com.arnold.msg.server;

import com.arnold.msg.ZookeeperClientHolder;
import com.arnold.msg.data.InMemoryAtMostOnceMessageConsumer;
import com.arnold.msg.data.InMemoryMessageProducer;
import com.arnold.msg.data.MessageConsumer;
import com.arnold.msg.data.MessageProducer;
import com.arnold.msg.data.model.Message;
import com.arnold.msg.data.model.MessageBatch;
import com.arnold.msg.metadata.model.ClusterKind;
import com.arnold.msg.metadata.model.ClusterMetadata;
import com.arnold.msg.metadata.model.QueueMetadata;
import com.arnold.msg.metadata.model.ResourceType;
import com.arnold.msg.metadata.opeartor.BackendOperator;
import com.arnold.msg.metadata.opeartor.BackendOperatorRegistry;
import com.arnold.msg.metadata.operator.InMemoryBackendOperator;
import com.arnold.msg.metadata.store.MetadataStore;
import com.arnold.msg.metadata.store.MetadataStoreRegistry;
import com.arnold.msg.metadata.store.ZookeeperMetadataStore;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.locks.LockSupport;

@Slf4j
public class InMemoryZKServer {
    public static void main(String[] args) {
        // init zk as meta store
        ZookeeperClientHolder.initialize();
        ZookeeperMetadataStore.initialize();
        // init inmemory as backend data storage
        InMemoryBackendOperator.initialize();


        MetadataStore<ClusterMetadata> clusterMetadataStore = MetadataStoreRegistry.getMetadataStore(ResourceType.CLUSTER);
        ClusterMetadata cluster = new ClusterMetadata();
        cluster.setId("test");
        cluster.setKind(ClusterKind.IN_MEMORY);
        cluster.setProvider("{}");
        clusterMetadataStore.save(cluster);

        MetadataStore<QueueMetadata> queueMetadataStore = MetadataStoreRegistry.getMetadataStore(ResourceType.QUEUE);
        QueueMetadata queue = new QueueMetadata();
        queue.setId("test");
        queue.setCluster("test");
        queue.setProvider("{}");
        queueMetadataStore.save(queue);

        BackendOperator operator = BackendOperatorRegistry.getOperator(cluster.getKind());
        operator.createQueue(cluster, queue);

        for (int i=0; i<25; i++) {
            Message msg = new Message();
            msg.setData("test".getBytes());
            msg.setQueue(queue.getId());
            MessageProducer producer = new InMemoryMessageProducer();
            producer.send(msg);
        }
        for (int i=0; i<2; i++) {
            String consumerID = "testConsumer" + i;
            new Thread(() -> {
                Random random = new Random();
                MessageConsumer consumer = new InMemoryAtMostOnceMessageConsumer(
                        consumerID, queue.getId());
                for (int j = 0; j < 3; j++) {
                    MessageBatch batch = consumer.poll();
                    log.info("{} poll {} batch {} with {} messages",
                            consumerID, j, batch.getBatchId(), batch.getMessages().size());
                    int seconds = random.nextInt(5, 10);
                    LockSupport.parkNanos(Duration.ofSeconds(seconds).toNanos());
                }
            }).start();
        }

    }
}