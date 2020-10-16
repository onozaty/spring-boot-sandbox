package com.github.onozaty.springboot.upload;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.PathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UploadControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void test() throws URISyntaxException {

        Path testFilePath = Paths.get(this.getClass().getResource("1000.txt").toURI());

        IntStream.rangeClosed(1, 10000)
                .parallel()
                .forEach(x -> {

                    PathResource resource = new PathResource(testFilePath);
                    HttpEntity<PathResource> request = new HttpEntity<>(resource);

                    ResponseEntity<Void> response = testRestTemplate.postForEntity("/api/upload", request, Void.class);
                    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
                });
    }

}
