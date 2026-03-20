package com.thailandcube.models;

import java.util.ArrayList;

public class PersonList {
    private ArrayList<Person> persons;

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public void addPerson(Person person) {
        persons.add(person);
    }
}
