package io.github.benzwreck.wykop4j.entries;

import io.github.benzwreck.wykop4j.media.Embed;
import io.github.benzwreck.wykop4j.profiles.SimpleProfile;

import java.time.LocalDateTime;
import java.util.Optional;

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
     * @return id.
     */
    public Integer id() {
        return id;
    }

    /**
     * @return author's {@link SimpleProfile}
     */
    public SimpleProfile author() {
        return author;
    }

    /**
     * @return creation date.
     */
    public LocalDateTime date() {
        return date;
    }

    /**
     * @return comment's body.
     */
    public String body() {
        return body;
    }

    /**
     * @return is blocked by logged in user.
     */
    public Boolean blocked() {
        return blocked;
    }

    /**
     * @return is favorited by logged in user.
     */
    public Boolean favorite() {
        return favorite;
    }

    /**
     * @return number of votes.
     */
    public Integer voteCount() {
        return voteCount;
    }

    /**
     * @return possible {@link Embed} file.
     */
    public Optional<Embed> embed() {
        return Optional.ofNullable(embed);
    }

    /**
     * @return status.
     */
    public String status() {
        return status;
    }

    /**
     * @return current user's vote.
     */
    public UserVote userVote() {
        return userVote;
    }

    /**
     * @return possible application name via which entry was sent.
     */
    public Optional<String> app() {
        return Optional.ofNullable(app);
    }

    /**
     * @return url to violation form.
     */
    public String violationUrl() {
        return violationUrl;
    }

    /**
     * @return original body.
     */
    public String original() {
        return original;
    }

    /**
     * @return id of commented entry.
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
