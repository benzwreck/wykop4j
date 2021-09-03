package io.github.benzwreck.wykop4j.media;

import java.net.URL;

/**
 * This class represents an embed media. Holds data for images, videos, gifs etc.
 */
public class Embed {
    private final String type;
    private final URL url;
    private final String source;
    private final String preview;
    private final Boolean plus18;
    private final String size;
    private final Boolean animated;
    private final Float ratio;

    public Embed(String type, URL url, String source, String preview, Boolean plus18, String size, Boolean animated, Float ratio) {
        this.type = type;
        this.url = url;
        this.source = source;
        this.preview = preview;
        this.plus18 = plus18;
        this.size = size;
        this.animated = animated;
        this.ratio = ratio;
    }

    /**
     * Gets type of file. E.g. "image", "video"
     */
    public String type() {
        return type;
    }

    /**
     * Gets url to media source.
     */
    public URL url() {
        return url;
    }

    /**
     * Gets original source content.
     */
    public String source() {
        return source;
    }

    /**
     * Gets url to media preview.
     */
    public String preview() {
        return preview;
    }

    /**
     * Returns if content is adult only.
     */
    public Boolean plus18() {
        return plus18;
    }

    /**
     * Gets size of posted media.
     */
    public String size() {
        return size;
    }

    /**
     * Returns if content is animated.
     */
    public Boolean animated() {
        return animated;
    }

    /**
     * Gets image height to width ratio.
     */
    public Float ratio() {
        return ratio;
    }

    @Override
    public String toString() {
        return "Embed{" +
                "type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", source='" + source + '\'' +
                ", preview='" + preview + '\'' +
                ", plus18=" + plus18 +
                ", size='" + size + '\'' +
                ", animated=" + animated +
                ", ratio=" + ratio +
                '}';
    }
}
