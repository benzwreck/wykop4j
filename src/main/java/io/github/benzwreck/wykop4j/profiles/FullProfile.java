package io.github.benzwreck.wykop4j.profiles;

import java.time.LocalDateTime;
import java.util.Optional;

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

    public String login() {
        return login;
    }

    public Color color() {
        return color;
    }

    public Optional<Sex> sex() {
        return Optional.ofNullable(sex);
    }

    public String avatar() {
        return avatar;
    }

    public Integer followers() {
        return followers;
    }

    public Optional<String> name() {
        return Optional.ofNullable(name);
    }

    public Optional<String> city() {
        return Optional.ofNullable(city);
    }

    public LocalDateTime signupAt() {
        return signupAt;
    }

    public Optional<String> background() {
        return Optional.ofNullable(background);
    }

    public boolean isVerified() {
        return isVerified;
    }

    public boolean isObserved() {
        return isObserved;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public Optional<String> email() {
        return Optional.ofNullable(publicEmail);
    }

    public Optional<String> about() {
        return Optional.ofNullable(about);
    }

    public Optional<String> www() {
        return Optional.ofNullable(www);
    }

    public Optional<String> jabber() {
        return Optional.ofNullable(jabber);
    }

    public Optional<String> gg() {
        return Optional.ofNullable(gg);
    }

    public Optional<String> facebook() {
        return Optional.ofNullable(facebook);
    }

    public Optional<String> twitter() {
        return Optional.ofNullable(twitter);
    }

    public Optional<String> instagram() {
        return Optional.ofNullable(instagram);
    }

    public int linksAddedCount() {
        return linksAddedCount;
    }

    public int linksPublishedCount() {
        return linksPublishedCount;
    }

    public int commentsCount() {
        return commentsCount;
    }

    public int rank() {
        return rank;
    }

    public int following() {
        return following;
    }

    public int entries() {
        return entries;
    }

    public int entriesComments() {
        return entriesComments;
    }

    public int diggs() {
        return diggs;
    }

    public int buries() {
        return buries;
    }

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