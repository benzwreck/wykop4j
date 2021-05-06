package io.github.benzwreck.wykop4j.entries;

import java.io.File;
import java.util.Optional;

public class NewEntryComment {
    private final String body;
    private final String urlEmbed;
    private final File fileEmbed;
    private final String shownFileName;

    private NewEntryComment(String body, String urlEmbed, File fileEmbed, String shownFileName) {
        this.body = body;
        this.urlEmbed = urlEmbed;
        this.fileEmbed = fileEmbed;
        this.shownFileName = shownFileName;
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

    public static class Builder {
        private String body;
        private String urlEmbed;
        private File fileEmbed;
        private String shownFileName;

        public Builder() {
        }
        public Builder withBody(String body){
            this.body = body;
            return this;
        }
        public Builder withMedia(String urlEmbed){
            this.urlEmbed = urlEmbed;
            return this;
        }
        public Builder withMedia(File fileEmbed){
            this.fileEmbed = fileEmbed;
            this.shownFileName = fileEmbed.getName();
            return this;
        }
        public Builder withMedia(File fileEmbed, String shownFileName){
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
