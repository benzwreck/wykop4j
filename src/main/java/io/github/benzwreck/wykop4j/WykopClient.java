package io.github.benzwreck.wykop4j;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.benzwreck.wykop4j.entries.Entry;
import io.github.benzwreck.wykop4j.entries.EntryComment;
import io.github.benzwreck.wykop4j.entries.NewComment;
import io.github.benzwreck.wykop4j.entries.NewEntry;
import io.github.benzwreck.wykop4j.entries.Page;
import io.github.benzwreck.wykop4j.entries.Period;
import io.github.benzwreck.wykop4j.entries.Survey;
import io.github.benzwreck.wykop4j.entries.Vote;
import io.github.benzwreck.wykop4j.exceptions.ArchivalContentException;
import io.github.benzwreck.wykop4j.exceptions.NiceTryException;
import io.github.benzwreck.wykop4j.exceptions.UnableToModifyEntryException;

import java.io.File;
import java.util.List;
import java.util.Optional;

public class WykopClient {
    private final static String WYKOP_URL = "https://a2.wykop.pl";
    private final WykopHttpClient client;
    private final WykopObjectMapper wykopObjectMapper;

    WykopClient(WykopHttpClient wykopHttpClient, WykopObjectMapper wykopObjectMapper) {
        this.client = wykopHttpClient;
        this.wykopObjectMapper = wykopObjectMapper;
    }

    /**
     * @return First page of the latest Microblog's Entries.
     */
    public Chain<List<Entry>> entriesStream() {
        return entriesStream(Page.of(1));
    }

