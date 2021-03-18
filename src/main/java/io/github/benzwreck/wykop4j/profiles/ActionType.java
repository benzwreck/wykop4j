package io.github.benzwreck.wykop4j.profiles;

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
