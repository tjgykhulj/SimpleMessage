package com.arnold.msg.service.meta;

import com.arnold.msg.proto.common.ClusterInfo;
import com.arnold.msg.proto.common.QueueInfo;
import com.arnold.msg.proto.common.Response;
import com.arnold.msg.proto.meta.*;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
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
                            StreamObserver<QueueResponse> responseObserver) {
        try {
            QueueInfo info = metadataService.createQueue(request.getQueue());
            QueueResponse response = QueueResponse.newBuilder()
                    .setBase(successResponse())
                    .setQueue(info)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            handleError(responseObserver, e, "Failed to create queue");
        }
    }

    public void deleteQueue(DeleteQueueRequest request,
                            StreamObserver<QueueResponse> responseObserver) {
        try {
            QueueInfo info = metadataService.deleteQueue(request.getName());
            QueueResponse.Builder builder = QueueResponse.newBuilder()
                    .setBase(successResponse());
            if (info != null) {
                builder.setQueue(info);
            }
            responseObserver.onNext(builder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            handleError(responseObserver, e, "Failed to create queue");
        }
    }

    @Override
    public void createCluster(CreateClusterRequest request,
                              StreamObserver<ClusterResponse> responseObserver) {
        try {
            ClusterInfo cluster = metadataService.createCluster(request.getCluster());
            ClusterResponse response = ClusterResponse.newBuilder()
                    .setBase(successResponse())
                    .setCluster(cluster)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            handleError(responseObserver, e, "Failed to create queue");
        }
    }

    @Override
    public void deleteCluster(DeleteClusterRequest request,
                              StreamObserver<ClusterResponse> responseObserver) {
        try {
            ClusterInfo cluster = metadataService.deleteCluster(request.getName());
            ClusterResponse.Builder responseBuilder = ClusterResponse.newBuilder()
                    .setBase(successResponse());
            if (cluster != null) {
                responseBuilder.setCluster(cluster);
            }
            responseObserver.onNext(responseBuilder.build());
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

    private Response successResponse() {
        return Response.newBuilder()
                .setSuccess(true)
                .setMessage("OK")
                .build();
    }

    private <T> void handleError(StreamObserver<T> responseObserver, Exception e, String context) {
        log.error("{}: {}", context, e.getMessage(), e);
        responseObserver.onError(Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
    }

}