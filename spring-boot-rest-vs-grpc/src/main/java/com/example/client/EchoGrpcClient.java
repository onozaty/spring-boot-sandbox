package com.example.client;

import java.io.Closeable;
import java.util.stream.IntStream;

import com.example.server.EchoGrpc;
import com.example.server.EchoGrpc.EchoBlockingStub;
import com.example.server.EchoOuterClass.EchoReply;
import com.example.server.EchoOuterClass.EchoRequest;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class EchoGrpcClient implements Closeable {

    private final ManagedChannel channel;
    private final EchoBlockingStub stub;

    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();

        String message = "1234567890";
        try (EchoGrpcClient client = new EchoGrpcClient("localhost:6565")) {

            IntStream.range(0, 100000)
                    .forEach(x -> client.echo(message));
        }

        long totalTime = System.currentTimeMillis() - startTime;

        System.out.println(
                String.format(
                        "Finish. Total time: %,.3f seconds",
                        totalTime / 1000d));
    }

    public EchoGrpcClient(String target) {

        channel = ManagedChannelBuilder.forTarget(target)
                .usePlaintext()
                .build();

        stub = EchoGrpc.newBlockingStub(channel);
    }

    public String echo(String message) {

        EchoRequest request = EchoRequest.newBuilder()
                .setMessage(message)
                .build();

        EchoReply reply = stub.echo(request);

        return reply.getMessage();
    }

    @Override
    public void close() {

        channel.shutdown();
    }
}
