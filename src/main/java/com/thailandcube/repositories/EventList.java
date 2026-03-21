package com.thailandcube.repositories;

import com.thailandcube.models.Event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventList implements Repository<Event> {
    private List<Event> events;

    public EventList() {
        events = new ArrayList<>();
    }

    @Override
    public void add(Event item) {
        events.add(item);
    }

    @Override
    public void remove(Event item) {
        events.remove(item);
    }

    @Override
    public List<Event> getAll() {
        return Collections.unmodifiableList(events);
    }
}
