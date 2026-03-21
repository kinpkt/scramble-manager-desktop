package com.thailandcube.models;

import java.util.Collections;
import java.util.List;

public class Room {
    private int id;
    private String name;
    private String color;
    private List<Activity> activities;

    public Room(int id, String name, String color, List<Activity> activities) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.activities = activities;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public List<Activity> getActivities() {
        return Collections.unmodifiableList(activities);
    }
}
