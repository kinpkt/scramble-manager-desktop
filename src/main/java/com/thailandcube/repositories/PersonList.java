package com.thailandcube.repositories;

import com.thailandcube.models.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PersonList implements Repository<Person> {
    private List<Person> persons;

    public PersonList() {
        persons = new ArrayList<>();
    }

    @Override
    public void add(Person item) {
        persons.add(item);
    }

    @Override
    public void remove(Person item) {
        persons.remove(item);
    }

    @Override
    public List<Person> getAll() {
        return Collections.unmodifiableList(persons);
    }
}
