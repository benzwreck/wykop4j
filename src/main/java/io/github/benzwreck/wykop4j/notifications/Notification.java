package io.github.benzwreck.wykop4j.notifications;

import io.github.benzwreck.wykop4j.profiles.SimpleProfile;

import java.time.LocalDateTime;

/**
 * This class represents a single notification e.g. new private message, replies, warnings.
 */
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

    /**
     * Gets notification's id.
     */
    public Long id() {
        return id;
    }

    /**
     * Gets notification's author {@link SimpleProfile}.
     */
    public SimpleProfile author() {
        return author;
    }

    /**
     * Gets notification's date and time.
     */
    public LocalDateTime date() {
        return date;
    }

    /**
     * Gets notification's body.
     */
    public String body() {
        return body;
    }

    /**
     * Gets notification's type.
     */
    public String type() {
        return type;
    }

    /**
     * Gets the id of item e.g. entry, link, comment.
     */
    public String itemId() {
        return itemId;
    }

    /**
     * Gets the if of subitem e.g. if you replied to a link's comment then subitem will be link's id.
     */
    public String subitemId() {
        return subitemId;
    }

    /**
     * Gets notification's url.
     */
    public String url() {
        return url;
    }

    /**
     * Returns if the notification is new or already read.
     */
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
