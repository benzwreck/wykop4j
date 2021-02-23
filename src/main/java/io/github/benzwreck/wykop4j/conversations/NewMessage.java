package io.github.benzwreck.wykop4j.conversations;

import java.io.File;
import java.util.Optional;

public class NewMessage {
    private final String body;
    private final String urlEmbed;
    private final File fileEmbed;
    private final String shownFileName;
    private final boolean adultOnly;

    NewMessage(String body, String urlEmbed, File fileEmbed, String shownFileName, boolean adultOnly) {
        this.body = body;
        this.urlEmbed = urlEmbed;
        this.fileEmbed = fileEmbed;
        this.shownFileName = shownFileName;
        this.adultOnly = adultOnly;
    }

    public Optional<String> body() {
        return Optional.ofNullable(body);
    }

    public Optional<String> urlEmbed() {
        return Optional.ofNullable(urlEmbed);
    }

    public Optional<File> fileEmbed() {
        return Optional.ofNullable(fileEmbed);
    }

    public Optional<String> shownFileName() {
        return Optional.ofNullable(shownFileName);
    }

    public boolean adultOnly() {
        return adultOnly;
    }

    public class Builder {
        private String body;
        private String urlEmbed;
        private File fileEmbed;
        private String shownFileName;
        private boolean adultOnly = false;

        public Builder() {
        }

        public Builder withBody(String body) {
            this.body = body;
            return this;
        }

        public Builder withMedia(String urlEmbed) {
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
