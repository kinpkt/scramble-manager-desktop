package com.thailandcube.models;

import java.util.Collections;
import java.util.List;

enum Status {
    ACCEPTED,
    PENDING,
    CANCELLED,
    DELETED
}

public class Registration {
    private int wcaRegistrationId;
    private List<EventId> eventIds;
    private Status status;
    private boolean isCompeting;

    public Registration(int wcaRegistrationId, List<EventId> eventIds, Status status, boolean isCompeting) {
        this.wcaRegistrationId = wcaRegistrationId;
        this.eventIds = eventIds;
        this.status = status;
        this.isCompeting = isCompeting;
    }

    public int getWcaRegistrationId() {
        return wcaRegistrationId;
    }

    public List<EventId> getEventIds() {
        return Collections.unmodifiableList(eventIds);
    }

    public Status getStatus() {
        return status;
    }

    public boolean isCompeting() {
        return isCompeting;
    }
}
