package io.github.benzwreck.wykop4j.conversations;

import java.time.LocalDateTime;

public class Message {
    private final Integer id;
    private final LocalDateTime date;
    private final String body;
    private final String status;
    private final Direction direction;

    public Message(Integer id, LocalDateTime date, String body, String status, Direction direction) {
        this.id = id;
        this.date = date;
        this.body = body;
        this.status = status;
        this.direction = direction;
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

    public String status() {
        return status;
    }

    public Direction direction() {
        return direction;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", date=" + date +
                ", body='" + body + '\'' +
                ", status='" + status + '\'' +
                ", direction=" + direction +
                '}';
    }

    public enum Direction {
        RECEIVED, SENDED
    }
}

