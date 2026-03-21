package com.thailandcube.models;

import com.thailandcube.repositories.ActivityList;

public class Room {
    private int id;
    private String name;
    private String color;
    private ActivityList activities;

    public Room(int id, String name, String color, ActivityList activities) {
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

    public ActivityList getActivities() {
        return activities;
    }
}
