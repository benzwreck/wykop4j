package io.github.benzwreck.wykop4j.links;

import io.github.benzwreck.wykop4j.profiles.SimpleProfile;

import java.time.LocalDateTime;

public class Link {
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
    private final Boolean userFavorite;
    private final Boolean userObserve;
    private final Boolean hasOwnContent;
    private final String url;
    private final String violationUrl;

    public Link(Integer id, String title, String description, String tags, String sourceUrl, Integer voteCount, Integer buryCount, Integer commentsCount, Integer relatedCount, LocalDateTime date, SimpleProfile author, String preview, Boolean plus18, String status, Boolean canVote, Boolean isHot, Boolean userFavorite, Boolean userObserve, Boolean hasOwnContent, String url, String violationUrl) {
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
        this.userFavorite = userFavorite;
        this.userObserve = userObserve;
        this.hasOwnContent = hasOwnContent;
        this.url = url;
        this.violationUrl = violationUrl;
    }

    public Integer id() {
        return id;
    }

    public String title() {
        return title;
    }

    public String description() {
        return description;
    }

    public String tags() {
        return tags;
    }

    public String sourceUrl() {
        return sourceUrl;
    }

    public Integer voteCount() {
        return voteCount;
    }

    public Integer buryCount() {
        return buryCount;
    }

    public Integer commentsCount() {
        return commentsCount;
    }

    public Integer relatedCount() {
        return relatedCount;
    }

    public LocalDateTime date() {
        return date;
    }

    public SimpleProfile author() {
        return author;
    }

    public String preview() {
        return preview;
    }

    public Boolean plus18() {
        return plus18;
    }

    public String status() {
        return status;
    }

    public Boolean canVote() {
        return canVote;
    }

    public Boolean isHot() {
        return isHot;
    }

    public Boolean userFavorite() {
        return userFavorite;
    }

    public Boolean userObserve() {
        return userObserve;
    }

    public Boolean hasOwnContent() {
        return hasOwnContent;
    }

    public String url() {
        return url;
    }

    public String violationUrl() {
        return violationUrl;
    }

    @Override
    public String toString() {
        return "Link{" +
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
                ", userFavorite=" + userFavorite +
                ", userObserve=" + userObserve +
                ", hasOwnContent=" + hasOwnContent +
                ", url='" + url + '\'' +
                ", violationUrl='" + violationUrl + '\'' +
                '}';
    }
}
