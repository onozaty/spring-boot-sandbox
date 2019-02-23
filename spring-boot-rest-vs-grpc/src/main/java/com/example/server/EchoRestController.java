package com.example.server;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/echo")
@Slf4j
public class EchoRestController {

    @PostMapping
    public EchoResponse echo(@RequestBody EchoRequest request) {

        log.info("REST: " + request.getMessage());

        return new EchoResponse(request.getMessage());
    }

    @Value
    public static class EchoRequest {
        private final String message;
    }

    @Value
    public static class EchoResponse {
        private final String message;
    }
}
