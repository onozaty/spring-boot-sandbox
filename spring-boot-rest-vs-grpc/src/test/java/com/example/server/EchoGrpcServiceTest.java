package com.example.server;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lognet.springboot.grpc.context.LocalRunningGrpcPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.server.EchoGrpc.EchoBlockingStub;
import com.example.server.EchoOuterClass.EchoReply;
import com.example.server.EchoOuterClass.EchoRequest;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EchoGrpcServiceTest {

    @LocalRunningGrpcPort
    private int runningPort;

    @Test
    public void echo() {

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", runningPort)
                .usePlaintext()
                .build();

        EchoBlockingStub stub = EchoGrpc.newBlockingStub(channel);

        String message = "Taro";

        EchoRequest request = EchoRequest.newBuilder()
                .setMessage(message)
                .build();

        EchoReply reply = stub.echo(request);

        assertThat(reply.getMessage()).isEqualTo(message);
    }
}
