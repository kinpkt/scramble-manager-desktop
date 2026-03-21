package com.thailandcube.models;

import com.thailandcube.repositories.ActivityList;

import java.time.LocalDateTime;

public class Activity {
    private int id;
    private String name;
    private String activityCode;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private ActivityList childActivities;

    public Activity(int id, String name, String activityCode, LocalDateTime startTime, LocalDateTime endTime, ActivityList childActivities) {
        this.id = id;
        this.name = name;
        this.activityCode = activityCode;
        this.startTime = startTime;
        this.endTime = endTime;
        this.childActivities = childActivities;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getActivityCode() {
        return activityCode;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public ActivityList getChildActivities() {
        return childActivities;
    }
}
