package io.github.benzwreck.wykop4j.profiles;

/**
 * Interaction status between user and given profile.
 * Shows if that profile is observed or blocked by user.
 */
public class InteractionStatus {
    private final boolean isObserved;
    private final boolean isBlocked;

    public InteractionStatus(boolean isObserved, boolean isBlocked) {
        this.isObserved = isObserved;
        this.isBlocked = isBlocked;
    }

    /**
     * @return true - if given profile is observed by user, false - otherwise.
     */
    public boolean isObserved() {
        return isObserved;
    }

    /**
     * @return true - if given profile is blocked by user, false - otherwise.
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
