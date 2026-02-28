package org.example.clubmanager.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import org.example.clubmanager.model.Post;

@Service
public class DiscordService {

    private final RestTemplate restTemplate = new RestTemplate();

    public void shareToDiscord(String webhookUrl, String message) {
        // Simple implementation using a Discord Webhook
        
        Map<String, String> payload = new HashMap<>();
        payload.put("content", message);

        try {
            restTemplate.postForEntity(webhookUrl, payload, String.class);
        } catch (Exception e) {
            System.err.println("Failed to send to Discord: " + e.getMessage());
        }

    }

    public void sharePostToDiscord(Post post) {

    }
}
