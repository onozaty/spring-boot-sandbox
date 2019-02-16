package com.example.server;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.example.server.EchoRestController.EchoRequest;
import com.example.server.EchoRestController.EchoResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EchoRestControllerTest {

    @LocalServerPort
    private int runningPort;
    
    @Test
    public void echo() {

        String message = "Taro";

        RestTemplate restTemplate = new RestTemplate();
        EchoResponse response = restTemplate.postForObject(
                "http://localhost:" + runningPort + "/api/echo", 
                new EchoRequest(message), 
                EchoResponse.class);

        assertThat(response.getMessage()).isEqualTo(message);
    }
}
