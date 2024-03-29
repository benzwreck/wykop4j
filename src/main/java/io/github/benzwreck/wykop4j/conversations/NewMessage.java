package io.github.benzwreck.wykop4j.conversations;

import java.io.File;
import java.net.URL;
import java.util.Optional;

/**
 * This class contains a recipe for a new message that will be sent to a user.<br>
 * To create a new class use {@link NewMessage.Builder}.
 */
public class NewMessage {
    private final String body;
    private final URL urlEmbed;
    private final File fileEmbed;
    private final String shownFileName;
    private final boolean adultOnly;

    NewMessage(String body, URL urlEmbed, File fileEmbed, String shownFileName, boolean adultOnly) {
        this.body = body;
        this.urlEmbed = urlEmbed;
        this.fileEmbed = fileEmbed;
        this.shownFileName = shownFileName;
        this.adultOnly = adultOnly;
    }

    /**
     * Gets possible body.
     */
    public Optional<String> body() {
        return Optional.ofNullable(body);
    }

    /**
     * Gets possible url to embed media.
     */
    public Optional<URL> urlEmbed() {
        return Optional.ofNullable(urlEmbed);
    }

    /**
     * Gets possible file of embed media.
     */
    public Optional<File> fileEmbed() {
        return Optional.ofNullable(fileEmbed);
    }

    /**
     * Gets possible shown file name of embed media.
     */
    public Optional<String> shownFileName() {
        return Optional.ofNullable(shownFileName);
    }

    /**
     * Returns if embed is adult only.
     */
    public boolean adultOnly() {
        return adultOnly;
    }

    public static final class Builder {
        private String body;
        private URL urlEmbed;
        private File fileEmbed;
        private String shownFileName;
        private boolean adultOnly = false;

        public Builder() {
        }

        public Builder(NewMessage newMessage) {
            this.body = newMessage.body;
            this.urlEmbed = newMessage.urlEmbed;
            this.fileEmbed = newMessage.fileEmbed;
            this.shownFileName = newMessage.shownFileName;
            this.adultOnly = newMessage.adultOnly;
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
            return this;
        }

        public Builder withMedia(File fileEmbed, String shownFileName) {
            this.fileEmbed = fileEmbed;
            this.shownFileName = shownFileName;
            return this;
        }

        public Builder adultOnly() {
            this.adultOnly = true;
            return this;
        }

        public NewMessage build() {
            if (body == null || body.isEmpty()) {
                throw new IllegalArgumentException("Body length must be greater than zero.");
            }
            return new NewMessage(body, urlEmbed, fileEmbed, shownFileName, adultOnly);
        }

    }
}
