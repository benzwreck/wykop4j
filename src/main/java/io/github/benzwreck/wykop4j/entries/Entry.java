package io.github.benzwreck.wykop4j.entries;

import io.github.benzwreck.wykop4j.media.Embed;
import io.github.benzwreck.wykop4j.profiles.SimpleProfile;
import io.github.benzwreck.wykop4j.shared.UserVote;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
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
    private final List<EntryComment> comments;
    private final String status;
    private final Survey survey;
    private final Boolean canComment;
    private final Embed embed;
    private final UserVote userVote;
    private final String app;
    private final String violationUrl;
    private final String original;
    private final String url;


    public Entry(Integer id, LocalDateTime date, String body, SimpleProfile author, Boolean blocked, Boolean favorite, Integer voteCount, Integer commentsCount, List<EntryComment> comments, String status, Survey survey, Boolean canComment, Embed embed, UserVote userVote, String app, String violationUrl, String original, String url) {
        this.id = id;
        this.date = date;
        this.body = body;
        this.author = author;
        this.blocked = blocked;
        this.favorite = favorite;
        this.voteCount = voteCount;
        this.commentsCount = commentsCount;
        this.comments = comments;
        this.status = status;
        this.survey = survey;
        this.canComment = canComment;
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
     * @return possible body.
     */
    public Optional<String> body() {
        return Optional.ofNullable(body);
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
     * If {@code Entry::commentsCount} > 0, you should use {@code WykopClient::entry}
     * with this entry's id to get those comments.
     *
     * @return possible list of comments.
     */
    public Optional<List<EntryComment>> comments() {
        return Optional.ofNullable(Collections.unmodifiableList(comments));
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
     * @return author's {@link SimpleProfile}.
     */
    public SimpleProfile author() {
        return author;
    }

    /**
     * @return url to violation form.
     */
    public Optional<String> violationUrl() {
        return Optional.ofNullable(violationUrl);
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

    /**
     * @return possible {@link Survey}
     */
    public Optional<Survey> survey() {
        return Optional.ofNullable(survey);
    }

    /**
     * @return if user can comment this entry.
     */
    public Boolean canComment() {
        return canComment;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "id=" + id +
                ", date=" + date +
                ", body='" + body + '\'' +
                ", author=" + author +
                ", blocked=" + blocked +
                ", favorite=" + favorite +
                ", voteCount=" + voteCount +
                ", commentsCount=" + commentsCount +
                ", status='" + status + '\'' +
                ", survey=" + survey +
                ", canComment=" + canComment +
                ", embed=" + embed +
                ", userVote=" + userVote +
                ", app='" + app + '\'' +
                ", violationUrl='" + violationUrl + '\'' +
                ", original='" + original + '\'' +
                ", url='" + url + '\'' +
                '}';
    }


}

