package io.github.benzwreck.wykop4j.links;

/**
 * This class represents related link's vote data.
 */
public class RelatedLinkVoteData {
    private final int voteCount;

    public RelatedLinkVoteData(int voteCount) {
        this.voteCount = voteCount;
    }

    /**
     * Gets related link's vote count.
     */
    public int voteCount() {
        return voteCount;
    }

    @Override
    public String toString() {
        return "RelatedLinkVoteData{" +
                "voteCount=" + voteCount +
                '}';
    }
}
