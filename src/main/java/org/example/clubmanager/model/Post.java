package org.example.clubmanager.model;

import java.util.Date;

public class Post {
    private String id;
    private String clubId;
    private String title;
    private String content;
    private String imageUrl;
    private Date timestamp;

    public Post() {}

    public Post(String id, String clubId, String title, String content, Date timestamp) {
        this.id = id;
        this.clubId = clubId;
        this.title = title;
        this.content = content;
        this.timestamp = timestamp;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getClubId() { return clubId; }
    public void setClubId(String clubId) { this.clubId = clubId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Date getTimestamp() { return timestamp; }
    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }
}
