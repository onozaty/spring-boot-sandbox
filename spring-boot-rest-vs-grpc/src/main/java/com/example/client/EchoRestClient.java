package com.example.client;

import java.util.stream.IntStream;

import org.springframework.web.client.RestTemplate;

import com.example.server.EchoRestController.EchoRequest;
import com.example.server.EchoRestController.EchoResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EchoRestClient {

    private final RestTemplate restTemplate = new RestTemplate();

    private final String url;

    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();

        String message = "1234567890";
        EchoRestClient client = new EchoRestClient("http://localhost:8080/api/echo");

        IntStream.range(0, 100000)
                .forEach(x -> client.echo(message));

        long totalTime = System.currentTimeMillis() - startTime;

        System.out.println(
                String.format(
                        "Finish. Total time: %,.3f seconds",
                        totalTime / 1000d));
    }

    public String echo(String message) {

        EchoResponse response = restTemplate.postForObject(
                url,
                new EchoRequest(message),
                EchoResponse.class);

        return response.getMessage();
    }
}
