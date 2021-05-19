package io.github.benzwreck.wykop4j.links;

/**
 * This class contains available link comments sorting options.
 */
public enum LinkCommentsSorting {
    OLD, NEW, BEST;

    public String value() {
        return toString().toLowerCase();
    }
}
