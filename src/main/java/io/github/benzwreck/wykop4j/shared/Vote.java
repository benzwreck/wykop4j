package io.github.benzwreck.wykop4j.shared;

import io.github.benzwreck.wykop4j.profiles.SimpleProfile;

import java.time.LocalDateTime;

/**
 * This class represents a single vote data like an author or date of voting.
 */
public class Vote {
    private final SimpleProfile author;
    private final LocalDateTime date;

    public Vote(SimpleProfile profile, LocalDateTime date) {
        this.author = profile;
        this.date = date;
    }

    /**
     * Gets vote's author {@link SimpleProfile}.
     */
    public SimpleProfile author() {
        return author;
    }

    /**
     * Gets vote's date and time.
     */
    public LocalDateTime date() {
        return date;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "author=" + author +
                ", date=" + date +
                '}';
    }
}
