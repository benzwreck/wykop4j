package io.github.benzwreck.wykop4j.notifications;

import io.github.benzwreck.wykop4j.profiles.SimpleProfile;

import java.time.LocalDateTime;

public class Notification {
    private final Long id;
    private final SimpleProfile author;
    private final LocalDateTime date;
    private final String body;
    private final String type;
    private final String itemId;
    private final String subitemId;
    private final String url;
    private final Boolean isNew;

    public Notification(Long id, SimpleProfile author, LocalDateTime date, String body, String type, String itemId, String subitemId, String url, Boolean isNew) {
        this.id = id;
        this.author = author;
        this.date = date;
        this.body = body;
        this.type = type;
        this.itemId = itemId;
        this.subitemId = subitemId;
        this.url = url;
        this.isNew = isNew;
    }

    public Long id() {
        return id;
    }

    public SimpleProfile author() {
        return author;
    }

    public LocalDateTime date() {
        return date;
    }

    public String body() {
        return body;
    }

    public String type() {
        return type;
    }

    public String itemId() {
        return itemId;
    }

    public String subitemId() {
        return subitemId;
    }

    public String url() {
        return url;
    }

    public Boolean isNew() {
        return isNew;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", author=" + author +
                ", date=" + date +
                ", body='" + body + '\'' +
                ", type='" + type + '\'' +
                ", itemId='" + itemId + '\'' +
                ", subitemId='" + subitemId + '\'' +
                ", url='" + url + '\'' +
                ", isNew=" + isNew +
                '}';
    }
}
