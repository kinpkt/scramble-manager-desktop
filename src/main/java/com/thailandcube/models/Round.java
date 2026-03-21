package com.thailandcube.models;

class TimeLimit {
    private int centiseconds;

    public TimeLimit(int centiseconds) {
        this.centiseconds = centiseconds;
    }

    public int getCentiseconds() {
        return centiseconds;
    }
}

class Cutoff {
    private int numberOfAttempts;
    private int attemptResult;

    public Cutoff(int numberOfAttempts, int attemptResult) {
        this.numberOfAttempts = numberOfAttempts;
        this.attemptResult = attemptResult;
    }

    public int getNumberOfAttempts() {
        return numberOfAttempts;
    }

    public int getAttemptResult() {
        return attemptResult;
    }
}

enum AdvancementType {
    PERCENT,
    RANKING
}

class AdvancementCondition {
    private AdvancementType type;
    private int level;

    public AdvancementCondition(AdvancementType type, int level) {
        this.type = type;
        this.level = level;
    }

    public AdvancementType getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }
}

public class Round {
    private String id;
    private String format;
    private TimeLimit timeLimit;
    private Cutoff cutoff;
    private AdvancementCondition advancementCondition;

    public Round(String id, String format, TimeLimit timeLimit, Cutoff cutoff, AdvancementCondition advancementCondition) {
        this.id = id;
        this.format = format;
        this.timeLimit = timeLimit;
        this.cutoff = cutoff;
        this.advancementCondition = advancementCondition;
    }

    public String getId() {
        return id;
    }

    public String getFormat() {
        return format;
    }

    public TimeLimit getTimeLimit() {
        return timeLimit;
    }

    public Cutoff getCutoff() {
        return cutoff;
    }

    public AdvancementCondition getAdvancementCondition() {
        return advancementCondition;
    }
}
