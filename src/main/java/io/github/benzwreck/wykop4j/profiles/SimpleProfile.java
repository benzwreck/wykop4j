package io.github.benzwreck.wykop4j.profiles;

import java.time.LocalDateTime;
import java.util.Optional;

public class SimpleProfile {
    private final String login;
    private final Color color;
    private final Sex sex;
    private final String avatar;
    private final LocalDateTime signupAt;
    private final String background;
    private final String violationUrl;

    public SimpleProfile(String login, Color color, Sex sex, String avatar, LocalDateTime signupAt, String background, String violationUrl) {
        this.login = login;
        this.color = color;
        this.sex = sex;
        this.avatar = avatar;
        this.signupAt = signupAt;
        this.background = background;
        this.violationUrl = violationUrl;
    }

    /**
     * @return login.
     */
    public String login() {
        return login;
    }

    /**
     * @return user's {@link Color}
     */
    public Color color() {
        return color;
    }

    /**
     * @return possible user's {@link Sex}
     */
    public Optional<Sex> sex() {
        return Optional.ofNullable(sex);
    }

    /**
     * @return user's avatar url.
     */
    public String avatar() {
        return avatar;
    }

    /**
     * @return user's sign up date.
     */
    public LocalDateTime signupAt() {
        return signupAt;
    }

    /**
     * @return possible background's url.
     */
    public Optional<String> background() {
        return Optional.ofNullable(background);
    }

    /**
     * @return url to violation form.
     */
    public String violationUrl() {
        return violationUrl;
    }

    @Override
    public String toString() {
        return "SimpleProfile{" +
                "login='" + login + '\'' +
                ", color=" + color +
                ", sex=" + sex +
                ", avatar='" + avatar + '\'' +
                ", signupAt=" + signupAt +
                ", background='" + background + '\'' +
                ", violationUrl='" + violationUrl + '\'' +
                '}';
    }

}