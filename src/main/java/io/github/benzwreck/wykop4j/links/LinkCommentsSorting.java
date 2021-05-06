package io.github.benzwreck.wykop4j.links;

public enum LinkCommentsSorting {
    OLD, NEW, BEST;

    public String value() {
        return toString().toLowerCase();
    }
}
