package io.github.benzwreck.wykop4j.links;

/**
 * This class represents an image of new link.
 */
public class LinkImage {
    private final String key;
    private final String type;
    private final String previewUrl;
    private final String sourceUrl;

    public LinkImage(String key, String type, String previewUrl, String sourceUrl) {
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
    public String previewUrl() {
        return previewUrl;
    }

    /**
     * Gets image's source url.
     */
    public String sourceUrl() {
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


