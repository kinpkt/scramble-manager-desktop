package com.thailandcube.models;

import com.thailandcube.repositories.RoomList;

public class Venue {
    private int id;
    private String name;
    private int latitudeMicrodegrees;
    private int longitudeMicrodegrees;
    private String countryIso2;
    private String timezone;
    private RoomList rooms;

    public Venue(int id, String name, int latitudeMicrodegrees, int longitudeMicrodegrees, String countryIso2, String timezone, RoomList rooms) {
        this.id = id;
        this.name = name;
        this.latitudeMicrodegrees = latitudeMicrodegrees;
        this.longitudeMicrodegrees = longitudeMicrodegrees;
        this.countryIso2 = countryIso2;
        this.timezone = timezone;
        this.rooms = rooms;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getLatitudeMicrodegrees() {
        return latitudeMicrodegrees;
    }

    public int getLongitudeMicrodegrees() {
        return longitudeMicrodegrees;
    }

    public String getCountryIso2() {
        return countryIso2;
    }

    public String getTimezone() {
        return timezone;
    }

    public RoomList getRooms() {
        return rooms;
    }
}
