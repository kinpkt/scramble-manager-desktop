package com.thailandcube.models;

import com.thailandcube.repositories.EventList;
import com.thailandcube.repositories.PersonList;

public class PublicWcif {
    private String formatVersion;
    private String id;
    private String name;
    private String shortName;
    private PersonList persons;
    private EventList events;
    private Schedule schedule;
    private RegistrationInfo registrationInfo;

    public PublicWcif(String formatVersion, String id, String name, String shortName, PersonList persons, EventList events, Schedule schedule, RegistrationInfo registrationInfo) {
        this.formatVersion = formatVersion;
        this.id = id;
        this.name = name;
        this.shortName = shortName;
        this.persons = persons;
        this.events = events;
        this.schedule = schedule;
        this.registrationInfo = registrationInfo;
    }

    public String getFormatVersion() {
        return formatVersion;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    public PersonList getPersons() {
        return persons;
    }

    public EventList getEvents() {
        return events;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public RegistrationInfo getRegistrationInfo() {
        return registrationInfo;
    }
}
