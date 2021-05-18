package io.github.benzwreck.wykop4j.entries;

import io.github.benzwreck.wykop4j.media.Embed;
import io.github.benzwreck.wykop4j.profiles.SimpleProfile;
import io.github.benzwreck.wykop4j.shared.UserVote;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * This class represents Microblog's single entry.
 */
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
     * Gets entry's id.
     */
    public Integer id() {
        return id;
    }

    /**
     * Gets entry's creation date.
     */
    public LocalDateTime date() {
        return date;
    }

    /**
     * Gets possible entry's body.
     */
    public Optional<String> body() {
        return Optional.ofNullable(body);
    }

    /**
     * Returns if entry is blocked by logged in user.
     */
    public Boolean blocked() {
        return blocked;
    }

    /**
     * Returns if entry is favorited by logged in user.
     */
    public Boolean favorite() {
        return favorite;
    }

    /**
     * Gets entry's vote count.
     */
    public Integer voteCount() {
        return voteCount;
    }

    /**
     * Get entry's comments count.
     */
    public Integer commentsCount() {
        return commentsCount;
    }


    // TODO: Entry -> EntryWithComments and Entry

    /**
     * Gets list of entry's comments.<br>
     * <p>
     * If {@code Entry::commentsCount} > 0, you should use {@code WykopClient::entry}
     * with this entry's id to get those comments.
     */
    public Optional<List<EntryComment>> comments() {
        return Optional.of(Collections.unmodifiableList(comments));
    }

    /**
     * Gets entry's status.
     */
    public String status() {
        return status;
    }

    /**
     * Gets possible entry's {@link Embed} media.
     */
    public Optional<Embed> embed() {
        return Optional.ofNullable(embed);
    }

    /**
     * Gets entry's current user's vote.
     */
    public UserVote userVote() {
        return userVote;
    }

    /**
     * Gets possible application name used to create this entry.
     */
    public Optional<String> app() {
        return Optional.ofNullable(app);
    }

    /**
     * Gets entry's author's {@link SimpleProfile}.
     */
    public SimpleProfile author() {
        return author;
    }

    /**
     * Gets possible entry's url to violation form.
     */
    public Optional<String> violationUrl() {
        return Optional.ofNullable(violationUrl);
    }

    /**
     * Gets entry's original body.
     */
    public String original() {
        return original;
    }

    /**
     * Gets entry's url.
     */
    public String url() {
        return url;
    }

    /**
     * Gets possible entry's {@link Survey}
     */
    public Optional<Survey> survey() {
        return Optional.ofNullable(survey);
    }

    /**
     * Returns if user can comment this entry.
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

