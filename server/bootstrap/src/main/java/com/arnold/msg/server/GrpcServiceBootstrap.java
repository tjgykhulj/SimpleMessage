package com.arnold.msg.server;

import com.arnold.msg.DataServiceGrpcImpl;
import com.arnold.msg.service.meta.MetaServiceGrpcImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class GrpcServiceBootstrap {

    private final Server metaServer;
    private final Server dataServer;
    private final int metaPort;
    private final int dataPort;


    public GrpcServiceBootstrap() {
        // TODO start server by configuration
        MetaServiceGrpcImpl metaService = new MetaServiceGrpcImpl();
        this.metaPort = 9000;
        this.metaServer = ServerBuilder.forPort(this.metaPort)
                .addService(metaService)
                .build();
        this.dataPort = 9001;
        this.dataServer = ServerBuilder.forPort(this.dataPort)
                .addService(new DataServiceGrpcImpl())
                .build();
    }

    public void start() throws IOException, InterruptedException {
        metaServer.start();
        dataServer.start();
        log.info("gRPC Server started, listening on port {}, {}", metaPort, dataPort);
        metaServer.awaitTermination();
        dataServer.awaitTermination();
    }

}
