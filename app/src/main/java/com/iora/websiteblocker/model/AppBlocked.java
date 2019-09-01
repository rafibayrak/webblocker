package com.iora.websiteblocker.model;

public class AppBlocked {
    private int Id;
    private String Name;
    private String PackageName;
    private boolean IsBlocked;
    private long CreationTime;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getPackageName() {
        return PackageName;
    }

    public void setPackageName(String packageName) {
        PackageName = packageName;
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
