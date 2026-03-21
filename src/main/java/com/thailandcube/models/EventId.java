package com.thailandcube.models;

public enum EventId {
    _333("333"),
    _222("222"),
    _444("444"),
    _555("555"),
    _666("666"),
    _777("777"),
    _333BF("333bf"),
    _333FM("333fm"),
    _333OH("333oh"),
    CLOCK("clock"),
    MINX("minx"),
    PYRAM("pyram"),
    SKEWB("skewb"),
    SQ1("sq1"),
    _444BF("444bf"),
    _555BF("555bf"),
    _333MBF("333mbf");

    private final String id;

    EventId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public static EventId fromString(String text) {
        for (EventId event : EventId.values()) {
            if (event.id.equalsIgnoreCase(text))
                return event;
        }

        throw new IllegalArgumentException("No EventId found for: " + text);
    }
}
