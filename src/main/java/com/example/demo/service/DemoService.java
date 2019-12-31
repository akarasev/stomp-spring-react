package com.example.demo.service;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectListing;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;

@Service
public class DemoService {

    private static final Logger logger = LoggerFactory.getLogger(DemoService.class);

    private final SimpMessagingTemplate simpMessagingTemplate;

    public DemoService(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Scheduled(cron = "0 * * * * *")
    public void performTask() throws IOException {
        Instant now = Instant.now();
        logger.info("Scheduled task performed at {} (ISO 8601 date and time format)", now);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://jsonplaceholder.typicode.com/users/1")
                .build();
        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();
        logger.info("Response: {}", responseBody);

        EnvironmentVariableCredentialsProvider credentialsProvider = new EnvironmentVariableCredentialsProvider();
        AmazonS3 s3client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.US_EAST_1)
                .withCredentials(credentialsProvider)
                .build();

        ObjectListing objectListing = s3client.listObjects("sea-1074-amazon-codeguru-poc");
        logger.info("Test bucket listing: {}", objectListing);

        s3client.putObject("sea-1074-amazon-codeguru-poc", "user.json", responseBody);

        this.simpMessagingTemplate.convertAndSend("/queue/now", now);
    }
}
