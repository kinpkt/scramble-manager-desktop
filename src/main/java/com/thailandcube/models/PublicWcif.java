package com.thailandcube.models;

import java.util.Collections;
import java.util.List;

public class PublicWcif {
    private String formatVersion;
    private String id;
    private String name;
    private String shortName;
    private List<Person> persons;
    private List<Event> events;
    private Schedule schedule;
    private RegistrationInfo registrationInfo;

    public PublicWcif(String formatVersion, String id, String name, String shortName, List<Person> persons, List<Event> events, Schedule schedule, RegistrationInfo registrationInfo) {
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

    public List<Person> getPersons() {
        return Collections.unmodifiableList(persons);
    }

    public List<Event> getEvents() {
        return Collections.unmodifiableList(events);
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public RegistrationInfo getRegistrationInfo() {
        return registrationInfo;
    }
}
