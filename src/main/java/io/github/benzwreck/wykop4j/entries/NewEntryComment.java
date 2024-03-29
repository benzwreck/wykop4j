package io.github.benzwreck.wykop4j.entries;

import java.io.File;
import java.net.URL;
import java.util.Optional;

/**
 * This class contains a recipe for a new entry comment.<br>
 * To create a new class use {@link NewEntryComment.Builder}.
 */
public class NewEntryComment {
    private final String body;
    private final URL urlEmbed;
    private final File fileEmbed;
    private final String shownFileName;

    private NewEntryComment(String body, URL urlEmbed, File fileEmbed, String shownFileName) {
        this.body = body;
        this.urlEmbed = urlEmbed;
        this.fileEmbed = fileEmbed;
        this.shownFileName = shownFileName;
    }

    /**
     * Gets possible new entry comment's body.
     */
    public Optional<String> body() {
        return Optional.ofNullable(body);
    }

    /**
     * Gets possible new entry comment's embed's url.
     */
    public Optional<URL> urlEmbed() {
        return Optional.ofNullable(urlEmbed);
    }

    /**
     * Gets possible new entry comment's embed's file.
     */
    public Optional<File> fileEmbed() {
        return Optional.ofNullable(fileEmbed);
    }

    /**
     * Gets possible new entry comment's shown file name.
     */
    public Optional<String> shownFileName() {
        return Optional.ofNullable(shownFileName);
    }

    public static class Builder {
        private String body;
        private URL urlEmbed;
        private File fileEmbed;
        private String shownFileName;

        public Builder() {
        }

        public Builder(NewEntryComment newEntryComment) {
            this.body = newEntryComment.body;
            this.urlEmbed = newEntryComment.urlEmbed;
            this.fileEmbed = newEntryComment.fileEmbed;
            this.shownFileName = newEntryComment.shownFileName;
        }

        public Builder withBody(String body) {
            this.body = body;
            return this;
        }

        public Builder withMedia(URL urlEmbed) {
            this.urlEmbed = urlEmbed;
            return this;
        }

        public Builder withMedia(File fileEmbed) {
            this.fileEmbed = fileEmbed;
            this.shownFileName = fileEmbed.getName();
            return this;
        }

        public Builder withMedia(File fileEmbed, String shownFileName) {
            this.fileEmbed = fileEmbed;
            this.shownFileName = shownFileName;
            return this;
        }

        public NewEntryComment build() {
            if ((body == null && fileEmbed == null && urlEmbed == null)) {
                throw new IllegalArgumentException("At least one of Body and Media must be provided.");
            }
            return new NewEntryComment(body, urlEmbed, fileEmbed, shownFileName);
        }
    }
}
