package io.github.benzwreck.wykop4j.links;

import java.net.URL;

/**
 * This class represents an image of new link.
 */
public class LinkImage {
    private final String key;
    private final String type;
    private final URL previewUrl;
    private final URL sourceUrl;

    public LinkImage(String key, String type, URL previewUrl, URL sourceUrl) {
        this.key = key;
        this.type = type;
        this.previewUrl = previewUrl;
        this.sourceUrl = sourceUrl;
    }

    /**
     * Gets image's key.
     */
    public String key() {
        return key;
    }

    /**
     * Gets image's type.
     */
    public String type() {
        return type;
    }

    /**
     * Gets image's preview url.
     */
    public URL previewUrl() {
        return previewUrl;
    }

    /**
     * Gets image's source url.
     */
    public URL sourceUrl() {
        return sourceUrl;
    }

    @Override
    public String toString() {
        return "LinkImage{" +
                "key='" + key + '\'' +
                ", type='" + type + '\'' +
                ", previewUrl='" + previewUrl + '\'' +
                ", sourceUrl='" + sourceUrl + '\'' +
                '}';
    }
}


