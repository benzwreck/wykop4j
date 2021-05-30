package io.github.benzwreck.wykop4j.links;

/**
 * This class represents link's vote data.
 */
public class LinkVoteData {
    private final int digs;
    private final int buries;

    public LinkVoteData(int digs, int buries) {
        this.digs = digs;
        this.buries = buries;
    }

    /**
     * Gets link's vote data digs count.
     */
    public int digs() {
        return digs;
    }

    /**
     * Gets link's vote data buries count.
     */
    public int buries() {
        return buries;
    }

    @Override
    public String toString() {
        return "LinkVoteData{" +
                "digs=" + digs +
                ", buries=" + buries +
                '}';
    }
}
