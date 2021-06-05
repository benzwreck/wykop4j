package io.github.benzwreck.wykop4j.profiles;

import java.net.URL;
import java.util.Optional;

/**
 * This class contains basic information about profile.
 * To get more data check {@link FullProfile}.
 */
public class SimpleProfile {
    private final String login;
    private final Color color;
    private final Sex sex;
    private final URL avatar;


    public SimpleProfile(String login, Color color, Sex sex, URL avatar) {
        this.login = login;
        this.color = color;
        this.sex = sex;
        this.avatar = avatar;
    }

    /**
     * Gets user's login.
     */
    public String login() {
        return login;
    }

    /**
     * Gets user's {@link Color}.
     */
    public Color color() {
        return color;
    }

    /**
     * Gets possible user's {@link Sex}.
     */
    public Optional<Sex> sex() {
        return Optional.ofNullable(sex);
    }

    /**
     * Gets user's avatar url.
     */
    public URL avatar() {
        return avatar;
    }

    @Override
    public String toString() {
        return "SimpleProfile{" +
                "login='" + login + '\'' +
                ", color=" + color +
                ", sex=" + sex +
                ", avatar='" + avatar + '\'' +
                '}';
    }

}