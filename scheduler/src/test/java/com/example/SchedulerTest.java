package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SchedulerTest.TestApplication.class)
public class SchedulerTest {

    @Test
    public void test() throws InterruptedException {
        Thread.sleep(5000);
    }

    @SpringBootApplication
    @ComponentScan(excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, value = SchedulerApplication.class))
    static class TestApplication {

        public static void main(String[] args) {
            SpringApplication.run(TestApplication.class, args);
        }
    }

}
