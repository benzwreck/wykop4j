package io.github.benzwreck.wykop4j.media;

public class Embed {
    private final String type;
    private final String url;
    private final String source;
    private final String preview;
    private final Boolean plus18;
    private final String size;
    private final Boolean animated;
    private final Float ratio;

    public Embed(String type, String url, String source, String preview, Boolean plus18, String size, Boolean animated, Float ratio) {
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
     * @return type of file. E.g. "image", "video"
     */
    public String type() {
        return type;
    }

    /**
     * @return url to media source.
     */
    public String url() {
        return url;
    }

    /**
     * @return original source content.
     */
    public String source() {
        return source;
    }

    /**
     * @return url to media preview.
     */
    public String preview() {
        return preview;
    }

    /**
     * @return is adult only.
     */
    public Boolean plus18() {
        return plus18;
    }

    /**
     * @return size of image.
     */
    public String size() {
        return size;
    }

    /**
     * @return is animated.
     */
    public Boolean animated() {
        return animated;
    }

    /**
     * @return image height to width ratio.
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
