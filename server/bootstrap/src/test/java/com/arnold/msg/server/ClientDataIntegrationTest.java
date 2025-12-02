package com.arnold.msg.server;

import com.arnold.msg.proto.common.ClusterInfo;
import com.arnold.msg.proto.common.ClusterKindEnum;
import com.arnold.msg.proto.common.QueueInfo;
import com.arnold.msg.proto.data.DataServiceGrpc;
import com.arnold.msg.proto.data.MessageProto;
import com.arnold.msg.proto.data.ProduceRequest;
import com.arnold.msg.proto.data.ProduceResponse;
import com.arnold.msg.proto.meta.*;
import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ClientDataIntegrationTest {

    private final MetaServiceGrpc.MetaServiceBlockingStub metaBlockingStub;
    private final DataServiceGrpc.DataServiceBlockingStub dataBlockingStub;

    private static final String cluster = "test-cluster";
    private static final String queue = "test-queue";
    private final String producer = "test-p";

    public ClientDataIntegrationTest() {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("127.0.0.1", 9000)
                .usePlaintext()
                .build();
        this.metaBlockingStub = MetaServiceGrpc.newBlockingStub(channel);
        ManagedChannel dataChannel = ManagedChannelBuilder
                .forAddress("127.0.0.1", 9001)
                .usePlaintext()
                .build();
        this.dataBlockingStub = DataServiceGrpc.newBlockingStub(dataChannel);
    }

    @Test
    public void testCreateCluster() {
        CreateClusterRequest request = CreateClusterRequest.newBuilder()
                .setCluster(ClusterInfo.newBuilder()
                        .setName(cluster)
                        .setKind(ClusterKindEnum.IN_MEMORY)
                        .build())
                .build();
        ClusterResponse response = metaBlockingStub.createCluster(request);
        System.out.println("CreateCluster Response: " + response);
    }

    @Test
    public void testDeleteCluster() {
        DeleteClusterRequest deleteReq = DeleteClusterRequest.newBuilder()
                .setName(cluster)
                .build();
        ClusterResponse deleteResp = metaBlockingStub.deleteCluster(deleteReq);
        System.out.println("DeleteCluster Response: " + deleteResp);
    }

    @Test
    public void testCreateQueue() {
        CreateQueueRequest req = CreateQueueRequest.newBuilder()
                .setQueue(QueueInfo.newBuilder()
                        .setName(queue)
                        .setCluster(cluster)
                        .build())
                .build();
        QueueResponse response = metaBlockingStub.createQueue(req);
        log.info("CreateQueue Response: {}", response);
    }

    @Test
    public void testDeleteQueue() {
        DeleteQueueRequest req = DeleteQueueRequest.newBuilder().setName(queue).build();
        QueueResponse resp = metaBlockingStub.deleteQueue(req);
        log.info("DeleteQueue Response: {}", resp);
    }

    // after create cluster \ create queue
    @Test
    public void testProduce() {
        MessageProto msg = MessageProto.newBuilder()
                .setQueue(queue)
                .setPayload(ByteString.copyFrom("test".getBytes()))
                .build();
        ProduceRequest req = ProduceRequest.newBuilder()
                .setProducer(producer)
                .addMessages(msg)
                .build();
        ProduceResponse resp = dataBlockingStub.produce(req);
        log.info("Produce Response: {}", resp);
    }
}
