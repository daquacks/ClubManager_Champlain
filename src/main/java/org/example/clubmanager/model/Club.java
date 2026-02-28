package org.example.clubmanager.model;

import java.util.List;
import java.util.ArrayList;

public class Club {
    private String id;
    private String name;
    private String description;
    private String overview;
    private String adminKey; // The (unencrypted) admin key
    private String logoUrl;
    private String discordLink; // New field for Discord invite link

    public Club() {
    }

    public Club(String id, String name, String description, String adminKey) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.adminKey = adminKey;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getOverview() { return overview; }
    public void setOverview(String overview) { this.overview = overview; }

    public String getAdminKey() {
        return adminKey;
    }
    public void setAdminKey(String key) {
        this.adminKey = adminKey;
    }

    public String getLogoUrl() { return logoUrl; }
    public void setLogoUrl(String logoUrl) { this.logoUrl = logoUrl; }

    public String getDiscordLink() { return discordLink; }
    public void setDiscordLink(String discordLink) { this.discordLink = discordLink; }
}
