package com.iora.websiteblocker.model;

public class AppHistory {
    private int Id;
    private String Name;
    private String PackageName;
    private long CreationTime;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPackageName() {
        return PackageName;
    }

    public void setPackageName(String packageName) {
        PackageName = packageName;
    }

    public long getCreationTime() {
        return CreationTime;
    }

    public void setCreationTime(long creationTime) {
        CreationTime = creationTime;
    }
}
