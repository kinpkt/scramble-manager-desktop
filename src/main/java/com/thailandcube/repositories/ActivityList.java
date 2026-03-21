package com.thailandcube.repositories;

import com.thailandcube.models.Activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ActivityList implements Repository<Activity> {
    private List<Activity> activities;

    public ActivityList() {
        activities = new ArrayList<>();
    }

    @Override
    public void add(Activity item) {
        activities.add(item);
    }

    @Override
    public void remove(Activity item) {
        activities.remove(item);
    }

    @Override
    public List<Activity> getAll() {
        return Collections.unmodifiableList(activities);
    }
}
