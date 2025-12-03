package com.arnold.msg.metadata;

import com.arnold.msg.exceptions.KafkaClusterValidationException;
import com.arnold.msg.metadata.model.ClusterMetadata;
import com.arnold.msg.metadata.model.KafkaClusterMetadata;
import com.arnold.msg.metadata.model.QueueMetadata;
import com.arnold.msg.metadata.opeartor.BackendOperator;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DescribeClusterResult;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

@Slf4j
public class KafkaBackendOperator implements BackendOperator {
    @Override
    public void createQueue(ClusterMetadata cluster, QueueMetadata queue) {
        // TODO
    }

    @Override
    public void deleteQueue(ClusterMetadata cluster, String name) {
        // TODO
    }

    @Override
    public void validateCluster(ClusterMetadata metadata) {
        KafkaClusterMetadata kafkaMetadata = new KafkaClusterMetadata(metadata);
        Properties props = kafkaMetadata.getConfig().toProperties();
        try (AdminClient adminClient = AdminClient.create(props)) {
            DescribeClusterResult clusterResult = adminClient.describeCluster();
            String clusterId = clusterResult.clusterId().get(10, TimeUnit.SECONDS);
            log.debug("Kafka connection test passed, cluster id is {}", clusterId);
        } catch (Exception e) {
            throw new KafkaClusterValidationException("Kafka connection test failed", e);
        }
    }
}
