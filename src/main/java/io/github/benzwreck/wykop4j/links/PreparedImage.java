package io.github.benzwreck.wykop4j.links;

public class PreparedImage {
    private final String key;
    private final String type;
    private final String previewUrl;
    private final String sourceUrl;

    public PreparedImage(String key, String type, String previewUrl, String sourceUrl) {
        this.key = key;
        this.type = type;
        this.previewUrl = previewUrl;
        this.sourceUrl = sourceUrl;
    }

    public String key() {
        return key;
    }

    public String type() {
        return type;
    }

    public String previewUrl() {
        return previewUrl;
    }

    public String sourceUrl() {
        return sourceUrl;
    }

    @Override
    public String toString() {
        return "PreparedImage{" +
                "key='" + key + '\'' +
                ", type='" + type + '\'' +
                ", previewUrl='" + previewUrl + '\'' +
                ", sourceUrl='" + sourceUrl + '\'' +
                '}';
    }
}


