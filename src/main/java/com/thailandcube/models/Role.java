package com.thailandcube.models;

public enum Role {
    DELEGATE("delegate"),
    ORGANIZER("organizer"),
    STAFF_OTHER("staff-other"),
    STAFF_DATAENTRY("staff-dataentry");

    private final String id;

    Role(String id) {
        this.id = id;
    }
}
