package io.github.benzwreck.wykop4j.entries;

import io.github.benzwreck.wykop4j.profiles.SimpleProfile;

import java.time.LocalDateTime;
import java.util.Optional;

public class Entry {
    private final Integer id;
    private final LocalDateTime date;
    private final String body;
    private final SimpleProfile author;
    private final Boolean blocked;
    private final Boolean favorite;
    private final Integer voteCount;
    private final Integer commentsCount;
    private final String status;
    private final Embed embed;
    private final Integer userVote;
    private final String app;
    private final String violationUrl;
    private final String original;
    private final String url;

    public Entry(Integer id, LocalDateTime date, String body, SimpleProfile author, Boolean blocked, Boolean favorite, Integer voteCount, Integer commentsCount, String status, Embed embed, Integer userVote, String app, String violationUrl, String original, String url) {
        this.id = id;
        this.date = date;
        this.body = body;
        this.author = author;
        this.blocked = blocked;
        this.favorite = favorite;
        this.voteCount = voteCount;
        this.commentsCount = commentsCount;
        this.status = status;
        this.embed = embed;
        this.userVote = userVote;
        this.app = app;
        this.violationUrl = violationUrl;
        this.original = original;
        this.url = url;
    }

    /**
     * @return id
     */
    public Integer id() {
        return id;
    }

    /**
     * @return creation date.
     */
    public LocalDateTime date() {
        return date;
    }

    /**
     * @return body.
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
     * @return number of comments.
     */
    public Integer commentsCount() {
        return commentsCount;
    }

    /**
     * @return status.
     */
    public String status() {
        return status;
    }

    /**
     * @return possible {@link Embed} file.
     */
    public Optional<Embed> embed() {
        return Optional.ofNullable(embed);
    }

    /**
     * @return current user's vote.
     */
    public Integer userVote() {
        return userVote;
    }

    /**
     * @return possible application name via which entry was sent.
     */
    public Optional<String> app() {
        return Optional.ofNullable(app);
    }

    /**
     * @return author's {@link SimpleProfile}.
     */
    public SimpleProfile author() {
        return author;
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
     * @return url.
     */
    public String url() {
        return url;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", body='" + body + '\'' +
                ", author=" + author +
                ", blocked=" + blocked +
                ", favorite=" + favorite +
                ", voteCount=" + voteCount +
                ", commentsCount=" + commentsCount +
                ", status='" + status + '\'' +
                ", embed=" + embed +
                ", userVote=" + userVote +
                ", app='" + app + '\'' +
                '}';
    }
}

