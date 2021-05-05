package io.github.benzwreck.wykop4j.links;

public class RelatedLinkVoteData {
    private final int voteCount;

    public RelatedLinkVoteData(int voteCount) {
        this.voteCount = voteCount;
    }

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
