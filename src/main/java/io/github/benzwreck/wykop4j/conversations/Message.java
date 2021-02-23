package io.github.benzwreck.wykop4j.conversations;

import io.github.benzwreck.wykop4j.media.Embed;

import java.time.LocalDateTime;
import java.util.Optional;

public class Message {
    private final Integer id;
    private final LocalDateTime date;
    private final String body;
    private final Embed embed;
    private final String status;
    private final Direction direction;
    private final String app;

    public Message(Integer id, LocalDateTime date, String body, Embed embed, String status, Direction direction, String app) {
        this.id = id;
        this.date = date;
        this.body = body;
        this.embed = embed;
        this.status = status;
        this.direction = direction;
        this.app = app;
    }

    public Integer id() {
        return id;
    }

    public LocalDateTime date() {
        return date;
    }

    public String body() {
        return body;
    }

    public Optional<Embed> embed() {
        return Optional.ofNullable(embed);
    }

    public String status() {
        return status;
    }

    public Direction direction() {
        return direction;
    }

    public Optional<String> app() {
        return Optional.ofNullable(app);
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", date=" + date +
                ", body='" + body + '\'' +
                ", embed=" + embed +
                ", status='" + status + '\'' +
                ", direction=" + direction +
                ", app='" + app + '\'' +
                '}';
    }

    public enum Direction {
        RECEIVED, SENDED
    }
}