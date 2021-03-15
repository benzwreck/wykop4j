package io.github.benzwreck.wykop4j.links;

import io.github.benzwreck.wykop4j.profiles.SimpleProfile;
import io.github.benzwreck.wykop4j.shared.UserVote;

import java.time.LocalDateTime;

public class LinkComment {
    private final Integer id;
    private final LocalDateTime date;
    private final SimpleProfile author;
    private final Integer voteCount;
    private final Integer voteCountPlus;
    private final String body;
    private final Integer parentId;
    private final Boolean canVote;
    private final UserVote userVote;
    private final Boolean blocked;
    private final Boolean favorite;
    private final Integer linkId;
    private final Link link;
    private final String violationUrl;
    private final Integer voteCountMinus;
    private final String original;

    public LinkComment(Integer id, LocalDateTime date, SimpleProfile author, Integer voteCount, Integer voteCountPlus, String body, Integer parentId, Boolean canVote, UserVote userVote, Boolean blocked, Boolean favorite, Integer linkId, Link link, String violationUrl, Integer voteCountMinus, String original) {
        this.id = id;
        this.date = date;
        this.author = author;
        this.voteCount = voteCount;
        this.voteCountPlus = voteCountPlus;
        this.body = body;
        this.parentId = parentId;
        this.canVote = canVote;
        this.userVote = userVote;
        this.blocked = blocked;
        this.favorite = favorite;
        this.linkId = linkId;
        this.link = link;
        this.violationUrl = violationUrl;
        this.voteCountMinus = voteCountMinus;
        this.original = original;
    }

    public Integer id() {
        return id;
    }

    public LocalDateTime date() {
        return date;
    }

    public SimpleProfile author() {
        return author;
    }

    public Integer voteCount() {
        return voteCount;
    }

    public Integer voteCountPlus() {
        return voteCountPlus;
    }

    public String body() {
        return body;
    }

    public Integer parentId() {
        return parentId;
    }

    public Boolean canVote() {
        return canVote;
    }

    public UserVote userVote() {
        return userVote;
    }

    public Boolean blocked() {
        return blocked;
    }

    public Boolean favorite() {
        return favorite;
    }

    public Integer linkId() {
        return linkId;
    }

    public Link link() {
        return link;
    }

    public String violationUrl() {
        return violationUrl;
    }

    public Integer voteCountMinus() {
        return voteCountMinus;
    }

    public String original() {
        return original;
    }

    @Override
    public String toString() {
        return "LinkComment{" +
                "id=" + id +
                ", date=" + date +
                ", author=" + author +
                ", voteCount=" + voteCount +
                ", voteCountPlus=" + voteCountPlus +
                ", body='" + body + '\'' +
                ", parentId=" + parentId +
                ", canVote=" + canVote +
                ", userVote=" + userVote +
                ", blocked=" + blocked +
                ", favorite=" + favorite +
                ", linkId=" + linkId +
                ", link=" + link +
                ", violationUrl='" + violationUrl + '\'' +
                ", voteCountMinus=" + voteCountMinus +
                ", original='" + original + '\'' +
                '}';
    }
}
