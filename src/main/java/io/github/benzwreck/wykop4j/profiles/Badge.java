package io.github.benzwreck.wykop4j.profiles;

import java.time.LocalDateTime;

/**
 * This class represents a badge which can be obtained by user.
 */
public class Badge {
    private final String name;
    private final int level;
    private final LocalDateTime date;
    private final String icon;
    private final String description;

    public Badge(String name, int level, LocalDateTime date, String icon, String description) {
        this.name = name;
        this.level = level;
        this.date = date;
        this.icon = icon;
        this.description = description;
    }

    /**
     * Gets badge's name.
     */
    public String name() {
        return name;
    }

    /**
     * Gets badge's level.
     */
    public int level() {
        return level;
    }

    /**
     * Gets badge's assign date and time.
     */
    public LocalDateTime date() {
        return date;
    }

    /**
     * Gets url to badge's icon.
     */
    public String icon() {
        return icon;
    }

    /**
     * Gets badge's description.
     */
    public String description() {
        return description;
    }

    @Override
    public String toString() {
        return "Badge{" +
                "name='" + name + '\'' +
                ", level=" + level +
                ", date=" + date +
                ", icon='" + icon + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
