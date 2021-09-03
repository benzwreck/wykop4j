package io.github.benzwreck.wykop4j.links;

import io.github.benzwreck.wykop4j.media.Embed;
import io.github.benzwreck.wykop4j.profiles.SimpleProfile;
import io.github.benzwreck.wykop4j.shared.UserVote;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * This class represents a single link's comment.
 */
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
    private final Embed embed;
    private final URL violationUrl;
    private final Integer voteCountMinus;
    private final String original;

    public LinkComment(Integer id, LocalDateTime date, SimpleProfile author, Integer voteCount, Integer voteCountPlus, String body, Integer parentId, Boolean canVote, UserVote userVote, Boolean blocked, Boolean favorite, Integer linkId, Embed embed, URL violationUrl, Integer voteCountMinus, String original) {
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
        this.embed = embed;
        this.violationUrl = violationUrl;
        this.voteCountMinus = voteCountMinus;
        this.original = original;
    }

    /**
     * Gets link comment's id.
     */
    public Integer id() {
        return id;
    }

    /**
     * Gets link comment's date and time of creation.
     */
    public LocalDateTime date() {
        return date;
    }

    /**
     * Gets link comment's author.
     */
    public SimpleProfile author() {
        return author;
    }

    /**
     * Gets link comment's vote count.
     */
    public Integer voteCount() {
        return voteCount;
    }

    /**
     * Gets link comment's vote up count.
     */
    public Integer voteCountPlus() {
        return voteCountPlus;
    }

    /**
     * Gets link comment's body.
     */
    public String body() {
        return body;
    }

    /**
     * Gets the id of commented link or link's comment.
     */
    public Integer parentId() {
        return parentId;
    }

    /**
     * Returns if user can vote.
     */
    public Boolean canVote() {
        return canVote;
    }

    /**
     * Gets link comment's user vote.
     */
    public UserVote userVote() {
        return userVote;
    }

    /**
     * Returns if link is blocked by user.
     */
    public Boolean blocked() {
        return blocked;
    }

    /**
     * Returns if link is favorited by user.
     */
    public Boolean favorite() {
        return favorite;
    }

    /**
     * Gets link comment's link id.
     */
    public Integer linkId() {
        return linkId;
    }

    /**
     * Gets possible link comment's embed media.
     */
    public Optional<Embed> embed() {
        return Optional.ofNullable(embed);
    }

    /**
     * Gets link comment's violation url.
     */
    public URL violationUrl() {
        return violationUrl;
    }

    /**
     * Gets link comment's vote down count.
     */
    public Integer voteCountMinus() {
        return voteCountMinus;
    }

    /**
     * Gets link comment's original content.
     */
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
                ", violationUrl='" + violationUrl + '\'' +
                ", voteCountMinus=" + voteCountMinus +
                ", original='" + original + '\'' +
                '}';
    }
}
