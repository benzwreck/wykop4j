package io.github.benzwreck.wykop4j.links;

import io.github.benzwreck.wykop4j.profiles.SimpleProfile;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * This class represents a single link with comments.
 * Check out {@link Link} if you do not need comments.
 */
public class LinkWithComments {
    private final Integer id;
    private final String title;
    private final String description;
    private final String tags;
    private final String sourceUrl;
    private final Integer voteCount;
    private final Integer buryCount;
    private final Integer commentsCount;
    private final Integer relatedCount;
    private final LocalDateTime date;
    private final SimpleProfile author;
    private final String preview;
    private final Boolean plus18;
    private final String status;
    private final Boolean canVote;
    private final Boolean isHot;
    private final Boolean archived;
    private final List<LinkComment> comments;
    private final Boolean userFavorite;
    private final Boolean userObserve;
    private final Boolean isRecommended;
    private final String app;
    private final Boolean hasOwnContent;
    private final Info info;
    private final URL url;
    private final URL violationUrl;

    public LinkWithComments(Integer id, String title, String description, String tags, String sourceUrl, Integer voteCount, Integer buryCount, Integer commentsCount, Integer relatedCount, LocalDateTime date, SimpleProfile author, String preview, Boolean plus18, String status, Boolean canVote, Boolean isHot, Boolean archived, List<LinkComment> comments, Boolean userFavorite, Boolean userObserve, Boolean isRecommended, String app, Boolean hasOwnContent, Info info, URL url, URL violationUrl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.tags = tags;
        this.sourceUrl = sourceUrl;
        this.voteCount = voteCount;
        this.buryCount = buryCount;
        this.commentsCount = commentsCount;
        this.relatedCount = relatedCount;
        this.date = date;
        this.author = author;
        this.preview = preview;
        this.plus18 = plus18;
        this.status = status;
        this.canVote = canVote;
        this.isHot = isHot;
        this.archived = archived;
        this.comments = List.copyOf(comments);
        this.userFavorite = userFavorite;
        this.userObserve = userObserve;
        this.isRecommended = isRecommended;
        this.app = app;
        this.hasOwnContent = hasOwnContent;
        this.info = info;
        this.url = url;
        this.violationUrl = violationUrl;
    }

    /**
     * Gets link's id.
     */
    public Integer id() {
        return id;
    }

    /**
     * Gets link's title.
     */
    public String title() {
        return title;
    }

    /**
     * Gets link's description.
     */
    public String description() {
        return description;
    }

    /**
     * Gets link's tags.
     */
    public String tags() {
        return tags;
    }

    /**
     * Gets link's source url.
     */
    public String sourceUrl() {
        return sourceUrl;
    }

    /**
     * Gets link's vote count.
     */
    public Integer voteCount() {
        return voteCount;
    }

    /**
     * Gets link's bury count.
     */
    public Integer buryCount() {
        return buryCount;
    }

    /**
     * Gets link's comments count.
     */
    public Integer commentsCount() {
        return commentsCount;
    }

    /**
     * Gets link's related links count.
     */
    public Integer relatedCount() {
        return relatedCount;
    }

    /**
     * Gets link's date and time of creation.
     */
    public LocalDateTime date() {
        return date;
    }

    /**
     * Gets link's author.
     */
    public SimpleProfile author() {
        return author;
    }

    /**
     * Gets link's preview.
     */
    public String preview() {
        return preview;
    }

    /**
     * Returns if link is adult only.
     */
    public Boolean plus18() {
        return plus18;
    }

    /**
     * Gets link's status.
     */
    public String status() {
        return status;
    }

    /**
     * Returns if user can vote.
     */
    public Boolean canVote() {
        return canVote;
    }

    /**
     * Returns if link is hot.
     */
    public Boolean isHot() {
        return isHot;
    }

    /**
     * Returns if link is archived.
     */
    public Boolean archived() {
        return archived;
    }

    /**
     * Returns if link is user's favorite.
     */
    public Boolean userFavorite() {
        return userFavorite;
    }

    /**
     * Returns if link is observed by user.
     */
    public Boolean userObserve() {
        return userObserve;
    }

    /**
     * Returns if link is recommended.
     */
    public Boolean isRecommended() {
        return isRecommended;
    }

    /**
     * Gets possible application's name from which link was created.
     */
    public Optional<String> app() {
        return Optional.ofNullable(app);
    }

    /**
     * Returns if link is created from an external link or was created by the user.
     */
    public Boolean hasOwnContent() {
        return hasOwnContent;
    }

    /**
     * Gets link's url.
     */
    public URL url() {
        return url;
    }

    /**
     * Gets link's violation url.
     */
    public URL violationUrl() {
        return violationUrl;
    }

    /**
     * Gets possible link's info e.g. if it's buried.
     */
    public Optional<Info> info() {
        return Optional.ofNullable(info);
    }

    /**
     * Gets link's comments.
     */
    public List<LinkComment> comments() {
        return comments;
    }

    @Override
    public String toString() {
        return "LinkWithComments{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", tags='" + tags + '\'' +
                ", sourceUrl='" + sourceUrl + '\'' +
                ", voteCount=" + voteCount +
                ", buryCount=" + buryCount +
                ", commentsCount=" + commentsCount +
                ", relatedCount=" + relatedCount +
                ", date=" + date +
                ", author=" + author +
                ", preview='" + preview + '\'' +
                ", plus18=" + plus18 +
                ", status='" + status + '\'' +
                ", canVote=" + canVote +
                ", isHot=" + isHot +
                ", archived=" + archived +
                ", comments=" + comments +
                ", userFavorite=" + userFavorite +
                ", userObserve=" + userObserve +
                ", isRecommended=" + isRecommended +
                ", app='" + app + '\'' +
                ", hasOwnContent=" + hasOwnContent +
                ", info=" + info +
                ", url='" + url + '\'' +
                ", violationUrl='" + violationUrl + '\'' +
                '}';
    }

    /**
     * This class contains link's info.
     */
    public static class Info {

        private final String body;
        private final Link.Info.Color color;

        public Info(String body, Link.Info.Color color) {
            this.body = body;
            this.color = color;
        }

        /**
         * Gets info's body.
         */
        public String body() {
            return body;
        }

        /**
         * Gets info's color.
         */
        public Link.Info.Color color() {
            return color;
        }

        @Override
        public String toString() {
            return "Info{" +
                    "body='" + body + '\'' +
                    ", color=" + color +
                    '}';
        }

        /**
         * This class contains available Info's color.
         */
        public enum Color {
            RED, YELLOW, GREEN, UNDEFINED
        }
    }
}
