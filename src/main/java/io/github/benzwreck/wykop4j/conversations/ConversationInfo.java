package io.github.benzwreck.wykop4j.conversations;

import io.github.benzwreck.wykop4j.profiles.SimpleProfile;

import java.time.LocalDateTime;

/**
 * This class contains basic information about user's conversation with another user such as
 * date of last update, simple profile of the receiver and conversation status.<br>
 * It does not contain messages itself.
 */
public class ConversationInfo {
    private final LocalDateTime lastUpdate;
    private final SimpleProfile receiver;
    private final String status;

    public ConversationInfo(LocalDateTime lastUpdate, SimpleProfile receiver, String status) {
        this.lastUpdate = lastUpdate;
        this.receiver = receiver;
        this.status = status;
    }

    /**
     * Gets the date and time of last conversation update - when somebody wrote to you or you wrote to them.
     */
    public LocalDateTime lastUpdate() {
        return lastUpdate;
    }

    /**
     * Gets the simple profile of user you had a conversation with.
     */
    public SimpleProfile receiver() {
        return receiver;
    }

    /**
     * Gets the conversation status.<br>
     * It returns {@link String}, not an {@link Enum}
     * because Wykop API docs does not specify any of possible statuses.
     */
    public String status() {
        return status;
    }

    @Override
    public String toString() {
        return "ConversationInfo{" +
                "lastUpdate=" + lastUpdate +
                ", receiver=" + receiver +
                ", status='" + status + '\'' +
                '}';
    }
}
