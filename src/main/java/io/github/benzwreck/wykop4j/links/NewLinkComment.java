package io.github.benzwreck.wykop4j.links;

import java.io.File;
import java.util.Optional;

/**
 * This class contains a recipe for a new link comment.<br>
 * All fields except photoKey must be given to create {@link NewLinkComment}.
 * To create a new class use {@link NewLinkComment.Builder}.
 */
public class NewLinkComment {
    private final String body;
    private final String urlEmbed;
    private final File fileEmbed;
    private final String shownFileName;

    private NewLinkComment(String body, String urlEmbed, File fileEmbed, String shownFileName) {
        this.body = body;
        this.urlEmbed = urlEmbed;
        this.fileEmbed = fileEmbed;
        this.shownFileName = shownFileName;
    }

    /**
     * Gets possible new link's body.
     */
    public Optional<String> body() {
        return Optional.ofNullable(body);
    }

    /**
     * Gets possible new link's urls to embed media.
     */
    public Optional<String> urlEmbed() {
        return Optional.ofNullable(urlEmbed);
    }

    /**
     * Gets possible new link's file of embed media.
     */
    public Optional<File> fileEmbed() {
        return Optional.ofNullable(fileEmbed);
    }

    /**
     * Gets possible new link's shown file name of embed media.
     */
    public Optional<String> shownFileName() {
        return Optional.ofNullable(shownFileName);
    }

    public static class Builder {
        private String body;
        private String urlEmbed;
        private File fileEmbed;
        private String shownFileName;

        public Builder() {
        }

        public Builder(NewLinkComment newLinkComment) {
            this.body = newLinkComment.body;
            this.urlEmbed = newLinkComment.urlEmbed;
            this.fileEmbed = newLinkComment.fileEmbed;
            this.shownFileName = newLinkComment.shownFileName;
        }

        public NewLinkComment.Builder withBody(String body) {
            this.body = body;
            return this;
        }

        public NewLinkComment.Builder withMedia(String urlEmbed) {
            this.urlEmbed = urlEmbed;
            return this;
        }

        public NewLinkComment.Builder withMedia(File fileEmbed) {
            this.fileEmbed = fileEmbed;
            this.shownFileName = fileEmbed.getName();
            return this;
        }

        public NewLinkComment.Builder withMedia(File fileEmbed, String shownFileName) {
            this.fileEmbed = fileEmbed;
            this.shownFileName = shownFileName;
            return this;
        }

        public NewLinkComment build() {
            if ((body == null && fileEmbed == null && urlEmbed == null)) {
                throw new IllegalArgumentException("At least one of Body and Media must be provided.");
            }
            if (body != null && body.length() < 5) {
                throw new IllegalArgumentException("At least 5 characters must be provided to create a body message.");
            }
            return new NewLinkComment(body, urlEmbed, fileEmbed, shownFileName);
        }
    }
}
