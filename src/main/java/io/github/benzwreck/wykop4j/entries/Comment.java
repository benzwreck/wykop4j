package io.github.benzwreck.wykop4j.entries;

import io.github.benzwreck.wykop4j.profiles.SimpleProfile;

import java.time.LocalDateTime;
import java.util.Optional;

public class Comment {
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

    public Comment(Integer id, SimpleProfile author, LocalDateTime date, String body, Boolean blocked, Boolean favorite, Integer voteCount, Embed embed, String status, UserVote userVote, String app, String violationUrl, String original, Integer entryId) {
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

    public Integer id() {
        return id;
    }

    public SimpleProfile author() {
        return author;
    }

    public LocalDateTime date() {
        return date;
    }


    public String body() {
        return body;
    }

    public Boolean blocked() {
        return blocked;
    }

    public Boolean favorite() {
        return favorite;
    }

    public Integer voteCount() {
        return voteCount;
    }

    public Optional<Embed> embed() {
        return Optional.ofNullable(embed);
    }

    public String status() {
        return status;
    }

    public UserVote userVote() {
        return userVote;
    }

    public Optional<String> app() {
        return Optional.ofNullable(app);
    }

    public String violationUrl() {
        return violationUrl;
    }

    public String original() {
        return original;
    }

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
