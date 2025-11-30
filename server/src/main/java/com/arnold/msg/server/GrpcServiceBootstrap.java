package com.arnold.msg.server;

import com.arnold.msg.service.meta.MetaServiceGrpcImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class GrpcServiceBootstrap {

    private final Server server;
    private final int port;

    public GrpcServiceBootstrap() {
        MetaServiceGrpcImpl metaService = new MetaServiceGrpcImpl();
        this.port = 9000;
        this.server = ServerBuilder.forPort(this.port)
                .addService(metaService)
                .build();
    }

    public void start() throws IOException, InterruptedException {
        server.start();
        log.info("gRPC Server started, listening on port {}", port);
        server.awaitTermination();
    }

}
