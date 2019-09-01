package com.iora.websiteblocker.model;

public class WebBlocked {
    private int Id;
    private String UrlLink;
    private boolean IsBlocked;
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

    public boolean getIsBlocked() {
        return IsBlocked;
    }

    public void setIsBlocked(boolean blocked) {
        IsBlocked = blocked;
    }

    public long getCreationTime() {
        return CreationTime;
    }

    public void setCreationTime(long creationTime) {
        CreationTime = creationTime;
    }
}
