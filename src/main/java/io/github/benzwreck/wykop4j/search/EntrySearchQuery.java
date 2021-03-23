package io.github.benzwreck.wykop4j.search;

public class EntrySearchQuery {
    private final String phrase;
    private final DateRange dateRange;

    private EntrySearchQuery(String phrase, DateRange dateRange) {
        this.phrase = phrase;
        this.dateRange = dateRange;
    }

    public String phrase() {
        return phrase;
    }

    public DateRange dateRange() {
        return dateRange;
    }

    public static final class Builder {
        private String phrase;
        private DateRange dateRange = DateRange.ALL;

        public Builder() {
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
