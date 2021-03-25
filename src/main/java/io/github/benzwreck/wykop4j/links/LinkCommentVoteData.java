package io.github.benzwreck.wykop4j.links;

public class LinkCommentVoteData {
    private final int voteCount;
    private final int voteCountPlus;

    public LinkCommentVoteData(int voteCount, int voteCountPlus) {
        this.voteCount = voteCount;
        this.voteCountPlus = voteCountPlus;
    }

    public int voteCount() {
        return voteCount;
    }

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
