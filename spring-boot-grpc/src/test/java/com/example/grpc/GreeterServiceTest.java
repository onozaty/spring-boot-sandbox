package com.example.grpc;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lognet.springboot.grpc.context.LocalRunningGrpcPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.grpc.GreeterOuterClass.HelloReply;
import com.example.grpc.GreeterOuterClass.HelloRequest;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GreeterServiceTest {

    @LocalRunningGrpcPort
    private int runningPort;

    @Test
    public void sayHello() {

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", runningPort)
                .usePlaintext()
                .build();

        GreeterGrpc.GreeterBlockingStub stub = GreeterGrpc.newBlockingStub(channel);

        String name = "Taro";

        HelloRequest request = HelloRequest.newBuilder()
                .setName(name)
                .build();

        HelloReply reply = stub.sayHello(request);

        assertThat(reply.getMessage()).isEqualTo("Hello " + name);
    }

    @Test
    public void sayHello_並列実行() {

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", runningPort)
                .usePlaintext()
                .build();

        GreeterGrpc.GreeterBlockingStub stub = GreeterGrpc.newBlockingStub(channel);

        IntStream.range(0, 10).parallel().forEach(i -> {

            String name = "Taro" + i;

            HelloRequest request = HelloRequest.newBuilder()
                    .setName(name)
                    .build();

            HelloReply reply = stub.sayHello(request);

            assertThat(reply.getMessage()).isEqualTo("Hello " + name);

        });
    }
}
