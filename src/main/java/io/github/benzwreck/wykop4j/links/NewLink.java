package io.github.benzwreck.wykop4j.links;

public class NewLink {
    private final String key;
    private final String title;
    private final String description;
    private final String tags;
    private final String photoKey;
    private final String url;
    private final boolean isAdult;

    private NewLink(String key, String title, String description, String tags, String photoKey, String url, boolean isAdult) {
        this.key = key;
        this.title = title;
        this.description = description;
        this.tags = tags;
        this.photoKey = photoKey;
        this.url = url;
        this.isAdult = isAdult;
    }

    public static final class Builder {
        private String key;
        private String title;
        private String description;
        private String tags;
        private String photoKey;
        private String url;
        private boolean isAdult = false;

        public Builder() {
        }

        public Builder withKey(String key) {
            this.key = key;
            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withTags(String tags) {
            this.tags = tags;
            return this;
        }

        public Builder withPhotoKey(String photoKey) {
            this.photoKey = photoKey;
            return this;
        }

        public Builder withUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder adultOnly() {
            this.isAdult = true;
            return this;
        }

        public NewLink build() {
            return new NewLink(key, title, description, tags, photoKey, url, isAdult);
        }
    }
}
