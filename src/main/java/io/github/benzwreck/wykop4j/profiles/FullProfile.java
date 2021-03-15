package io.github.benzwreck.wykop4j.profiles;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Extended information about profile.
 */
public class FullProfile {
    private final String login;
    private final Color color;
    private final Sex sex;
    private final String avatar;
    private final LocalDateTime signupAt;
    private final String background;
    private final boolean isVerified;
    private final boolean isObserved;
    private final boolean isBlocked;
    private final String publicEmail;
    private final String about;
    private final String name;
    private final String www;
    private final String jabber;
    private final String gg;
    private final String city;
    private final String facebook;
    private final String twitter;
    private final String instagram;
    private final int linksAddedCount;
    private final int linksPublishedCount;
    private final int commentsCount;
    private final int rank;
    private final int followers;
    private final int following;
    private final int entries;
    private final int entriesComments;
    private final int diggs;
    private final int buries;
    private final String violationUrl;

    public FullProfile(String login, Color color, Sex sex, String avatar, LocalDateTime signupAt, String background, boolean isVerified, boolean isObserved, boolean isBlocked, String publicEmail, String about, String name, String www, String jabber, String gg, String city, String facebook, String twitter, String instagram, int linksAddedCount, int linksPublishedCount, int commentsCount, int rank, int followers, int following, int entries, int entriesComments, int diggs, int buries, String violationUrl) {
        this.login = login;
        this.color = color;
        this.sex = sex;
        this.avatar = avatar;
        this.signupAt = signupAt;
        this.background = background;
        this.isVerified = isVerified;
        this.isObserved = isObserved;
        this.isBlocked = isBlocked;
        this.publicEmail = publicEmail;
        this.about = about;
        this.name = name;
        this.www = www;
        this.jabber = jabber;
        this.gg = gg;
        this.city = city;
        this.facebook = facebook;
        this.twitter = twitter;
        this.instagram = instagram;
        this.linksAddedCount = linksAddedCount;
        this.linksPublishedCount = linksPublishedCount;
        this.commentsCount = commentsCount;
        this.rank = rank;
        this.followers = followers;
        this.following = following;
        this.entries = entries;
        this.entriesComments = entriesComments;
        this.diggs = diggs;
        this.buries = buries;
        this.violationUrl = violationUrl;
    }

    /**
     * @return user's login.
     */
    public String login() {
        return login;
    }

    /**
     * @return user's color.
     */
    public Color color() {
        return color;
    }

    /**
     * @return possible user's sex. Sometimes it's not defined - no stripe under user's avatar.
     */
    public Optional<Sex> sex() {
        return Optional.ofNullable(sex);
    }

    /**
     * @return url to user's avatar.
     */
    public String avatar() {
        return avatar;
    }

    /**
     * @return amount of user's followers.
     */
    public int followers() {
        return followers;
    }

    /**
     * @return possible user's name.
     */
    public Optional<String> name() {
        return Optional.ofNullable(name);
    }

    /**
     * @return possible user's city.
     */
    public Optional<String> city() {
        return Optional.ofNullable(city);
    }

    /**
     * @return user's sign up date.
     */
    public LocalDateTime signupAt() {
        return signupAt;
    }

    /**
     * @return possible url to user's background image.
     */
    public Optional<String> background() {
        return Optional.ofNullable(background);
    }

    /**
     * @return true - verified, false - not verified.
     */
    public boolean isVerified() {
        return isVerified;
    }

    /**
     * @return true - observed by current user, false - not observed.
     */
    public boolean isObserved() {
        return isObserved;
    }

    /**
     * @return true - blocked by current user, false - not blocked.
     */
    public boolean isBlocked() {
        return isBlocked;
    }

    /**
     * @return possible user's email.
     */
    public Optional<String> email() {
        return Optional.ofNullable(publicEmail);
    }

    /**
     * @return possible user's about note.
     */
    public Optional<String> about() {
        return Optional.ofNullable(about);
    }

    /**
     * @return possible user's website.
     */
    public Optional<String> www() {
        return Optional.ofNullable(www);
    }

    /**
     * @return possible user's jabber.
     */
    public Optional<String> jabber() {
        return Optional.ofNullable(jabber);
    }

    /**
     * @return possible user's gg number.
     */
    public Optional<String> gg() {
        return Optional.ofNullable(gg);
    }

    /**
     * @return possible user's facebook account url.
     */
    public Optional<String> facebook() {
        return Optional.ofNullable(facebook);
    }

    /**
     * @return possible user's twitter account url.
     */
    public Optional<String> twitter() {
        return Optional.ofNullable(twitter);
    }

    /**
     * @return possible user's instagram url.
     */
    public Optional<String> instagram() {
        return Optional.ofNullable(instagram);
    }

    /**
     * @return amount of links added by user.
     */
    public int linksAddedCount() {
        return linksAddedCount;
    }

    /**
     * @return amount of links published by user.
     */
    public int linksPublishedCount() {
        return linksPublishedCount;
    }

    /**
     * @return amount of user's comments.
     */
    public int commentsCount() {
        return commentsCount;
    }

    /**
     * @return user's rank.
     */
    public int rank() {
        return rank;
    }

    /**
     * @return amount of people following user.
     */
    public int following() {
        return following;
    }

    /**
     * @return amount of user's entries.
     */
    public int entries() {
        return entries;
    }

    /**
     * @return amount of user's entries' comments
     */
    public int entriesComments() {
        return entriesComments;
    }

    /**
     * @return amount of user's digged links.
     */
    public int diggs() {
        return diggs;
    }

    /**
     * @return amount of user's buried links.
     */
    public int buries() {
        return buries;
    }

    /**
     * @return user's violation url.
     */
    public String violationUrl() {
        return violationUrl;
    }

    @Override
    public String toString() {
        return "FullProfile{" +
                "login='" + login + '\'' +
                ", color=" + color +
                ", sex=" + sex +
                ", avatar='" + avatar + '\'' +
                ", signupAt=" + signupAt +
                ", background='" + background + '\'' +
                ", isVerified=" + isVerified +
                ", isObserved=" + isObserved +
                ", isBlocked=" + isBlocked +
                ", email='" + publicEmail + '\'' +
                ", about='" + about + '\'' +
                ", name='" + name + '\'' +
                ", www='" + www + '\'' +
                ", jabber='" + jabber + '\'' +
                ", gg='" + gg + '\'' +
                ", city='" + city + '\'' +
                ", facebook='" + facebook + '\'' +
                ", twitter='" + twitter + '\'' +
                ", instagram='" + instagram + '\'' +
                ", linksAddedCount=" + linksAddedCount +
                ", linksPublishedCount=" + linksPublishedCount +
                ", commentsCount=" + commentsCount +
                ", rank=" + rank +
                ", followers=" + followers +
                ", following=" + following +
                ", entries=" + entries +
                ", entriesComments=" + entriesComments +
                ", diggs=" + diggs +
                ", buries=" + buries +
                ", violationUrl='" + violationUrl + '\'' +
                '}';
    }
}