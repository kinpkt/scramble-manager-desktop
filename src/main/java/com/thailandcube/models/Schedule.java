package com.thailandcube.models;

import com.thailandcube.repositories.VenueList;

import java.util.Date;

public class Schedule {
    private Date startDate;
    private int numberOfDays;
    private VenueList venues;

    public Schedule(Date startDate, int numberOfDays, VenueList venues) {
        this.startDate = startDate;
        this.numberOfDays = numberOfDays;
        this.venues = venues;
    }

    public Date getStartDate() {
        return startDate;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public VenueList getVenues() {
        return venues;
    }
}