    /**
     * @param page available pages for Entries' Stream. Has to be 1 or 2.
     * @return Given page of the latest Microblog's Entries.
     * @throws IllegalArgumentException if page is different than 1 or 2.
     */
    public Chain<List<Entry>> entriesStream(Page page) {
        if (page.value() < 0 || page.value() > 2)
            throw new IllegalArgumentException("Page" + page + "is forbidden. Only page 1 and page 2 are possible to fetch.");
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Entries/Stream/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .build(), new TypeReference<List<Entry>>() {
        });
    }

    /**
     * @param entryId id from where we start counting except entryId
     * @return Microblog's Entries where first {@link Entry} id equals first one after entryId.
     */
    public Chain<List<Entry>> entriesStream(int entryId) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Entries/Stream/firstid/int/")
                .namedParam("firstid", String.valueOf(entryId))
                .build(), new TypeReference<List<Entry>>() {
        });
    }

    /**
     * @param id id of the {@link Entry} you are looking for.
     * @return possible {@link Entry}
     */
    public Chain<Optional<Entry>> entry(int id) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Entries/Entry/entry/")
                .apiParam("entry", String.valueOf(id))
                .build(), new TypeReference<Optional<Entry>>() {
        });
    }

    /**
     * @return First page of 12 hours hot entries.
     */
    public Chain<List<Entry>> hotEntries() {
        return hotEntries(Page.of(1), Period.TWELVE_HOURS);
    }

    /**
     * @param page given hot entries page. Has to be from 1 to 20.
     * @return List of Hot Entries from last 12 hours.
     * @throws IllegalArgumentException if page is not from 1 to 20.
     */
    public Chain<List<Entry>> hotEntries(Page page) {
        return hotEntries(page, Period.TWELVE_HOURS);
    }

    /**
     * @param period available pages for Entries' Hot
     * @return First page of the Hot Entries for a given period.
     */
    public Chain<List<Entry>> hotEntries(Period period) {
        return hotEntries(Page.of(1), period);
    }

    /**
     * @param page   given hot entries page. Has to be between 1 and 20. Throws exception otherwise.
     * @param period available pages for Entries' Hot
     * @return List of Hot Entries for a given page and period.
     * @throws IllegalArgumentException if page is not from 1 to 20.
     */
    public Chain<List<Entry>> hotEntries(Page page, Period period) {
        if (page.value() < 0 || page.value() > 20)
            throw new IllegalArgumentException("Page" + page + "is forbidden. Only pages from 1 to 20 are possible to fetch.");
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Entries/Hot/page/int/period/int/")
                .namedParam("page", String.valueOf(page.value()))
                .namedParam("period", String.valueOf(period.value()))
                .build(), new TypeReference<List<Entry>>() {
        });
    }

    /**
     * @return List of Active Entries from first page.
     */
    public Chain<List<Entry>> activeEntries() {
        return activeEntries(Page.of(1));
    }

    /**
     * @param page given active entries page.
     * @return List of Active Entries from given page.
     */
    public Chain<List<Entry>> activeEntries(Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Entries/Active/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .build(), new TypeReference<List<Entry>>() {
        });
    }

    /**
     * @return List of Observed Entries from first page.
     */
    public Chain<List<Entry>> observedEntries() {
        return observedEntries(Page.of(1));
    }

    /**
     * @param page given observed entries page.
     * @return List of Observed Entries from given page.
     */
    public Chain<List<Entry>> observedEntries(Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Entries/Observed/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .build(), new TypeReference<List<Entry>>() {
        }
        );
    }

    /**
     * @param entryId given entry's id.
     * @return Deleted {@link Entry} - status changes to "deleted"
     */
    public Chain<Entry> deleteEntry(int entryId) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Entries/Delete/entry_id/")
                .apiParam("entry_id", String.valueOf(entryId))
                .build(), Entry.class);
    }

    /**
     * @param newEntry new entry to be added.
     * @return Added {@link Entry}
     */
    public Chain<Entry> addEntry(NewEntry newEntry) {
        WykopRequest.Builder requestBuilder = new WykopRequest.Builder()
                .url(WYKOP_URL + "/Entries/Add/")
                .postParam("adultmedia", String.valueOf(newEntry.adultOnly()));
        newEntry.body().ifPresent(body -> requestBuilder.postParam("body", body));
        newEntry.urlEmbed().ifPresent(url -> requestBuilder.postParam("embed", url));
        Optional<File> fileEmbed = newEntry.fileEmbed();
        if (fileEmbed.isPresent()) {
            Optional<String> shownFileName = newEntry.shownFileName();
            if (shownFileName.isPresent()) {
                requestBuilder.file(fileEmbed.get(), shownFileName.get());
            } else {
                requestBuilder.file(fileEmbed.get());
            }
        }
        return new Chain<>(requestBuilder.build(), Entry.class);
    }

    /**
     * @param entryId  id of modifying entry.
     * @param newEntry new entry body.
     * @return modified {@link Entry}
     * @throws UnableToModifyEntryException when user has no permission to modify this entry.
     */
    public Chain<Entry> editEntry(int entryId, NewEntry newEntry) {
        WykopRequest.Builder requestBuilder = new WykopRequest.Builder()
                .url(WYKOP_URL + "/Entries/Edit/entry_id/")
                .apiParam("entry_id", String.valueOf(entryId))
                .postParam("adultmedia", String.valueOf(newEntry.adultOnly()));
        newEntry.body().ifPresent(body -> requestBuilder.postParam("body", body));
        newEntry.urlEmbed().ifPresent(url -> requestBuilder.postParam("embed", url));
        Optional<File> fileEmbed = newEntry.fileEmbed();
        if (fileEmbed.isPresent()) {
            Optional<String> shownFileName = newEntry.shownFileName();
            if (shownFileName.isPresent()) {
                requestBuilder.file(fileEmbed.get(), shownFileName.get());
            } else {
                requestBuilder.file(fileEmbed.get());
            }
        }
        return new Chain<>(requestBuilder.build(), Entry.class);
    }

    /**
     * @param entryId entry's id to vote up.
     * @return nothing.
     * @throws ArchivalContentException when non-existing entryId provided.
     */
    public Chain<Void> voteUp(int entryId) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Entries/VoteUp/id/")
                .apiParam("id", String.valueOf(entryId))
                .build(), Void.class);
    }

    /**
     * @param entryId entry's id to vote remove.
     * @return nothing.
     * @throws ArchivalContentException when non-existing entryId provided.
     */
    public Chain<Void> removeVote(int entryId) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Entries/VoteRemove/id/")
                .apiParam("id", String.valueOf(entryId))
                .build(), Void.class);
    }

    /**
     * @param entryId entry's id to fetch voters from.
     * @return List of {@link Vote}s.
     */
    public Chain<List<Vote>> allUpvotes(int entryId) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Entries/Upvoters/entry_id/")
                .apiParam("entry_id", String.valueOf(entryId))
                .build(), new TypeReference<List<Vote>>() {
        });
    }

    /**
     * @param commentId comment's id.
     * @return possible {@link EntryComment}
     */
    public Chain<Optional<EntryComment>> entryComment(int commentId) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Entries/Comment/comment_id/")
                .apiParam("comment_id", String.valueOf(commentId))
                .build(), new TypeReference<Optional<EntryComment>>() {
        });
    }

    /**
     * @param entryId    entry's id.
     * @param newComment new comment to be added.
     * @return Added comment.
     * @throws ArchivalContentException when non-existing id is provided.
     */
    public Chain<EntryComment> addEntryComment(int entryId, NewComment newComment) {
        WykopRequest.Builder requestBuilder = new WykopRequest.Builder()
                .url(WYKOP_URL + "/Entries/CommentAdd/entry_id/")
                .apiParam("entry_id", String.valueOf(entryId));
        newComment.body().ifPresent(body -> requestBuilder.postParam("body", body));
        newComment.urlEmbed().ifPresent(url -> requestBuilder.postParam("embed", url));
        Optional<File> fileEmbed = newComment.fileEmbed();
        if (fileEmbed.isPresent()) {
            Optional<String> shownFileName = newComment.shownFileName();
            if (shownFileName.isPresent()) {
                requestBuilder.file(fileEmbed.get(), shownFileName.get());
            } else {
                requestBuilder.file(fileEmbed.get());
            }
        }
        return new Chain<>(requestBuilder.build(), EntryComment.class);
    }

    /**
     * @param commentId  comment's id.
     * @param newComment new comment to be changed.
     * @return Changed comment.
     * @throws UnableToModifyEntryException when provided commentId does not belong to user's comment.
     * @throws ArchivalContentException     when provided commentId does not exist.
     */
    public Chain<EntryComment> editEntryComment(int commentId, NewComment newComment) {
        WykopRequest.Builder requestBuilder = new WykopRequest.Builder()
                .url(WYKOP_URL + "/Entries/CommentEdit/comment_id/")
                .apiParam("comment_id", String.valueOf(commentId));
        newComment.body().ifPresent(body -> requestBuilder.postParam("body", body));
        newComment.urlEmbed().ifPresent(url -> requestBuilder.postParam("embed", url));
        Optional<File> fileEmbed = newComment.fileEmbed();
        if (fileEmbed.isPresent()) {
            Optional<String> shownFileName = newComment.shownFileName();
            if (shownFileName.isPresent()) {
                requestBuilder.file(fileEmbed.get(), shownFileName.get());
            } else {
                requestBuilder.file(fileEmbed.get());
            }
        }
        return new Chain<>(requestBuilder.build(), EntryComment.class);
    }

    /**
     * @param commentId comment's id.
     * @return Deleted comment.
     */
    public Chain<EntryComment> deleteEntryComment(int commentId) {
        return new Chain<>(new io.github.benzwreck.wykop4j.WykopRequest.Builder()
                .url(WYKOP_URL + "/Entries/CommentDelete/entry_comment_id/")
                .apiParam("entry_comment_id", String.valueOf(commentId))
                .build(), EntryComment.class);
    }

    /**
     * @param commentId comment's id.
     * @return nothing.
     */
    public Chain<Void> entryCommentVoteUp(int commentId) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Entries/CommentVoteUp/id/")
                .apiParam("id", String.valueOf(commentId))
                .build(), Void.class);
    }

    /**
     * @param commentId comment's id.
     * @return nothing.
     */
    public Chain<Void> entryCommentVoteRemove(int commentId) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Entries/CommentVoteRemove/id/")
                .apiParam("id", String.valueOf(commentId))
                .build(), Void.class);
    }

    /**
     * @return First page of observed comments.
     */
    public Chain<List<EntryComment>> observedComments() {
        return observedComments(Page.of(1));
    }

    /**
     * @param page page number.
     * @return Given page of observed comments. When the list is over, returns empty one.
     */
    public Chain<List<EntryComment>> observedComments(Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Entries/ObservedComments/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .build(), new TypeReference<List<EntryComment>>() {
        });
    }

    /**
     * @param entryId entry's id.
     * @return true - entry favorite toggled on; false - entry favorite toggled off.
     */
    public Chain<Boolean> toggleEntryFavorite(int entryId) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Entries/Favorite/entry/")
                .apiParam("entry", String.valueOf(entryId))
                .build(), Boolean.class);
    }

    /**
     * @param entryId id of entry with survey.
     * @param answerId answer's id.
     * @return Survey with answered question.
     * @throws ArchivalContentException when non-existent entryId is provided.
     * @throws NiceTryException when non-existent answerId is provided.
     */
    public Chain<Survey> answerSurvey(int entryId, int answerId) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Entries/SurveyVote/entry/answer/")
                .apiParam("entry", String.valueOf(entryId))
                .apiParam("answer", String.valueOf(answerId))
                .build(), Survey.class);
    }

    public static final class Builder {
        private UserCredentials userCredentials;
        private ApplicationCredentials applicationCredentials;

        public Builder() {
        }

        public Builder withUserCredentials(UserCredentials userCredentials) {
            this.userCredentials = userCredentials;
            return this;
        }

        public Builder withApplicationCredentials(ApplicationCredentials applicationCredentials) {
            this.applicationCredentials = applicationCredentials;
            return this;
        }

        public WykopClient build() {
            WykopHttpClient client = new WykopHttpClient(userCredentials, applicationCredentials);
            WykopObjectMapper wykopObjectMapper = new WykopObjectMapper();
            return new WykopClient(client, wykopObjectMapper);
        }
    }

    public class Chain<T> {
        private final WykopRequest wykopRequest;
        private Class<T> clazz;
        private TypeReference<T> typeReference;

        Chain(WykopRequest wykopRequest, Class<T> clazz) {
            this.wykopRequest = wykopRequest;
            this.clazz = clazz;
        }

        Chain(WykopRequest wykopRequest, TypeReference<T> typeReference) {
            this.wykopRequest = wykopRequest;
            this.typeReference = typeReference;
        }

        public T execute() {
            String response = WykopClient.this.client.execute(wykopRequest);
            if (clazz != null) {
                return WykopClient.this.wykopObjectMapper.map(response, clazz);
            } else {
                return WykopClient.this.wykopObjectMapper.map(response, typeReference);
            }

        }

    }

}
