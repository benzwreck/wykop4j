package io.github.benzwreck.wykop4j.entries;

import java.io.File;
import java.util.Optional;

/**
 * This class contains a recipe for a new entry.<br>
 * To create a new class use {@link NewEntry.Builder}.
 */
public class NewEntry {
    private final String body;
    private final String urlEmbed;
    private final File fileEmbed;
    private final String shownFileName;
    private final boolean isAdult;

    private NewEntry(String body, String urlEmbed, File fileEmbed, String shownFileName, boolean isAdult) {
        this.body = body;
        this.urlEmbed = urlEmbed;
        this.fileEmbed = fileEmbed;
        this.shownFileName = shownFileName;
        this.isAdult = isAdult;
    }

    /**
     * Gets possible new entry's body.
     */
    public Optional<String> body() {
        return Optional.ofNullable(body);
    }

    /**
     * Gets possible new entry's embed's url.
     */
    public Optional<String> urlEmbed() {
        return Optional.ofNullable(urlEmbed);
    }

    /**
     * Gets possible new entry's embed's file.
     */
    public Optional<File> fileEmbed() {
        return Optional.ofNullable(fileEmbed);
    }

    /**
     * Gets possible new entry's embed's shown file name.
     */
    public Optional<String> shownFileName() {
        return Optional.ofNullable(shownFileName);
    }

    /**
     * Returns if new entry's embed is adult only.
     */
    public boolean adultOnly() {
        return isAdult;
    }

    public static class Builder {
        private String body;
        private String urlEmbed;
        private File fileEmbed;
        private String shownFileName;
        private boolean isAdult = false;

        public Builder() {
        }

        public Builder(NewEntry newEntry) {
            this.body = newEntry.body;
            this.urlEmbed = newEntry.urlEmbed;
            this.fileEmbed = newEntry.fileEmbed;
            this.shownFileName = newEntry.shownFileName;
            this.isAdult = newEntry.isAdult;
        }

        /**
         * @param body entry's content.
         * @return Builder with body.
         * @throws IllegalArgumentException when body's length is less than 5 characters.
         */
        public Builder withBody(String body) {
            if (body.length() < 5) throw new IllegalArgumentException("Entry's body must have at least 5 characters.");
            this.body = body;
            return this;
        }

        public Builder withMedia(String url) {
            this.urlEmbed = url;
            return this;
        }

        public Builder withMedia(File file) {
            this.fileEmbed = file;
            return this;
        }

        public Builder withMedia(File file, String shownFileName) {
            this.fileEmbed = file;
            this.shownFileName = shownFileName;
            return this;
        }

        /**
         * @return Builder with adult only content enabled. Only works when Media is provided. Does nothing otherwise.
         */
        public Builder adultOnly() {
            this.isAdult = true;
            return this;
        }

        public NewEntry build() {
            if ((body == null && fileEmbed == null && urlEmbed == null)) {
                throw new IllegalArgumentException("Between Body or Media, at least one of them should be provided.");
            }
            return new NewEntry(body, urlEmbed, fileEmbed, shownFileName, isAdult);
        }

    }
}
