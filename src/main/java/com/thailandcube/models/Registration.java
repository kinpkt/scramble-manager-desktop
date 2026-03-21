package com.thailandcube.models;

import java.util.ArrayList;

enum Status {
    ACCEPTED,
    PENDING,
    CANCELLED,
    DELETED
}

public class Registration {
    private int wcaRegistrationId;
    private ArrayList<EventId> eventIds;
    private Status status;
    private boolean isCompeting;

    public Registration(int wcaRegistrationId, ArrayList<EventId> eventIds, Status status, boolean isCompeting) {
        this.wcaRegistrationId = wcaRegistrationId;
        this.eventIds = eventIds;
        this.status = status;
        this.isCompeting = isCompeting;
    }

    public int getWcaRegistrationId() {
        return wcaRegistrationId;
    }

    public ArrayList<EventId> getEventIds() {
        return eventIds;
    }

    public Status getStatus() {
        return status;
    }

    public boolean isCompeting() {
        return isCompeting;
    }
}
