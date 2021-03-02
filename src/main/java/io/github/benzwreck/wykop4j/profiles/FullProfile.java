package io.github.benzwreck.wykop4j.profiles;

import java.time.LocalDateTime;

public class FullProfile {
    private final String login;
    private final Color color;
    private final Sex sex;
    private final String avatar;
    private final LocalDateTime signupAt;
    private final String background;
    private final Boolean isVerified;
    private final Boolean isObserved;
    private final Boolean isBlocked;
    private final String email;
    private final String about;
    private final String name;
    private final String www;
    private final String jabber;
    private final String gg;
    private final String city;
    private final String facebook;
    private final String twitter;
    private final String instagram;
    private final Integer linksAddedCount;
    private final Integer linksPublishedCount;
    private final Integer commentsCount;
    private final Integer rank;
    private final Integer followers;
    private final Integer following;
    private final Integer entries;
    private final Integer entriesComments;
    private final Integer diggs;
    private final Integer buries;
    private final String violationUrl;

    public FullProfile(String login, Color color, Sex sex, String avatar, LocalDateTime signupAt, String background, Boolean isVerified, Boolean isObserved, Boolean isBlocked, String email, String about, String name, String www, String jabber, String gg, String city, String facebook, String twitter, String instagram, Integer linksAddedCount, Integer linksPublishedCount, Integer commentsCount, Integer rank, Integer followers, Integer following, Integer entries, Integer entriesComments, Integer diggs, Integer buries, String violationUrl) {
        this.login = login;
        this.color = color;
        this.sex = sex;
        this.avatar = avatar;
        this.signupAt = signupAt;
        this.background = background;
        this.isVerified = isVerified;
        this.isObserved = isObserved;
        this.isBlocked = isBlocked;
        this.email = email;
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

    public Sex sex() {
        return sex;
    }

    public String avatar() {
        return avatar;
    }

    public Integer followers() {
        return followers;
    }

    public String name() {
        return name;
    }

    public String city() {
        return city;
    }

    public LocalDateTime signupAt() {
        return signupAt;
    }

    public String background() {
        return background;
    }

    public Boolean isVerified() {
        return isVerified;
    }

    public Boolean isObserved() {
        return isObserved;
    }

    public Boolean isBlocked() {
        return isBlocked;
    }

    public String email() {
        return email;
    }

    public String about() {
        return about;
    }

    public String www() {
        return www;
    }

    public String jabber() {
        return jabber;
    }

    public String gg() {
        return gg;
    }

    public String facebook() {
        return facebook;
    }

    public String twitter() {
        return twitter;
    }

    public String instagram() {
        return instagram;
    }

    public Integer linksAddedCount() {
        return linksAddedCount;
    }

    public Integer linksPublishedCount() {
        return linksPublishedCount;
    }

    public Integer commentsCount() {
        return commentsCount;
    }

    public Integer rank() {
        return rank;
    }

    public Integer following() {
        return following;
    }

    public Integer entries() {
        return entries;
    }

    public Integer entriesComments() {
        return entriesComments;
    }

    public Integer diggs() {
        return diggs;
    }

    public Integer buries() {
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
                ", email='" + email + '\'' +
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