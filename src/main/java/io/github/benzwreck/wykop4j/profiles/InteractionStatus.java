package io.github.benzwreck.wykop4j.profiles;

/**
 * Interaction status between current user and given user or tag.
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
     * @return true - if given profile or tag is observed by current user, false - otherwise.
     */
    public boolean isObserved() {
        return isObserved;
    }

    /**
     * @return true - if given profile or tag is blocked by current user, false - otherwise.
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
