package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class DemoService {

    private static final Logger logger = LoggerFactory.getLogger(DemoService.class);

    private final SimpMessagingTemplate simpMessagingTemplate;

    public DemoService(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Scheduled(cron = "*/5 * * * * *")
    public void performTask() {
        Instant now = Instant.now();
        logger.info("Scheduled task performed at {} (ISO 8601 date and time format)", now);
        this.simpMessagingTemplate.convertAndSend("/queue/now", now);
    }
}
