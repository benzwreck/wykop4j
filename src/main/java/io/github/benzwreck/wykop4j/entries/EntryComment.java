package io.github.benzwreck.wykop4j.entries;

import io.github.benzwreck.wykop4j.media.Embed;
import io.github.benzwreck.wykop4j.profiles.SimpleProfile;
import io.github.benzwreck.wykop4j.shared.UserVote;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * This class represents Microblog's entry's comment.
 */
public class EntryComment {
    private final Integer id;
    private final SimpleProfile author;
    private final LocalDateTime date;
    private final String body;
    private final Boolean blocked;
    private final Boolean favorite;
    private final Integer voteCount;
    private final Embed embed;
    private final String status;
    private final UserVote userVote;
    private final String app;
    private final String violationUrl;
    private final String original;
    private final Integer entryId;

    public EntryComment(Integer id, SimpleProfile author, LocalDateTime date, String body, Boolean blocked, Boolean favorite, Integer voteCount, Embed embed, String status, UserVote userVote, String app, String violationUrl, String original, Integer entryId) {
        this.id = id;
        this.author = author;
        this.date = date;
        this.body = body;
        this.blocked = blocked;
        this.favorite = favorite;
        this.voteCount = voteCount;
        this.embed = embed;
        this.status = status;
        this.userVote = userVote;
        this.app = app;
        this.violationUrl = violationUrl;
        this.original = original;
        this.entryId = entryId;
    }

    /**
     * Gets comment's id.
     */
    public Integer id() {
        return id;
    }

    /**
     * Gets comment's author's {@link SimpleProfile}.
     */
    public SimpleProfile author() {
        return author;
    }

    /**
     * Gets comment's creation date.
     */
    public LocalDateTime date() {
        return date;
    }

    /**
     * Gets comment's body.
     */
    public String body() {
        return body;
    }

    /**
     * Returns if comment is blocked by logged in user.
     */
    public Boolean blocked() {
        return blocked;
    }

    /**
     * Returns if comment is favorited by logged in user.
     */
    public Boolean favorite() {
        return favorite;
    }

    /**
     * Gets comment's vote count.
     */
    public Integer voteCount() {
        return voteCount;
    }

    /**
     * Gets comment's possible {@link Embed} media.
     */
    public Optional<Embed> embed() {
        return Optional.ofNullable(embed);
    }

    /**
     * Gets comment's status.
     */
    public String status() {
        return status;
    }

    /**
     * Gets comment's current user's vote.
     */
    public UserVote userVote() {
        return userVote;
    }

    /**
     * Gets comment's possible application name used to create this comment.
     */
    public Optional<String> app() {
        return Optional.ofNullable(app);
    }

    /**
     * Gets comment's url to violation form.
     */
    public String violationUrl() {
        return violationUrl;
    }

    /**
     * Gets comment's original body.
     */
    public String original() {
        return original;
    }

    /**
     * Gets comment's id of commented entry.
     */
    public Integer entryId() {
        return entryId;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", author=" + author +
                ", date=" + date +
                ", body='" + body + '\'' +
                ", blocked=" + blocked +
                ", favorite=" + favorite +
                ", voteCount=" + voteCount +
                ", embed=" + embed +
                ", status='" + status + '\'' +
                ", userVote=" + userVote +
                ", app='" + app + '\'' +
                ", violationUrl='" + violationUrl + '\'' +
                ", original='" + original + '\'' +
                ", entryId=" + entryId +
                '}';
    }
}
