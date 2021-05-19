package io.github.benzwreck.wykop4j.links;

/**
 * This class contains available Hit options.
 */
public enum HitsOption {
    POPULAR("Popular"),
    DAY("Day"),
    WEEK("Week"),
    MONTH("Month"),
    YEAR("Year");

    private final String value;

    HitsOption(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
