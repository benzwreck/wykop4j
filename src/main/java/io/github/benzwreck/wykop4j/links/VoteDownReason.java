package io.github.benzwreck.wykop4j.links;

/**
 * This class contains available vote down reasons.
 */
public enum VoteDownReason {
    DUPLICATE(1), SPAM(2), FAKE_INFO(3), INAPPROPRIATE_CONTENT(4), NOT_QUALIFY(5);

    private final int value;

    VoteDownReason(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}
