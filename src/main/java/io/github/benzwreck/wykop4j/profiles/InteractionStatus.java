package io.github.benzwreck.wykop4j.profiles;

public class InteractionStatus {
    private final boolean isObserved;
    private final boolean isBlocked;

    public InteractionStatus(boolean isObserved, boolean isBlocked) {
        this.isObserved = isObserved;
        this.isBlocked = isBlocked;
    }

    public boolean isObserved() {
        return isObserved;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    @Override
    public String toString() {
        return "InteractionStatus{" +
                "isObserved=" + isObserved +
                ", isBlocked=" + isBlocked +
                '}';
    }
}
