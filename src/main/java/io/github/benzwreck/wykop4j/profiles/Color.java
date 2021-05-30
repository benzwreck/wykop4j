package io.github.benzwreck.wykop4j.profiles;

/**
 * This class contains available profile's colors.
 */
public enum Color {
    GREEN("#339933", RGB.GREEN),
    ORANGE("#FF5917", RGB.ORANGE),
    CLARET("#BB0000", RGB.CLARET),
    ADMIN("#000000", RGB.BLACK),
    BANNED("#999999", RGB.GRAY),
    DELETED("#999999", RGB.GRAY),
    CLIENT("#3F6FA0", RGB.BLUE),
    PLAY("#593787", RGB.PURPLE),
    GOLD("#bf9b30", RGB.GOLD),
    UNDEFINED("undefined", RGB.UNDEFINED);

    private final String hex;
    private final RGB rgb;

    Color(String hex, RGB rgb) {
        this.hex = hex;
        this.rgb = rgb;
    }

    /**
     * Gets the hex value of the color.
     */
    public String hex() {
        return hex;
    }

    /**
     * This class contains RGB values for available colors.
     */
    public enum RGB {
        GREEN(51, 153, 51),
        ORANGE(255, 89, 23),
        CLARET(187, 0, 0),
        BLACK(0, 0, 0),
        PURPLE(72,34,124),
        GOLD(191,155, 48),
        GRAY(153, 153,153),
        BLUE(63, 111, 160),
        UNDEFINED(255,255,255);

        private final int red;
        private final int green;
        private final int blue;

        RGB(int red, int green, int blue) {
            this.red = red;
            this.green = green;
            this.blue = blue;
        }

        /**
         * Gets red value.
         */
        public int red() {
            return red;
        }

        /**
         * Gets green value.
         */
        public int green() {
            return green;
        }

        /**
         * Gets blue value.
         */
        public int blue() {
            return blue;
        }

        @Override
        public String toString() {
            return "RGB{" +
                    "red=" + red +
                    ", green=" + green +
                    ", blue=" + blue +
                    '}';
        }
        }
}
