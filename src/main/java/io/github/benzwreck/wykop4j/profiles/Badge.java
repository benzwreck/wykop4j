package io.github.benzwreck.wykop4j.profiles;

import java.time.LocalDateTime;

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
     * @return badge's name.
     */
    public String name() {
        return name;
    }

    /**
     * @return badge's level.
     */
    public int level() {
        return level;
    }

    /**
     * @return badge's assign date.
     */
    public LocalDateTime date() {
        return date;
    }

    /**
     * @return url to badge's icon.
     */
    public String icon() {
        return icon;
    }

    /**
     * @return badge's description.
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
