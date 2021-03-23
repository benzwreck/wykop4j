package io.github.benzwreck.wykop4j.search;

public enum DateRange {
    ALL("all"), TODAY("today"), YESTERDAY("yesterday"), WEEK("week"), MONTH("month");
    private final String value;

    DateRange(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
