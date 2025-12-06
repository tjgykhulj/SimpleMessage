package com.arnold.msg.service.meta;

import com.arnold.msg.proto.common.ClusterInfo;
import com.arnold.msg.proto.common.ConsumerInfo;
import com.arnold.msg.proto.common.ProducerInfo;
import com.arnold.msg.proto.common.QueueInfo;
import com.arnold.msg.proto.meta.*;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MetaServiceGrpcImpl extends MetaServiceGrpc.MetaServiceImplBase {

    private final MetadataStoreService metadataService;

    public MetaServiceGrpcImpl() {
        this.metadataService = MetadataStoreService.getInstance();
    }

    @Override
    public void createQueue(CreateQueueRequest request,
                            StreamObserver<QueueInfo> responseObserver) {
        try {
            QueueInfo info = metadataService.createQueue(request.getQueue());
            responseObserver.onNext(info);
            responseObserver.onCompleted();
        } catch (Exception e) {
            handleError(responseObserver, e, "Failed to create queue");
        }
    }

    public void deleteQueue(DeleteQueueRequest request,
                            StreamObserver<QueueInfo> responseObserver) {
        try {
            QueueInfo info = metadataService.deleteQueue(request.getName());
            responseObserver.onNext(info);
            responseObserver.onCompleted();
        } catch (Exception e) {
            handleError(responseObserver, e, "Failed to create queue");
        }
    }

    @Override
    public void createCluster(CreateClusterRequest request,
                              StreamObserver<ClusterInfo> responseObserver) {
        try {
            ClusterInfo cluster = metadataService.createCluster(request.getCluster());
            responseObserver.onNext(cluster);
            responseObserver.onCompleted();
        } catch (Exception e) {
            handleError(responseObserver, e, "Failed to create queue");
        }
    }

    @Override
    public void deleteCluster(DeleteClusterRequest request,
                              StreamObserver<ClusterInfo> responseObserver) {
        try {
            ClusterInfo cluster = metadataService.deleteCluster(request.getName());
            responseObserver.onNext(cluster);
            responseObserver.onCompleted();
        } catch (Exception e) {
            handleError(responseObserver, e, "Failed to create queue");
        }
    }

    @Override
    public void createProducer(CreateProducerRequest request,
                               StreamObserver<ProducerInfo> responseObserver) {
        try {
            ProducerInfo info = metadataService.createProducer(request.getProducer());
            responseObserver.onNext(info);
            responseObserver.onCompleted();
        } catch (Exception e) {
            handleError(responseObserver, e, "Failed to create queue");
        }
    }

    @Override
    public void createConsumer(CreateConsumerRequest request,
                               StreamObserver<ConsumerInfo> responseObserver) {
        try {
            ConsumerInfo info = metadataService.createConsumer(request.getConsumer());
            responseObserver.onNext(info);
            responseObserver.onCompleted();
        } catch (Exception e) {
            handleError(responseObserver, e, "Failed to create queue");
        }
    }

    @Override
    public void assignDataServer(AssignDataServerRequest request,
                                 StreamObserver<AssignDataServerResponse> responseObserver) {
        try {
            AssignDataServerResponse response =
                    AssignDataServerResponse.newBuilder().build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (Exception e) {
            handleError(responseObserver, e, "Failed to allocate data node");
        }
    }

    private <T> void handleError(StreamObserver<T> responseObserver, Exception e, String context) {
        log.error("{}: {}", context, e.getMessage(), e);
        responseObserver.onError(Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
    }

}