package com.example;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @see https://docs.spring.io/spring/docs/current/spring-framework-reference/html/scheduling.html#scheduling-annotation-support-scheduled
 *
 */
@Component
@Slf4j
public class Scheduler {

    @Scheduled(fixedDelay = 5000)
    public void doFixedDelay() throws InterruptedException {

        log.info(String.format("fixedDelay: %s", LocalDateTime.now()));

        Thread.sleep(1000);
    }

    @Scheduled(fixedDelayString = "${scheduler.fixed-delay}")
    public void doFixedDelayString() throws InterruptedException {

        log.info(String.format("fixedDelayString: %s", LocalDateTime.now()));

        Thread.sleep(1000);
    }

    @Scheduled(fixedRate = 5000)
    public void doFixedRate() throws InterruptedException {

        log.info(String.format("fixedRate: %s", LocalDateTime.now()));

        Thread.sleep(1000);
    }

    @Scheduled(fixedRateString = "${scheduler.fixed-rate}")
    public void doFixedRateString() throws InterruptedException {

        log.info(String.format("fixedRateString: %s", LocalDateTime.now()));

        Thread.sleep(1000);
    }

    @Scheduled(initialDelay = 2000, fixedRate = 5000)
    public void doInitialDelay() throws InterruptedException {

        log.info(String.format("initialDelay: %s", LocalDateTime.now()));

        Thread.sleep(1000);
    }

    @Scheduled(cron = "*/5 * * * * *")
    public void doCron() throws InterruptedException {

        log.info(String.format("cron: %s", LocalDateTime.now()));

        Thread.sleep(1000);
    }

}
