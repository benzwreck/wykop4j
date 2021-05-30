package io.github.benzwreck.wykop4j.entries;

/**
 * This class defines the possible period of Hot Entries.
 */
public enum Period {
    SIX_HOURS(6),
    TWELVE_HOURS(12),
    TWENTY_FOUR_HOURS(24);

    private final int time;

    Period(int time) {
        this.time = time;
    }

    public int value() {
        return time;
    }

}
