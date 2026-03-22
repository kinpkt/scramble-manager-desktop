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

    public String getFullName() {
        return switch (this) {
            case _333 -> "3x3x3 Cube";
            case _222 -> "2x2x2 Cube";
            case _444 -> "4x4x4 Cube";
            case _555 -> "5x5x5 Cube";
            case _666 -> "6x6x6 Cube";
            case _777 -> "7x7x7 Cube";
            case _333BF -> "3x3x3 Blindfolded";
            case _333FM -> "3x3x3 Fewest Moves";
            case _333OH -> "3x3x3 One-Handed";
            case CLOCK -> "Clock";
            case MINX -> "Megaminx";
            case PYRAM -> "Pyraminx";
            case SKEWB -> "Skewb";
            case SQ1 -> "Square-1";
            case _444BF -> "4x4x4 Blindfolded";
            case _555BF -> "5x5x5 Blindfolded";
            case _333MBF -> "3x3x3 Multiple Blindfolded";
        };
    }

    public static EventId fromString(String text) {
        for (EventId event : EventId.values()) {
            if (event.id.equalsIgnoreCase(text))
                return event;
        }

        throw new IllegalArgumentException("No EventId found for: " + text);
    }

    public static EventId fromFullName(String fullName) {
        for (EventId event : EventId.values()) {
            if (fullName.equalsIgnoreCase(event.getFullName()))
                return event;
        }

        return null;
    }
}
