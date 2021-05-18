package io.github.benzwreck.wykop4j.conversations;

import io.github.benzwreck.wykop4j.media.Embed;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * This class represents a single private message.
 */
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

    /**
     * Gets the id of the message.
     */
    public Integer id() {
        return id;
    }

    /**
     * Gets the date and time of message delivery.
     */
    public LocalDateTime date() {
        return date;
    }

    /**
     * Gets the body of the message.
     */
    public String body() {
        return body;
    }

    /**
     * Gets the possible embed media attached to the message.
     */
    public Optional<Embed> embed() {
        return Optional.ofNullable(embed);
    }

    /**
     * Gets the status of the message.
     */
    public String status() {
        return status;
    }

    /**
     * Gets the direction of the message.
     */
    public Direction direction() {
        return direction;
    }

    /**
     * Gets the possible application's name which was used to send a message, e.g. Wykop Mobilny, Android, iOS.
     */
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
        RECEIVED, SENT
    }
}