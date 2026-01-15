package com.example.JWT.service;

import com.example.JWT.dto.response.randomStatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RandomStatusApi {
    private static final String API = "http://localhost:8080/api/random";

    @Autowired private RestTemplate restTemplate;

    public String getRandomStatus() {
        ResponseEntity<randomStatusResponse> response = restTemplate.exchange(API, HttpMethod.GET, null, randomStatusResponse.class);
        randomStatusResponse body = response.getBody();
        log.info("Random status: {}", body.getStatus());
        return body.getStatus();

    }
}
