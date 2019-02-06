package com.example.grpc;

import org.lognet.springboot.grpc.GRpcService;

import com.example.grpc.GreeterOuterClass.HelloReply;
import com.example.grpc.GreeterOuterClass.HelloRequest;

import io.grpc.stub.StreamObserver;

@GRpcService
public class GreeterService extends GreeterGrpc.GreeterImplBase {

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {

        HelloReply reply = HelloReply.newBuilder()
                .setMessage("Hello " + request.getName())
                .build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}