package com.iora.websiteblocker.model;

public class WebHistory {
    private int Id;
    private String UrlLink;
    private long CreationTime;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getUrlLink() {
        return UrlLink;
    }

    public void setUrlLink(String urlLink) {
        UrlLink = urlLink;
    }

    public long getCreationTime() {
        return CreationTime;
    }

    public void setCreationTime(long creationTime) {
        CreationTime = creationTime;
    }
}
