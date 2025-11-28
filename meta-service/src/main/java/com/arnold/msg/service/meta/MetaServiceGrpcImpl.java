package com.arnold.msg.service.meta;

import com.arnold.msg.proto.common.CommonProto;
import com.arnold.msg.proto.common.DataServer;
import com.arnold.msg.proto.common.QueueInfo;
import com.arnold.msg.proto.common.Response;
import com.arnold.msg.proto.meta.*;
import com.arnold.msg.registry.DataNode;
import com.arnold.msg.registry.NodeRegistry;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class MetaServiceGrpcImpl extends MetaServiceGrpc.MetaServiceImplBase {


    private final NodeRegistry nodeRegistry;

    public MetaServiceGrpcImpl(NodeRegistry nodeRegistry) {
        this.nodeRegistry = nodeRegistry;
    }

    @Override
    public void createQueue(CreateQueueRequest request,
                            StreamObserver<CreateQueueResponse> responseObserver) {
        try {
            CreateQueueResponse response = CreateQueueResponse.newBuilder()
                            .setBase(createSuccessResponse())
                            .setQueueInfo(QueueInfo.newBuilder().build())
                            .build();

            responseObserver.onNext(response);
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

    private Response createSuccessResponse() {
        return Response.newBuilder()
                .setSuccess(true)
                .setMessage("OK")
                .build();
    }

    private <T> void handleError(StreamObserver<T> responseObserver, Exception e, String context) {
        log.error("{}: {}", context, e.getMessage(), e);
        // TODO
    }

}