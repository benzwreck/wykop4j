package io.github.benzwreck.wykop4j.links;

import java.util.Optional;

/**
 * Provides all mandatory information to create a new {@link Link}.
 * All fields except photoKey must be given to create {@link NewLink}.
 */
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

    public String key() {
        return key;
    }

    public String title() {
        return title;
    }

    public String description() {
        return description;
    }

    public String tags() {
        return tags;
    }

    public Optional<String> photoKey() {
        return Optional.of(photoKey);
    }

    public String url() {
        return url;
    }

    public boolean isAdult() {
        return isAdult;
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

        public Builder(Builder builder){
            this.key = builder.key;
            this.title = builder.title;
            this.description = builder.description;
            this.tags = builder.tags;
            this.photoKey = builder.photoKey;
            this.url = builder.url;
            this.isAdult = builder.isAdult;
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
            if (key == null || title == null || description == null || tags == null || url == null)
                throw new IllegalArgumentException("Fields: key, title, description, tags and url must be provided.");
            return new NewLink(key, title, description, tags, photoKey, url, isAdult);
        }
    }
}
