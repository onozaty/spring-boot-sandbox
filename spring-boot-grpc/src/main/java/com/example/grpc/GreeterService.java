package com.example.grpc;

import org.lognet.springboot.grpc.GRpcService;

import com.example.grpc.GreeterOuterClass.HelloReply;
import com.example.grpc.GreeterOuterClass.HelloRequest;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

@GRpcService
@Slf4j
public class GreeterService extends GreeterGrpc.GreeterImplBase {

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {

        log.info("start: " + request.getName());

        HelloReply reply = HelloReply.newBuilder()
                .setMessage("Hello " + request.getName())
                .build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();

        log.info("end: " + request.getName());
    }
}