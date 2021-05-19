package io.github.benzwreck.wykop4j.links;

/**
 * This class represents link comment's vote data.
 */
public class LinkCommentVoteData {
    private final int voteCount;
    private final int voteCountPlus;

    public LinkCommentVoteData(int voteCount, int voteCountPlus) {
        this.voteCount = voteCount;
        this.voteCountPlus = voteCountPlus;
    }

    /**
     * Gets link comment's total vote count.
     */
    public int voteCount() {
        return voteCount;
    }

    /**
     * Gets link comment's vote up count.
     */
    public int voteCountPlus() {
        return voteCountPlus;
    }

    @Override
    public String toString() {
        return "LinkCommentVoteData{" +
                "voteCount=" + voteCount +
                ", voteCountPlus=" + voteCountPlus +
                '}';
    }
}
