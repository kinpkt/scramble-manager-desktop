package com.thailandcube.repositories;

import com.thailandcube.models.Venue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VenueList implements Repository<Venue> {
    private List<Venue> venues;

    public VenueList() {
        venues = new ArrayList<>();
    }

    @Override
    public void add(Venue item) {
        venues.add(item);
    }

    @Override
    public void remove(Venue item) {
        venues.remove(item);
    }

    @Override
    public List<Venue> getAll() {
        return Collections.unmodifiableList(venues);
    }
}
