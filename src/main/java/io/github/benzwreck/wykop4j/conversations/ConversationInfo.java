package io.github.benzwreck.wykop4j.conversations;

import io.github.benzwreck.wykop4j.profiles.SimpleProfile;

import java.time.LocalDateTime;

public class ConversationInfo {
    private final LocalDateTime lastUpdate;
    private final SimpleProfile receiver;
    private final String status;

    public ConversationInfo(LocalDateTime lastUpdate, SimpleProfile receiver, String status) {
        this.lastUpdate = lastUpdate;
        this.receiver = receiver;
        this.status = status;
    }

    public LocalDateTime lastUpdate() {
        return lastUpdate;
    }

    public SimpleProfile receiver() {
        return receiver;
    }

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
