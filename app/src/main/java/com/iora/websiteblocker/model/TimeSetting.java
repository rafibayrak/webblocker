package com.iora.websiteblocker.model;

public class TimeSetting {
    private int Id;
    private long StartTime;
    private long EndTime;
    private long CreationTime;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public long getStartTime() {
        return StartTime;
    }

    public void setStartTime(long startTime) {
        StartTime = startTime;
    }

    public long getEndTime() {
        return EndTime;
    }

    public void setEndTime(long endTime) {
        EndTime = endTime;
    }

    public long getCreationTime() {
        return CreationTime;
    }

    public void setCreationTime(long creationTime) {
        CreationTime = creationTime;
    }
}
