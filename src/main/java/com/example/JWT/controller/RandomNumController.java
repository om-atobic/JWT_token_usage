package com.example.JWT.controller;

import com.example.JWT.service.RandomStatusApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class RandomNumController {
    private final RandomStatusApi randomStatusApi;

    public RandomNumController(RandomStatusApi randomStatusApi) {
        this.randomStatusApi = randomStatusApi;
    }
    @GetMapping("/api/random")
    public ResponseEntity<Map<String, Integer>> getRandomNumber() {
        int value = ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE) % 2;

        Map<String, Integer> response = Map.of(
                "status", value
        );
       // randomStatusApi.getRandomStatus();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/api/random/status")
    public String getRandomStatus() {
        String result = randomStatusApi.getRandomStatus();
        return result;
    }

}
