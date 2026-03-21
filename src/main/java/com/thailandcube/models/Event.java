package com.thailandcube.models;


import java.util.Collections;
import java.util.List;

public class Event {
    private EventId id;
    private List<Round> rounds;

    public Event(EventId id, List<Round> rounds) {
        this.id = id;
        this.rounds = rounds;
    }

    public EventId getId() {
        return id;
    }

    public List<Round> getRounds() {
        return Collections.unmodifiableList(rounds);
    }
}
