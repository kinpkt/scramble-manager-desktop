package com.thailandcube.repositories;

import com.thailandcube.models.Round;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RoundList implements Repository<Round> {
    private List<Round> rounds;

    public RoundList() {
        rounds = new ArrayList<>();
    }

    @Override
    public void add(Round item) {
        rounds.add(item);
    }

    @Override
    public void remove(Round item) {
        rounds.remove(item);
    }

    @Override
    public List<Round> getAll() {
        return Collections.unmodifiableList(rounds);
    }
}
