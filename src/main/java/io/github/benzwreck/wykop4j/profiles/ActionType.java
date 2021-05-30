package io.github.benzwreck.wykop4j.profiles;

/**
 * This class contains available types of actions.
 */
public enum ActionType {
    LINKS("links"),
    ENTRIES("entries");

    private final String value;

    ActionType(String value) {
        this.value = value;
    }
    public String value(){
        return value;
    }
}
