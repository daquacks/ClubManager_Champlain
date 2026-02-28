package org.example.clubmanager.model;

import java.util.List;
import java.util.ArrayList;

public class Club {
    private String id;
    private String name;
    private String overview;
    private String description;
    private String adminUid; // The user ID of the club admin
    private List<String> memberUids; // List of user IDs who are members
    private String logoUrl;

    public Club() {
        this.memberUids = new ArrayList<>();
    }

    public Club(String id, String name, String description, String adminUid) {
        this.id = id;
        this.name = name;
        this.overview = description;
        this.description = description;
        this.adminUid = adminUid;
        this.memberUids = new ArrayList<>();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getOverview() { return overview; }
    public void setOverview(String overview) { this.overview = overview; };

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getAdminUid() { return adminUid; }
    public void setAdminUid(String adminUid) { this.adminUid = adminUid; }

    public List<String> getMemberUids() { return memberUids; }
    public void setMemberUids(List<String> memberUids) { this.memberUids = memberUids; }

    public String getLogoUrl() { return logoUrl; }
    public void setLogoUrl(String logoUrl) { this.logoUrl = logoUrl; }
}
