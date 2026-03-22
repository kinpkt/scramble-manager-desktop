package com.thailandcube.models;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class Activity {
    private int id;
    private String name;
    private String activityCode;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<Activity> childActivities;

    public Activity(int id, String name, String activityCode, LocalDateTime startTime, LocalDateTime endTime, List<Activity> childActivities) {
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

    public List<Activity> getChildActivities() {
        return Collections.unmodifiableList(childActivities);
    }
}
