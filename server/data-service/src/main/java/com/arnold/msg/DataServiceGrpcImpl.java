package com.arnold.msg;

import com.arnold.msg.data.MessageClientPoolRegistry;
import com.arnold.msg.data.MessageProducer;
import com.arnold.msg.data.model.Message;
import com.arnold.msg.metadata.model.ClusterKind;
import com.arnold.msg.proto.data.DataServiceGrpc;
import com.arnold.msg.proto.data.MessageProto;
import com.arnold.msg.proto.data.ProduceRequest;
import com.arnold.msg.proto.data.ProduceResponse;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class DataServiceGrpcImpl extends DataServiceGrpc.DataServiceImplBase {

    public void produce(ProduceRequest request,
                        StreamObserver<ProduceResponse> responseObserver) {
        try {
            String id = request.getProducer();
            ClusterKind kind = getClusterKind(id);
            MessageProducer producer = MessageClientPoolRegistry.getPool(kind).getProducer(id);
            List<Message> list = request.getMessagesList()
                    .stream()
                    .map(this::convertFromProto)
                    .toList();
            producer.send(list);
            // TODO may put the new stored messages list into the response
            ProduceResponse resp = ProduceResponse.newBuilder().setSuccess(true).build();
            responseObserver.onNext(resp);
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("Failed to produce messages", e);
            responseObserver.onError(e);
        }
    }

    private ClusterKind getClusterKind(String producer) {
        // TODO only for test, need to find cluster kind by producer -> cluster -> clusterKind
        return ClusterKind.KAFKA;
    }

    private Message convertFromProto(MessageProto origin) {
        Message msg = new Message();
        msg.setQueue(origin.getQueue());
        msg.setData(origin.toByteArray());
        return msg;
    }
}