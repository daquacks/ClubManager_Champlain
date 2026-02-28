package org.example.clubmanager.service;

import org.example.clubmanager.model.Post;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class DiscordService {

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String DISCORD_WEBHOOK_URL = "https://discord.com/api/webhooks/1477405400117678082/mFC91DLsW1Xy75JAObXXQ2UPTEqrclTZGXu2fuWqqxhd529beObuibApn1F0MOG6yZKO";

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
        if (DISCORD_WEBHOOK_URL.equals("https://discord.com/api/webhooks/1477405400117678082/mFC91DLsW1Xy75JAObXXQ2UPTEqrclTZGXu2fuWqqxhd529beObuibApn1F0MOG6yZKO")) {
            System.out.println("Discord Webhook URL not configured. Skipping Discord notification.");
            return;
        }

        String message = "**New Post from Club!**\n" +
                "**Title:** " + post.getTitle() + "\n" +
                "**Content:** " + post.getContent() + "\n" +
                (post.getImageUrl() != null ? post.getImageUrl() : "");

        shareToDiscord(DISCORD_WEBHOOK_URL, message);
    }
}
