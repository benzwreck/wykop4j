package io.github.benzwreck.wykop4j.profiles;

public enum Color {
    GREEN("#339933"),
    ORANGE("#FF5917"),
    CLARET("#BB0000"),
    ADMIN("#000000"),
    BANNED("#999999"),
    DELETED("#999999"),
    CLIENT("#3F6FA0"),
    UNDEFINED("undefined");
    private final String hex;

    Color(String hex) {
        this.hex = hex;
    }

    public String hex() {
        return hex;
    }
}
