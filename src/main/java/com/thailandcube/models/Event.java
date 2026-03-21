package com.thailandcube.models;

import com.thailandcube.repositories.RoundList;

public class Event {
    private EventId id;
    private RoundList rounds;

    public Event(EventId id, RoundList rounds) {
        this.id = id;
        this.rounds = rounds;
    }

    public EventId getId() {
        return id;
    }

    public RoundList getRounds() {
        return rounds;
    }
}
