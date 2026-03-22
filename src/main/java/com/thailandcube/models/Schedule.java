package com.thailandcube.models;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Schedule {
    private Date startDate;
    private int numberOfDays;
    private List<Venue> venues;

    public Schedule(Date startDate, int numberOfDays, List<Venue> venues) {
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

    public List<Venue> getVenues() {
        return Collections.unmodifiableList(venues);
    }
}
