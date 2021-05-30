package io.github.benzwreck.wykop4j.profiles;

/**
 * This class represents interaction status between current user and given user or tag.
 * Shows if that user or tag is observed or blocked by current user.
 */
public class InteractionStatus {
    private final boolean isObserved;
    private final boolean isBlocked;

    public InteractionStatus(boolean isObserved, boolean isBlocked) {
        this.isObserved = isObserved;
        this.isBlocked = isBlocked;
    }

    /**
     * Returns if given profile or tag is observed by current user.
     */
    public boolean isObserved() {
        return isObserved;
    }

    /**
     * Returns if given profile or tag is blocked by current user.
     */
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
