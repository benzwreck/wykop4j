package io.github.benzwreck.wykop4j.search;

/**
 * This class represents a single entry search query.
 */
public class EntrySearchQuery {
    private final String phrase;
    private final DateRange dateRange;

    private EntrySearchQuery(String phrase, DateRange dateRange) {
        this.phrase = phrase;
        this.dateRange = dateRange;
    }

    /**
     * Gets query's searching phrase.
     */
    public String phrase() {
        return phrase;
    }

    /**
     * Gets query's searching data range.
     */
    public DateRange dateRange() {
        return dateRange;
    }

    public static final class Builder {
        private String phrase;
        private DateRange dateRange = DateRange.ALL;

        public Builder() {
        }

        public Builder(EntrySearchQuery entrySearchQuery) {
            this.phrase = entrySearchQuery.phrase;
            this.dateRange = entrySearchQuery.dateRange;
        }

        public Builder phrase(String phrase) {
            this.phrase = phrase;
            return this;
        }

        public Builder dateRange(DateRange dateRange) {
            this.dateRange = dateRange;
            return this;
        }

        public EntrySearchQuery build() {
            if (phrase == null || phrase.length() < 3)
                throw new IllegalArgumentException("Phrase must be at least 3 characters long.");
            return new EntrySearchQuery(phrase, dateRange);
        }
    }
}
