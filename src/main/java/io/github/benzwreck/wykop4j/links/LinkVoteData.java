package io.github.benzwreck.wykop4j.links;

public class LinkVoteData {
    private final int digs;
    private final int buries;

    public LinkVoteData(int digs, int buries) {
        this.digs = digs;
        this.buries = buries;
    }

    public int digs() {
        return digs;
    }

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
