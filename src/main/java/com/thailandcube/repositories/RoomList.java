package com.thailandcube.repositories;

import com.thailandcube.models.Room;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RoomList implements Repository<Room> {
    private List<Room> rooms;

    public RoomList() {
        rooms = new ArrayList<>();
    }

    @Override
    public void add(Room item) {
        rooms.add(item);
    }

    @Override
    public void remove(Room item) {
        rooms.remove(item);
    }

    @Override
    public List<Room> getAll() {
        return Collections.unmodifiableList(rooms);
    }
}
