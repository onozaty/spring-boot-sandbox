package com.example;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InformationRepositoryTest {

    @Autowired
    private InformationRepository repository;

    @Test
    public void test() {

        JsonData jsonData = new JsonData(1, Arrays.asList("x"));
        Information information = new Information(null, "test", Arrays.asList(1, 2), Arrays.asList("a", "b"), jsonData);

        repository.insert(information);
        assertThat(information.getId()).isNotNull();

        information.setText("after");

        repository.update(information);
        
        Information result = repository.find(information.getId());
        assertThat(result).isEqualTo(information);
    }
}
