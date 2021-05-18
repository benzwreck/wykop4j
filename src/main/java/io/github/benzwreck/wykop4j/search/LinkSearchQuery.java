package io.github.benzwreck.wykop4j.search;

import java.util.Optional;

/**
 * This class represents a single link search query.
 */
public class LinkSearchQuery {
    private final String phrase;
    private final Type type;
    private final Sorting sorting;
    private final DateRange dateRange;
    private final int minimumVoteCount;

    private LinkSearchQuery(String phrase, Type type, Sorting sorting, DateRange dateRange, int minimumVoteCount) {
        this.phrase = phrase;
        this.type = type;
        this.sorting = sorting;
        this.dateRange = dateRange;
        this.minimumVoteCount = minimumVoteCount;
    }

    /**
     * Gets possible query's searching phrase.
     */
    public Optional<String> phrase() {
        return Optional.ofNullable(phrase);
    }

    /**
     * Gets query's searching type.
     */
    public Type type() {
        return type;
    }

    /**
     * Gets query's searching sorting option.
     */
    public Sorting sorting() {
        return sorting;
    }

    /**
     * Gets query's searching date range.
     */
    public DateRange dateRange() {
        return dateRange;
    }

    /**
     * Gets query's searching minimum vote conut.
     */
    public int minimumVoteCount() {
        return minimumVoteCount;
    }

    /**
     * This class contains link searching query types.
     */
    public enum Type {
        ALL("all"), PROMOTED("promoted"), ARCHIVED("archived"), DUPLICATES("duplicates");
        private final String value;

        Type(String value) {
            this.value = value;
        }

        public String value() {
            return value;
        }
    }

    /**
     * This class contains link searching query sorting options.
     */
    public enum Sorting {
        BEST("best"), DIGGS("diggs"), NEW("new");
        private final String value;

        Sorting(String value) {
            this.value = value;
        }

        public String value() {
            return value;
        }
    }

    public static final class Builder {
        private String phrase;
        private Type type = Type.ALL;
        private Sorting sorting = Sorting.BEST;
        private DateRange dateRange = DateRange.ALL;
        private int minimumVoteCount = 0;

        public Builder() {
        }

        public Builder phrase(String phrase) {
            this.phrase = phrase;
            return this;
        }

        public Builder type(Type type) {
            this.type = type;
            return this;
        }

        public Builder sorting(Sorting sorting) {
            this.sorting = sorting;
            return this;
        }

        public Builder dateRange(DateRange dateRange) {
            this.dateRange = dateRange;
            return this;
        }

        public Builder minimumVoteCount(int minimumVoteCount) {
            this.minimumVoteCount = minimumVoteCount;
            return this;
        }

        public LinkSearchQuery build() {
            if (phrase != null && phrase.length() < 3)
                throw new IllegalArgumentException("Phrase must be at least 3 characters long.");
            return new LinkSearchQuery(phrase, type, sorting, dateRange, minimumVoteCount);
        }
    }
}
