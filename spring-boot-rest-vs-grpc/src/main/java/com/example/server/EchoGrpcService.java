package com.example.server;

import org.lognet.springboot.grpc.GRpcService;

import com.example.server.EchoGrpc.EchoImplBase;
import com.example.server.EchoOuterClass.EchoReply;
import com.example.server.EchoOuterClass.EchoRequest;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

@GRpcService
@Slf4j
public class EchoGrpcService extends EchoImplBase {

    @Override
    public void echo(EchoRequest request, StreamObserver<EchoReply> responseObserver) {

        log.info("gRPC: " + request.getMessage());

        EchoReply reply = EchoReply.newBuilder()
                .setMessage(request.getMessage())
                .build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}