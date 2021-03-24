package io.github.benzwreck.wykop4j.shared;

import io.github.benzwreck.wykop4j.profiles.SimpleProfile;

import java.time.LocalDateTime;

public class Vote {
    private final SimpleProfile author;
    private final LocalDateTime date;

    public Vote(SimpleProfile profile, LocalDateTime date) {
        this.author = profile;
        this.date = date;
    }

    public SimpleProfile author() {
        return author;
    }

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
