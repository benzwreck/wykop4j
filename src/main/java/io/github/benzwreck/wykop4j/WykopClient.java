package io.github.benzwreck.wykop4j;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.benzwreck.wykop4j.conversations.ConversationInfo;
import io.github.benzwreck.wykop4j.conversations.Message;
import io.github.benzwreck.wykop4j.conversations.NewMessage;
import io.github.benzwreck.wykop4j.entries.Entry;
import io.github.benzwreck.wykop4j.entries.EntryComment;
import io.github.benzwreck.wykop4j.entries.NewEntry;
import io.github.benzwreck.wykop4j.entries.NewEntryComment;
import io.github.benzwreck.wykop4j.entries.Period;
import io.github.benzwreck.wykop4j.entries.Survey;
import io.github.benzwreck.wykop4j.exceptions.ArchivalContentException;
import io.github.benzwreck.wykop4j.exceptions.BodyContainsOnlyPmException;
import io.github.benzwreck.wykop4j.exceptions.CannotEditCommentsWithAnswerException;
import io.github.benzwreck.wykop4j.exceptions.CannotReplyOnDeletedObjectsException;
import io.github.benzwreck.wykop4j.exceptions.CommentDoesNotExistException;
import io.github.benzwreck.wykop4j.exceptions.InvalidValueException;
import io.github.benzwreck.wykop4j.exceptions.LinkAlreadyExistsException;
import io.github.benzwreck.wykop4j.exceptions.LinkCommentNotExistException;
import io.github.benzwreck.wykop4j.exceptions.NiceTryException;
import io.github.benzwreck.wykop4j.exceptions.UnableToDeleteCommentException;
import io.github.benzwreck.wykop4j.exceptions.UnableToModifyEntryException;
import io.github.benzwreck.wykop4j.exceptions.UserBlockedByAnotherUserException;
import io.github.benzwreck.wykop4j.exceptions.UserCannotObserveThemselfException;
import io.github.benzwreck.wykop4j.links.HitsOption;
import io.github.benzwreck.wykop4j.links.Link;
import io.github.benzwreck.wykop4j.links.LinkComment;
import io.github.benzwreck.wykop4j.links.LinkCommentVoteData;
import io.github.benzwreck.wykop4j.links.LinkCommentsSorting;
import io.github.benzwreck.wykop4j.links.LinkDraft;
import io.github.benzwreck.wykop4j.links.LinkImage;
import io.github.benzwreck.wykop4j.links.LinkVoteData;
import io.github.benzwreck.wykop4j.links.LinkWithComments;
import io.github.benzwreck.wykop4j.links.NewLink;
import io.github.benzwreck.wykop4j.links.NewLinkComment;
import io.github.benzwreck.wykop4j.links.NewRelatedLink;
import io.github.benzwreck.wykop4j.links.RelatedLink;
import io.github.benzwreck.wykop4j.links.RelatedLinkVoteData;
import io.github.benzwreck.wykop4j.links.VoteDownReason;
import io.github.benzwreck.wykop4j.login.WykopConnectLoginData;
import io.github.benzwreck.wykop4j.notifications.Notification;
import io.github.benzwreck.wykop4j.profiles.ActionType;
import io.github.benzwreck.wykop4j.profiles.Actions;
import io.github.benzwreck.wykop4j.profiles.Badge;
import io.github.benzwreck.wykop4j.profiles.FullProfile;
import io.github.benzwreck.wykop4j.profiles.InteractionStatus;
import io.github.benzwreck.wykop4j.profiles.SimpleProfile;
import io.github.benzwreck.wykop4j.search.EntrySearchQuery;
import io.github.benzwreck.wykop4j.search.LinkSearchQuery;
import io.github.benzwreck.wykop4j.shared.Vote;
import io.github.benzwreck.wykop4j.suggest.TagSuggestion;
import io.github.benzwreck.wykop4j.terms.Terms;

import java.net.URL;
import java.time.DateTimeException;
import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Optional;

public class WykopClient {
    private final static String WYKOP_URL = "https://a2.wykop.pl";
    private final WykopHttpClient client;
    private final WykopObjectMapper wykopObjectMapper;
    private final WykopConnect wykopConnect;

    WykopClient(WykopHttpClient wykopHttpClient, WykopObjectMapper wykopObjectMapper, WykopConnect wykopConnect) {
        this.client = wykopHttpClient;
        this.wykopObjectMapper = wykopObjectMapper;
        this.wykopConnect = wykopConnect;
    }

    //Entries

    /**
     * Fetches first page of the latest Microblog's Entries.
     *
     * @return list of entries.
     */
    public Chain<List<Entry>> getEntriesStream() {
        return getEntriesStream(Page.of(1));
    }

    /**
     * Fetches given page of the latest Microblog's Entries.
     *
     * @param page available pages for Entries' Stream. Has to be 1 or 2.
     * @return list of entries.
     * @throws IllegalArgumentException if page is different than 1 or 2.
     */
    public Chain<List<Entry>> getEntriesStream(Page page) {
        if (page.value() < 0 || page.value() > 2)
            throw new IllegalArgumentException("Page" + page + "is forbidden. Only page 1 and page 2 are possible to fetch.");
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Entries/Stream/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .build(), new TypeReference<>() {
        });
    }

    /**
     * Fetches microblog's Entries where first {@link Entry} id equals first one after entryId.
     *
     * @param entryId id from where we start counting except entryId
     * @return list of entries.
     */
    public Chain<List<Entry>> getEntriesStream(int entryId) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Entries/Stream/firstid/int/")
                .namedParam("firstid", String.valueOf(entryId))
                .build(), new TypeReference<>() {
        });
    }

    /**
     * Fetches entry with given id.
     *
     * @param id id of the {@link Entry} you are looking for.
     * @return possible {@link Entry}
     */
    public Chain<Optional<Entry>> getEntry(int id) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Entries/Entry/entry/")
                .apiParam("entry", String.valueOf(id))
                .build(), new TypeReference<>() {
        });
    }

    /**
     * Fetches first page of 12 hours hot entries.
     *
     * @return list of entries.
     */
    public Chain<List<Entry>> getHotEntries() {
        return getHotEntries(Page.of(1), Period.TWELVE_HOURS);
    }

    /**
     * Fetches given page of Hot Entries from last 12 hours.
     *
     * @param page given hot entries page. Has to be from 1 to 20.
     * @return list of entries.
     * @throws IllegalArgumentException if page is not from 1 to 20.
     */
    public Chain<List<Entry>> getHotEntries(Page page) {
        return getHotEntries(page, Period.TWELVE_HOURS);
    }

    /**
     * Fetches first page of the Hot Entries for a given period.
     *
     * @param period available pages for Entries' Hot
     * @return list of entries.
     */
    public Chain<List<Entry>> getHotEntries(Period period) {
        return getHotEntries(Page.of(1), period);
    }

    /**
     * Fetches Hot Entries for a given page and period.
     *
     * @param page   given hot entries page. Has to be between 1 and 20. Throws exception otherwise.
     * @param period available pages for Entries' Hot
     * @return list of entries.
     * @throws IllegalArgumentException if page is not from 1 to 20.
     */
    public Chain<List<Entry>> getHotEntries(Page page, Period period) {
        if (page.value() < 0 || page.value() > 20)
            throw new IllegalArgumentException("Page" + page + "is forbidden. Only pages from 1 to 20 are possible to fetch.");
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Entries/Hot/page/int/period/int/")
                .namedParam("page", String.valueOf(page.value()))
                .namedParam("period", String.valueOf(period.value()))
                .build(), new TypeReference<>() {
        });
    }

    /**
     * Fetches first page of Active Entries.
     *
     * @return list of entries.
     */
    public Chain<List<Entry>> getActiveEntries() {
        return getActiveEntries(Page.of(1));
    }

    /**
     * Fetches Active Entries from given page.
     *
     * @param page given active entries page.
     * @return list of entries.
     */
    public Chain<List<Entry>> getActiveEntries(Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Entries/Active/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .build(), new TypeReference<>() {
        });
    }

    /**
     * Fetches first page of Observed Entries.
     *
     * @return list of entries.
     */
    public Chain<List<Entry>> getObservedEntries() {
        return getObservedEntries(Page.of(1));
    }

    /**
     * Fetches Observed Entries from given page.
     *
     * @param page given observed entries page.
     * @return list of entries.
     */
    public Chain<List<Entry>> getObservedEntries(Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Entries/Observed/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .build(), new TypeReference<>() {
        }
        );
    }

    /**
     * Deletes {@link Entry} - status changes to "deleted"
     *
     * @param entryId given entry's id.
     * @return deleted entry.
     */
    public Chain<Entry> deleteEntry(int entryId) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Entries/Delete/entry_id/")
                .apiParam("entry_id", String.valueOf(entryId))
                .build(), Entry.class);
    }

    /**
     * Adds {@link Entry}
     *
     * @param newEntry new entry to be added.
     * @return added {@link Entry}
     */
    public Chain<Entry> addEntry(NewEntry newEntry) {
        WykopRequest.Builder requestBuilder = new WykopRequest.Builder()
                .url(WYKOP_URL + "/Entries/Add/")
                .postParam("adultmedia", String.valueOf(newEntry.adultOnly()));
        newEntry.body().ifPresent(body -> requestBuilder.postParam("body", body));
        newEntry.urlEmbed().ifPresent(url -> requestBuilder.postParam("embed", url));
        newEntry.fileEmbed().ifPresent(file ->
                newEntry.shownFileName().ifPresentOrElse(shownFileName ->
                                requestBuilder.file(file, shownFileName),
                        () -> requestBuilder.file(file)));
        return new Chain<>(requestBuilder.build(), Entry.class);
    }

    /**
     * Modifies {@link Entry}
     *
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
        newEntry.fileEmbed().ifPresent(file ->
                newEntry.shownFileName().ifPresentOrElse(shownFileName ->
                                requestBuilder.file(file, shownFileName),
                        () -> requestBuilder.file(file)));
        return new Chain<>(requestBuilder.build(), Entry.class);
    }

    /**
     * Votes up given {@link Entry}
     *
     * @param entryId entry's id to vote up.
     * @return nothing.
     * @throws ArchivalContentException when non-existing entryId provided.
     */
    public Chain<Void> voteUpEntry(int entryId) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Entries/VoteUp/id/")
                .apiParam("id", String.valueOf(entryId))
                .build(), Void.class);
    }

    /**
     * Removes vote from given {@link Entry}
     *
     * @param entryId entry's id to vote remove.
     * @return nothing.
     * @throws ArchivalContentException when non-existing entryId provided.
     */
    public Chain<Void> voteRemoveFromEntry(int entryId) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Entries/VoteRemove/id/")
                .apiParam("id", String.valueOf(entryId))
                .build(), Void.class);
    }

    /**
     * Fetches list of all entry {@link Vote}s
     *
     * @param entryId entry's id to fetch voters from.
     * @return list of votes.
     */
    public Chain<List<Vote>> getAllUpvotesFromEntry(int entryId) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Entries/Upvoters/entry_id/")
                .apiParam("entry_id", String.valueOf(entryId))
                .build(), new TypeReference<>() {
        });
    }

    /**
     * Fetches {@link EntryComment}
     *
     * @param commentId comment's id.
     * @return possible entry's comment.
     */
    public Chain<Optional<EntryComment>> getEntryComment(int commentId) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Entries/Comment/comment_id/")
                .apiParam("comment_id", String.valueOf(commentId))
                .build(), new TypeReference<>() {
        });
    }

    /**
     * Adds {@link NewEntryComment} to given {@link Entry}
     *
     * @param entryId         entry's id.
     * @param newEntryComment new comment to be added.
     * @return added comment.
     * @throws ArchivalContentException when non-existing id is provided.
     */
    public Chain<EntryComment> addEntryComment(int entryId, NewEntryComment newEntryComment) {
        WykopRequest.Builder requestBuilder = new WykopRequest.Builder()
                .url(WYKOP_URL + "/Entries/CommentAdd/entry_id/")
                .apiParam("entry_id", String.valueOf(entryId));
        newEntryComment.body().ifPresent(body -> requestBuilder.postParam("body", body));
        newEntryComment.urlEmbed().ifPresent(url -> requestBuilder.postParam("embed", url));
        newEntryComment.fileEmbed().ifPresent(file ->
                newEntryComment.shownFileName().ifPresentOrElse(shownFileName ->
                                requestBuilder.file(file, shownFileName),
                        () -> requestBuilder.file(file)));
        return new Chain<>(requestBuilder.build(), EntryComment.class);
    }

    /**
     * Edits entry's comment.
     *
     * @param commentId       comment's id.
     * @param newEntryComment new comment to be changed.
     * @return changed comment.
     * @throws UnableToModifyEntryException when provided commentId does not belong to user's comment.
     * @throws ArchivalContentException     when provided commentId does not exist.
     */
    public Chain<EntryComment> editEntryComment(int commentId, NewEntryComment newEntryComment) {
        WykopRequest.Builder requestBuilder = new WykopRequest.Builder()
                .url(WYKOP_URL + "/Entries/CommentEdit/comment_id/")
                .apiParam("comment_id", String.valueOf(commentId));
        newEntryComment.body().ifPresent(body -> requestBuilder.postParam("body", body));
        newEntryComment.urlEmbed().ifPresent(url -> requestBuilder.postParam("embed", url));
        newEntryComment.fileEmbed().ifPresent(file ->
                newEntryComment.shownFileName().ifPresentOrElse(shownFileName ->
                                requestBuilder.file(file, shownFileName),
                        () -> requestBuilder.file(file)));
        return new Chain<>(requestBuilder.build(), EntryComment.class);
    }

    /**
     * Deletes entry's comment.
     *
     * @param commentId comment's id.
     * @return deleted comment.
     * @throws ArchivalContentException       when provided commentId does not exist.
     * @throws UnableToDeleteCommentException when provided comment does not belong to user.
     */
    public Chain<EntryComment> deleteEntryComment(int commentId) {
        return new Chain<>(new io.github.benzwreck.wykop4j.WykopRequest.Builder()
                .url(WYKOP_URL + "/Entries/CommentDelete/entry_comment_id/")
                .apiParam("entry_comment_id", String.valueOf(commentId))
                .build(), EntryComment.class);
    }

    /**
     * Votes up entry's comment.
     *
     * @param commentId comment's id.
     * @return nothing.
     * @throws ArchivalContentException when provided commentId does not exist.
     */
    public Chain<Void> voteUpEntryComment(int commentId) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Entries/CommentVoteUp/id/")
                .apiParam("id", String.valueOf(commentId))
                .build(), Void.class);
    }

    /**
     * Removes entry's comment vote.
     *
     * @param commentId comment's id.
     * @return nothing.
     * @throws ArchivalContentException when provided commentId does not exist.
     */
    public Chain<Void> removeVoteFromEntryComment(int commentId) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Entries/CommentVoteRemove/id/")
                .apiParam("id", String.valueOf(commentId))
                .build(), Void.class);
    }

    /**
     * Fetches first page of observed comments.
     *
     * @return list of entry's comments.
     */
    public Chain<List<EntryComment>> getObservedEntryComments() {
        return getObservedEntryComments(Page.of(1));
    }

    /**
     * Fetches observed comments for a given page. When the list is over, returns empty one.
     *
     * @param page page number.
     * @return list of entry's comments.
     */
    public Chain<List<EntryComment>> getObservedEntryComments(Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Entries/ObservedComments/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .build(), new TypeReference<>() {
        });
    }

    /**
     * Toggles on/off entry favorite.
     *
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
     * Answers a survey.
     *
     * @param entryId  id of entry with survey.
     * @param answerId answer's id.
     * @return survey with answered question.
     * @throws ArchivalContentException when non-existent entryId is provided.
     * @throws NiceTryException         when non-existent answerId is provided.
     */
    public Chain<Survey> answerSurvey(int entryId, int answerId) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Entries/SurveyVote/entry/answer/")
                .apiParam("entry", String.valueOf(entryId))
                .apiParam("answer", String.valueOf(answerId))
                .build(), Survey.class);
    }

    /**
     * Toggles on/off entry's comment favorite.
     *
     * @param entryCommentId id of entry's comment.
     * @return true - comment favorite toggled on; false - comment favorite toggled off.
     * @throws CommentDoesNotExistException when such comment does not exist.
     */
    public Chain<Boolean> toggleEntryCommentFavorite(int entryCommentId) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Entries/CommentFavorite/comment/")
                .apiParam("comment", String.valueOf(entryCommentId))
                .build(), Boolean.class);
    }

    //Link hits

    /**
     * Fetches link hits with given option.
     *
     * @param option type of links to retrieve.
     * @return list of chosen links.
     */
    public Chain<List<Link>> getHitLinks(HitsOption option) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Hits/" + option.value() + "/")
                .build(), new TypeReference<>() {
        });
    }

    /**
     * Fetches link hits for a given month.
     *
     * @param month month of link's date.
     * @return list of chosen links.
     * @throws DateTimeException when illegal {@link Month} value is passed.
     */
    public Chain<List<Link>> getHitLinks(Month month) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Hits/Month/year/month")
                .apiParam("year", Year.now().toString())
                .apiParam("month", String.valueOf(month.getValue()))
                .build(), new TypeReference<>() {
        });
    }

    /**
     * Fetches link hits for a given year.
     *
     * @param year year of link's date.
     * @return list of chosen links.
     */
    public Chain<List<Link>> getHitLinks(Year year) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Hits/Year/year")
                .apiParam("year", year.toString())
                .build(), new TypeReference<>() {
        });
    }

    /**
     * Fetches link hits for a given month and year.
     *
     * @param month month of link's date.
     * @param year  year of link's date.
     * @return list of chosen links.
     * @throws DateTimeException when illegal {@link Month} value is passed.
     */
    public Chain<List<Link>> getHitLinks(Month month, Year year) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Hits/Month/year/month/")
                .apiParam("year", year.toString())
                .apiParam("month", String.valueOf(month.getValue()))
                .build(), new TypeReference<>() {
        });
    }

    //Notifications

    /**
     * Fetches first page of user's directed notifications.
     *
     * @return list of notifications.
     */
    public Chain<List<Notification>> getDirectedNotifications() {
        return getDirectedNotifications(Page.of(1));
    }

    /**
     * Fetches user's directed notifications for a given page.
     *
     * @param page page you want to fetch.
     * @return list of norifications.
     */
    public Chain<List<Notification>> getDirectedNotifications(Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Notifications/Index/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .fullData(false)        //wykop api crashes otherwise - returns error html page
                .build(), new TypeReference<>() {
        });
    }

    /**
     * Fetches user's directed notifications count.
     *
     * @return number of notifications.
     */
    public Chain<Integer> getDirectedNotificationCount() {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Notifications/Count/")
                .build(), Integer.class);
    }

    /**
     * Fetches first page of user's tags notifications.
     *
     * @return list of notifications.
     */
    public Chain<List<Notification>> getTagsNotifications() {
        return getTagsNotifications(Page.of(1));
    }

    /**
     * Fetches user's tags notifications for a given page.
     *
     * @param page page you want to fetch.
     * @return list of notifications.
     */
    public Chain<List<Notification>> getTagsNotifications(Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Notifications/HashTags/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .fullData(false)
                .build(), new TypeReference<>() {
        });
    }

    /**
     * Fetches user's tags notifications count.
     *
     * @return number of notifications.
     */
    public Chain<Integer> getTagsNotificationCount() {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Notifications/HashTagsCount/")
                .build(), Integer.class);
    }

    /**
     * Fetches fist page of combined direct notifications and tags notifications.
     *
     * @return list of notifications.
     */
    public Chain<List<Notification>> getAllNotifications() {
        return getAllNotifications(Page.of(1));
    }

    /**
     * Fetches a given page of combined direct notifications and tags notifications.
     *
     * @param page page you want to fetch.
     * @return list of notifications.
     */
    public Chain<List<Notification>> getAllNotifications(Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Notifications/Total/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .fullData(false)
                .build(), new TypeReference<>() {
        });
    }

    /**
     * Fetches combined direct notifications and tags notifications count.
     *
     * @return total notification count.
     */
    public Chain<Integer> getAllNotificationCount() {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Notifications/TotalCount/")
                .build(), Integer.class);
    }

    /**
     * Reads all user's notifications.
     *
     * @return nothing.
     */
    public Chain<Void> readAllNotifications() {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Notifications/ReadAllNotifications/")
                .build(), Void.class);
    }

    /**
     * Reads all user's directed notifications.
     *
     * @return nothing.
     */
    public Chain<Void> readAllDirectedNotifications() {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Notifications/ReadDirectedNotifications/")
                .build(), Void.class);
    }

    /**
     * Reads all user's tags notifications.
     *
     * @return nothing.
     */
    public Chain<Void> readAllTagsNotifications() {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Notifications/ReadHashTagsNotifications/")
                .build(), Void.class);
    }

    /**
     * Marks a notification as read.
     *
     * @param notificationId notification's id.
     * @return nothing.
     */
    public Chain<Void> markNotificationAsRead(long notificationId) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Notifications/MarkAsRead/notification/")
                .apiParam("notification", String.valueOf(notificationId))
                .build(), Void.class);
    }

    //Pm

    /**
     * Fetches list of conversations.
     *
     * @return list of conversation's basic information.
     */
    public Chain<List<ConversationInfo>> getConversationsList() {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Pm/ConversationsList/")
                .fullData(false)
                .build(), new TypeReference<>() {
        });
    }

    /**
     * Fetches a conversation with given user.
     *
     * @param login user's login.
     * @return list of messages.
     */
    public Chain<List<Message>> getConversation(String login) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Pm/Conversation/receiver/")
                .apiParam("receiver", login)
                .build(), new TypeReference<>() {
        });
    }

    /**
     * Sends a message to a given user.
     *
     * @param login      user's login.
     * @param newMessage message you'd like to send.
     * @return sent message.
     * @throws UserBlockedByAnotherUserException when you are blocked by another user.
     */
    public Chain<Message> sendMessage(String login, NewMessage newMessage) {
        WykopRequest.Builder builder = new WykopRequest.Builder()
                .url(WYKOP_URL + "/Pm/SendMessage/receiver/")
                .postParam("adultmedia", String.valueOf(newMessage.adultOnly()))
                .apiParam("receiver", login);
        newMessage.body().ifPresent(body -> builder.postParam("body", body));
        newMessage.urlEmbed().ifPresent(urlEmbed -> builder.postParam("embed", urlEmbed));
        newMessage.fileEmbed().ifPresent(file ->
                newMessage.shownFileName().ifPresentOrElse(shownFileName ->
                                builder.file(file, shownFileName),
                        () -> builder.file(file)));
        return new Chain<>(builder.build(), Message.class);
    }

    /**
     * Deletes a conversation with a given user.
     *
     * @param login user's login.
     * @return true - deleted.
     */
    public Chain<Boolean> deleteConversation(String login) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Pm/DeleteConversation/receiver_name/")
                .apiParam("receiver_name", login)
                .build(), Boolean.class);
    }

    //Profile

    /**
     * Fetches user's profile.
     *
     * @param login user's login.
     * @return possible user's profile.
     */
    public Chain<Optional<FullProfile>> getProfile(String login) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Profiles/Index/login/")
                .apiParam("login", login)
                .build(), new TypeReference<>() {
        });
    }

    /**
     * Fetches all user's actions.
     *
     * @param login user's login.
     * @return user's actions.
     */
    public Chain<Actions> getProfileActions(String login) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Profiles/Actions/login/")
                .apiParam("login", login)
                .build(), Actions.class);
    }

    /**
     * Fetches first page of links added by user.
     *
     * @param login user's login.
     * @return list of links.
     */
    public Chain<List<Link>> getProfileAddedLinks(String login) {
        return getProfileAddedLinks(login, Page.of(1));
    }

    /**
     * Fetches a given page of links added by user.
     *
     * @param login user's login.
     * @param page  page.
     * @return list of links.
     */
    public Chain<List<Link>> getProfileAddedLinks(String login, Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Profiles/Added/login/page/int/")
                .apiParam("login", login)
                .namedParam("page", String.valueOf(page.value()))
                .build(), new TypeReference<>() {
        });
    }

    /**
     * Fetches first page of links commented by user.
     *
     * @param login user's login.
     * @return list of links.
     */
    public Chain<List<Link>> getProfileCommentedLinks(String login) {
        return getProfileCommentedLinks(login, Page.of(1));
    }

    /**
     * Fetches given page of links commented by user.
     *
     * @param login user's login.
     * @param page  page.
     * @return list of links.
     */
    public Chain<List<Link>> getProfileCommentedLinks(String login, Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Profiles/Commented/login/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .apiParam("login", login)
                .build(), new TypeReference<>() {
        });
    }

    /**
     * Fetches first page of links' comments added by user.
     *
     * @param login user's login.
     * @return list of link's comments.
     */
    public Chain<List<LinkComment>> getProfileLinksComments(String login) {
        return getProfileLinksComments(login, Page.of(1));
    }

    /**
     * Fetches given page of links' comments added by user.
     *
     * @param login user's login.
     * @param page  page.
     * @return list of link's comments.
     */
    public Chain<List<LinkComment>> getProfileLinksComments(String login, Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Profiles/Comments/login/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .apiParam("login", login)
                .build(), new TypeReference<>() {
        });
    }

    /**
     * Fetches first page of links published by user.
     *
     * @param login user's login.
     * @return list of links.
     */
    public Chain<List<Link>> getProfilePublishedLinks(String login) {
        return getProfilePublishedLinks(login, Page.of(1));
    }

    /**
     * Fetches a given page of links published by user.
     *
     * @param login user's login.
     * @param page  page.
     * @return list of links.
     */
    public Chain<List<Link>> getProfilePublishedLinks(String login, Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Profiles/Published/login/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .apiParam("login", login)
                .build(), new TypeReference<>() {
        });
    }

    /**
     * Fetches first page of entries added by user.
     *
     * @param login user's login.
     * @return list of entries.
     */
    public Chain<List<Entry>> getProfileEntries(String login) {
        return getProfileEntries(login, Page.of(1));
    }

    /**
     * Fetches a given page of entries added by user.
     *
     * @param login user's login.
     * @param page  page.
     * @return list of entries.
     */
    public Chain<List<Entry>> getProfileEntries(String login, Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Profiles/Entries/login/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .apiParam("login", login)
                .build(), new TypeReference<>() {
        });
    }

    /**
     * Fetches first page of entries commented by user.
     *
     * @param login user's login.
     * @return list of entries.
     */
    public Chain<List<Entry>> getProfileCommentedEntries(String login) {
        return getProfileCommentedEntries(login, Page.of(1));
    }

    /**
     * Fetches a given page of entries commented by user.
     *
     * @param login user's login.
     * @param page  page.
     * @return list of entries.
     */
    public Chain<List<Entry>> getProfileCommentedEntries(String login, Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Profiles/CommentedEntries/login/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .apiParam("login", login)
                .build(), new TypeReference<>() {
        });
    }

    /**
     * Fetches first page of entries' comments by user.
     *
     * @param login user's login.
     * @return list of entry's comments.
     */
    public Chain<List<EntryComment>> getProfileEntriesComments(String login) {
        return getProfileEntriesComments(login, Page.of(1));
    }

    /**
     * Fetches a given page of entries' comments by user.
     *
     * @param login user's login.
     * @param page  page.
     * @return list of entry's comments.
     */
    public Chain<List<EntryComment>> getProfileEntriesComments(String login, Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Profiles/EntriesComments/login/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .apiParam("login", login)
                .build(), new TypeReference<>() {
        });
    }

    /**
     * Fetches first page of related links added by user.
     *
     * @param login user's login.
     * @return list of links.
     */
    public Chain<List<Link>> getProfileRelatedLinks(String login) {
        return getProfileRelatedLinks(login, Page.of(1));
    }

    /**
     * Fetches a given page of related links added by user.
     *
     * @param login user's login.
     * @param page  page.
     * @return list of links.
     */
    public Chain<List<Link>> getProfileRelatedLinks(String login, Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Profiles/EntriesComments/login/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .apiParam("login", login)
                .build(), new TypeReference<>() {
        });
    }

    /**
     * Fetches first page of users following particular user.
     *
     * @param login user's login.
     * @return list of profiles.
     */
    public Chain<List<FullProfile>> getProfileFollowers(String login) {
        return getProfileFollowers(login, Page.of(1));
    }

    /**
     * Fetches a given page of users following particular user.
     *
     * @param login user's login.
     * @param page  page.
     * @return list of profiles.
     */
    public Chain<List<FullProfile>> getProfileFollowers(String login, Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Profiles/Followers/login/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .apiParam("login", login)
                .build(), new TypeReference<>() {
        });
    }

    /**
     * Fetches first page of users being followed by particular user.
     *
     * @param login user's login.
     * @return list of profiles.
     */
    public Chain<List<FullProfile>> getProfileFollowed(String login) {
        return getProfileFollowed(login, Page.of(1));
    }

    /**
     * Fetches a given page of users being followed by particular user.
     *
     * @param login user's login.
     * @param page  page.
     * @return list of profiles.
     */
    public Chain<List<FullProfile>> getProfileFollowed(String login, Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Profiles/Followed/login/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .apiParam("login", login)
                .build(), new TypeReference<>() {
        });
    }

    /**
     * Fetches first page of user's badges.
     *
     * @param login user's login.
     * @return list of badges.
     */
    public Chain<List<Badge>> getProfileBadges(String login) {
        return getProfileBadges(login, Page.of(1));
    }

    /**
     * Fetches a given page of user's badges.
     *
     * @param login user's login.
     * @param page  page.
     * @return list of badges.
     */
    public Chain<List<Badge>> getProfileBadges(String login, Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Profiles/Badges/login/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .apiParam("login", login)
                .build(), new TypeReference<>() {
        });
    }

    /**
     * Fetches first page of user's digged links.
     *
     * @param login user's login.
     * @return list of links.
     */
    public Chain<List<Link>> getProfileDiggedLinks(String login) {
        return getProfileDiggedLinks(login, Page.of(1));
    }

    /**
     * Fetches a given page of user's digged links.
     *
     * @param login user's login.
     * @param page  page.
     * @return list of links.
     */
    public Chain<List<Link>> getProfileDiggedLinks(String login, Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Profiles/Digged/login/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .apiParam("login", login)
                .build(), new TypeReference<>() {
        });
    }

    /**
     * Fetches first page of user's buried links.
     *
     * @param login user's login.
     * @return list of links.
     */
    public Chain<List<Link>> getProfileBuriedLinks(String login) {
        return getProfileBuriedLinks(login, Page.of(1));
    }

    /**
     * Fetches a given page of user's buried links.
     *
     * @param login user's login.
     * @param page  page.
     * @return list of links.
     */
    public Chain<List<Link>> getProfileBuriedLinks(String login, Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Profiles/Buried/login/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .apiParam("login", login)
                .build(), new TypeReference<>() {
        });
    }

    /**
     * Fetches first page of profiles from user rank.
     *
     * @return list of profiles.
     */
    public Chain<List<FullProfile>> getProfileRanking() {
        return getProfileRanking(Page.of(1));
    }

    /**
     * Fetches a given page of profiles from user rank.
     *
     * @param page page.
     * @return list of profiles.
     */
    public Chain<List<FullProfile>> getProfileRanking(Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Profiles/Rank/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .build(), new TypeReference<>() {
        });
    }

    /**
     * Starts observing a user.
     *
     * @param login user's login.
     * @return interaction status - being observed or blocked.
     * @throws UserCannotObserveThemselfException when you try to observe yourself.
     */
    public Chain<InteractionStatus> observeUser(String login) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Profiles/Observe/login/")
                .apiParam("login", login)
                .build(), InteractionStatus.class);
    }

    /**
     * Stops observing a user.
     *
     * @param login user's login.
     * @return interaction status - being observed or blocked.
     * @throws UserCannotObserveThemselfException when you try to observe yourself.
     */
    public Chain<InteractionStatus> unobserveUser(String login) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Profiles/UnObserve/login/")
                .apiParam("login", login)
                .build(), InteractionStatus.class);
    }

    /**
     * Starts blocking a user.
     *
     * @param login user's login.
     * @return interaction status - being observed or blocked.
     * @throws UserCannotObserveThemselfException when you try to observe yourself.
     */
    public Chain<InteractionStatus> blockUser(String login) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Profiles/Block/login/")
                .apiParam("login", login)
                .build(), InteractionStatus.class);
    }

    /**
     * Stops blocking a user.
     *
     * @param login user's login.
     * @return interaction status - being observed or blocked.
     * @throws UserCannotObserveThemselfException when you try to observe yourself.
     */
    public Chain<InteractionStatus> unblockUser(String login) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Profiles/UnBlock/login/")
                .apiParam("login", login)
                .build(), InteractionStatus.class);
    }

    // Terms

    /**
     * Fetches Wykop Terms of Use.
     *
     * @return terms of use.
     */
    public Chain<Terms> getTerms() {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Terms/Index/")
                .build(), Terms.class);
    }

    /**
     * Confirms Wykop Terms of Use.
     *
     * @return confirmation terms status.
     */
    public Chain<Boolean> confirmTerms() {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Terms/Confirm/")
                .build(), Boolean.class);
    }

    // Tags

    /**
     * Fetches first page of actions.
     *
     * @param tag name of the tag, either with or without '#'.
     * @return actions.
     */
    public Chain<Actions> getTagActions(String tag) {
        return getTagActions(tag, Page.of(1));
    }

    /**
     * Fetches a given page of actions.
     *
     * @param tag  name of the tag, either with or without '#'.
     * @param page page.
     * @return actions.
     */
    public Chain<Actions> getTagActions(String tag, Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Tags/Index/tag/page/int/")
                .apiParam("tag", tag)
                .namedParam("page", String.valueOf(page.value()))
                .build(), Actions.class);
    }

    /**
     * Fetches a first page of list of links.
     *
     * @param tag name of the tag, either with or without '#'.
     * @return list of links.
     */
    public Chain<List<Link>> getTagLinks(String tag) {
        return getTagLinks(tag, Page.of(1));
    }

    /**
     * Fetches a given page of list of links.
     *
     * @param tag  name of the tag, either with or without '#'.
     * @param page page.
     * @return list of links.
     */
    public Chain<List<Link>> getTagLinks(String tag, Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Tags/Links/tag/page/int/")
                .apiParam("tag", tag)
                .namedParam("page", String.valueOf(page.value()))
                .build(), new TypeReference<>() {
        });
    }

    /**
     * Fetches first page of list of entries.
     *
     * @param tag name of the tag, either with or without '#'.
     * @return list of entries.
     */
    public Chain<List<Entry>> getTagEntries(String tag) {
        return getTagEntries(tag, Page.of(1));
    }

    /**
     * Fetches a given page of list of entries.
     *
     * @param tag  name of the tag, either with or without '#'.
     * @param page page.
     * @return list of entries.
     */
    public Chain<List<Entry>> getTagEntries(String tag, Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Tags/Entries/tag/page/int/")
                .apiParam("tag", tag)
                .namedParam("page", String.valueOf(page.value()))
                .build(), new TypeReference<>() {
        });
    }

    /**
     * Starts observing a tag.
     *
     * @param tag tag's name.
     * @return interaction status - being observed or blocked.
     */
    public Chain<InteractionStatus> observeTag(String tag) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Tags/Observe/tag/")
                .apiParam("tag", tag)
                .build(), InteractionStatus.class);
    }

    /**
     * Stops observing a tag.
     *
     * @param tag tag's name.
     * @return interaction status - being observed or blocked.
     */
    public Chain<InteractionStatus> unobserveTag(String tag) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Tags/Unobserve/tag/")
                .apiParam("tag", tag)
                .build(), InteractionStatus.class);
    }

    /**
     * Starts blocking a tag.
     *
     * @param tag tag's name.
     * @return interaction status - being observed or blocked.
     */
    public Chain<InteractionStatus> blockTag(String tag) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Tags/Block/tag/")
                .apiParam("tag", tag)
                .build(), InteractionStatus.class);
    }

    /**
     * Stops blocking a tag.
     *
     * @param tag tag's name.
     * @return interaction status - being observed or blocked.
     */
    public Chain<InteractionStatus> unblockTag(String tag) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Tags/Unblock/tag/")
                .apiParam("tag", tag)
                .build(), InteractionStatus.class);
    }

    /**
     * Enables tag notifications.
     *
     * @param tag tag's name.
     * @return nothing.
     * @throws NiceTryException when you try to enable notification on not observed tag.
     */
    public Chain<Void> enableTagNotifications(String tag) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Tags/Notify/tag/")
                .apiParam("tag", tag)
                .build(), Void.class);
    }

    /**
     * Disables tag notifications.
     *
     * @param tag tag's name.
     * @return nothing.
     * @throws NiceTryException when you try to disable notification on not observed tag.
     */
    public Chain<Void> disableTagNotifications(String tag) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Tags/Dontnotify/tag/")
                .apiParam("tag", tag)
                .build(), Void.class);
    }

    // Suggest

    /**
     * Fetches a tag suggestions.
     *
     * @param tag tag's name.
     * @return list of tag suggestions.
     */
    public Chain<List<TagSuggestion>> suggestTags(String tag) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Suggest/Tags/tag/")
                .apiParam("tag", tag)
                .build(), new TypeReference<>() {
        });
    }

    /**
     * Fetches a user suggestions.
     *
     * @param login user's login.
     * @return list of user suggestions.
     */
    public Chain<List<SimpleProfile>> suggestUsers(String login) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Suggest/Users/login/")
                .apiParam("login", login)
                .build(), new TypeReference<>() {
        });
    }

    // Mywykop

    /**
     * Fetches first page of actions from observed users and tags from MyWykop.
     *
     * @return actions.
     */
    public Chain<Actions> getMyWykopIndexActions() {
        return getMyWykopIndexActions(Page.of(1));
    }

    /**
     * Fetches a given page of actions from observed users and tags from MyWykop.
     *
     * @param page page.
     * @return actions.
     */
    public Chain<Actions> getMyWykopIndexActions(Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Mywykop/Index/type/string/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .build(), Actions.class);
    }

    /**
     * Fetches first page of actions with {@link ActionType} from observed users and tags from MyWykop.
     *
     * @param type type of returning value.
     * @return actions.
     */
    public Chain<Actions> getMyWykopIndexActions(ActionType type) {
        return getMyWykopIndexActions(type, Page.of(1));
    }

    /**
     * Fetches a given page of actions with {@link ActionType} from observed users and tags from MyWykop.
     *
     * @param type type of returning value.
     * @param page page.
     * @return actions.
     */
    public Chain<Actions> getMyWykopIndexActions(ActionType type, Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Mywykop/Index/type/string/page/int/")
                .namedParam("type", type.value())
                .namedParam("page", String.valueOf(page.value()))
                .build(), Actions.class);
    }

    /**
     * Fetches first page of actions from MyWykop's observed tags.
     *
     * @return actions.
     */
    public Chain<Actions> getMyWykopTagsActions() {
        return getMyWykopTagsActions(Page.of(1));
    }

    /**
     * Fetches a given page of actions from MyWykop's observed tags.
     *
     * @param page page.
     * @return actions.
     */
    public Chain<Actions> getMyWykopTagsActions(Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Mywykop/Tags/type/string/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .build(), Actions.class);
    }

    /**
     * Fetches first page of actions with {@link ActionType} from MyWykop's observed tags.
     *
     * @param type type of returning value.
     * @return actions.
     */
    public Chain<Actions> getMyWykopTagsActions(ActionType type) {
        return getMyWykopIndexActions(type, Page.of(1));
    }

    /**
     * Fetches a given page of actions with {@link ActionType} from MyWykop's observed tags.
     *
     * @param type type of returning value.
     * @param page page.
     * @return actions.
     */
    public Chain<Actions> getMyWykopTagsActions(ActionType type, Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Mywykop/Tags/type/string/page/int/")
                .namedParam("type", type.value())
                .namedParam("page", String.valueOf(page.value()))
                .build(), Actions.class);
    }

    /**
     * Fetches first page of actions from MyWykop's observed users.
     *
     * @return actions.
     */
    public Chain<Actions> getMyWykopUsersActions() {
        return getMyWykopUsersActions(Page.of(1));
    }

    /**
     * Fetches a given page of actions from MyWykop's observed users.
     *
     * @param page page.
     * @return actions.
     */
    public Chain<Actions> getMyWykopUsersActions(Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Mywykop/Users/type/string/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .build(), Actions.class);
    }

    /**
     * Fetches first page of actions with {@link ActionType} from MyWykop's observed users.
     *
     * @param type type of returning value.
     * @return actions.
     */
    public Chain<Actions> getMyWykopUsersActions(ActionType type) {
        return getMyWykopUsersActions(type, Page.of(1));
    }

    /**
     * Fetches a given page of actions with {@link ActionType} from MyWykop's observed users.
     *
     * @param type type of returning value.
     * @param page page.
     * @return actions.
     */
    public Chain<Actions> getMyWykopUsersActions(ActionType type, Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Mywykop/Users/type/string/page/int/")
                .namedParam("type", type.value())
                .namedParam("page", String.valueOf(page.value()))
                .build(), Actions.class);
    }

    /**
     * Fetches entries from observed users and tags.
     *
     * @return list of entries.
     */
    public Chain<List<Entry>> getMyWykopEntries() {
        return getMyWykopEntries(Page.of(1));
    }

    /**
     * Fetches entries from observed users and tags.
     *
     * @param page page.
     * @return list of entries.
     */
    public Chain<List<Entry>> getMyWykopEntries(Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Mywykop/Entries/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .build(), new TypeReference<>() {
        });
    }

    /**
     * Fetches links from observed users and tags.
     *
     * @return list of links.
     */
    public Chain<List<Link>> getMyWykopLinks() {
        return getMyWykopLinks(Page.of(1));
    }

    /**
     * Fetches links from observed users and tags.
     *
     * @param page page.
     * @return list of links.
     */
    public Chain<List<Link>> getMyWykopLinks(Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Mywykop/Links/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .build(), new TypeReference<>() {
        });
    }

    // Search

    /**
     * Fetches first page of search results for a given phrase.
     *
     * @param phrase search phrase.
     * @return list of links
     */
    public Chain<List<Link>> searchLinks(String phrase) {
        return searchLinks(phrase, Page.of(1));
    }

    /**
     * Fetches a given page of search results for a given phrase.
     *
     * @param phrase search phrase.
     * @param page   page.
     * @return list of links.
     */
    public Chain<List<Link>> searchLinks(String phrase, Page page) {
        return searchLinks(new LinkSearchQuery.Builder().phrase(phrase).build(), page);
    }

    /**
     * Fetches first page of search results for a given query.
     *
     * @param linkSearchQuery search query.
     * @return list of links.
     */
    public Chain<List<Link>> searchLinks(LinkSearchQuery linkSearchQuery) {
        return searchLinks(linkSearchQuery, Page.of(1));
    }

    /**
     * Fetches a given page of search results for a given query.
     *
     * @param linkSearchQuery search query.
     * @param page            page.
     * @return list of links.
     */
    public Chain<List<Link>> searchLinks(LinkSearchQuery linkSearchQuery, Page page) {
        WykopRequest.Builder builder = new WykopRequest.Builder()
                .url(WYKOP_URL + "/Search/Links/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .postParam("what", linkSearchQuery.type().value())
                .postParam("sort", linkSearchQuery.sorting().value())
                .postParam("when", linkSearchQuery.dateRange().value())
                .postParam("votes", String.valueOf(linkSearchQuery.minimumVoteCount()));
        linkSearchQuery.phrase().ifPresent(phrase -> builder.postParam("q", phrase));
        return new Chain<>(builder.build(), new TypeReference<>() {
        });
    }

    /**
     * Fetches first page of entry search results for a given phrase.
     *
     * @param phrase search phrase.
     * @return list of entries.
     */
    public Chain<List<Entry>> searchEntries(String phrase) {
        return searchEntries(phrase, Page.of(1));
    }

    /**
     * Fetches a given page of entry search results for a given phrase.
     *
     * @param phrase search phrase.
     * @param page   page.
     * @return list of entries.
     */
    public Chain<List<Entry>> searchEntries(String phrase, Page page) {
        return searchEntries(new EntrySearchQuery.Builder().phrase(phrase).build(), page);
    }

    /**
     * Fetches first page of entry search results for a given query.
     *
     * @param entrySearchQuery search query.
     * @return list of entries.
     */
    public Chain<List<Entry>> searchEntries(EntrySearchQuery entrySearchQuery) {
        return searchEntries(entrySearchQuery, Page.of(1));
    }

    /**
     * Fetches a given page of entry search results for a given query.
     *
     * @param entrySearchQuery search query.
     * @param page             page.
     * @return list of entries.
     */
    public Chain<List<Entry>> searchEntries(EntrySearchQuery entrySearchQuery, Page page) {
        WykopRequest.Builder builder = new WykopRequest.Builder()
                .url(WYKOP_URL + "/Search/Entries/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .postParam("q", entrySearchQuery.phrase())
                .postParam("when", entrySearchQuery.dateRange().value());
        return new Chain<>(builder.build(), new TypeReference<>() {
        });
    }

    /**
     * Fetches first page of profile search results for a given user.
     *
     * @param login login.
     * @return list of profiles.
     */
    public Chain<List<SimpleProfile>> searchProfiles(String login) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Search/Profiles/")
                .postParam("q", login)
                .build(), new TypeReference<>() {
        });
    }

    // AddLink

    /**
     * Prepares a link's draft which is used to add a new link.
     *
     * @param url url you'd like to create a link from
     * @return draft of the link
     * @throws LinkAlreadyExistsException when trying to create a draft and link already exists
     */
    public Chain<LinkDraft> prepareLinkDraft(String url) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Addlink/Draft/")
                .postParam("url", url)
                .build(), LinkDraft.class);
    }

    /**
     * Prepares an image for a link.
     *
     * @param key {@link LinkDraft}'s key.
     * @return possible {@link LinkImage}.
     */
    public Chain<Optional<LinkImage>> prepareLinkImage(String key) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Addlink/Images/key/string/")
                .namedParam("key", key)
                .build(), new TypeReference<>() {
        });
    }

    /**
     * Adds a new link to Wykop site.
     * This method is not properly tested - Wykop has no sandbox, all tests run on live server.
     * Hence, some exceptions may be thrown while using this method. All known exceptions are listed below.
     * I'll be glad if you create an issue on <a href="https://www.github.com/benzwreck/wykop4j">github</a> or add a new pull request.
     *
     * @param newLink link you'd like to add.
     * @return added link.
     * @throws InvalidValueException when some of the fields are missing.
     */
    public Chain<Link> addLink(NewLink newLink) {
        WykopRequest.Builder linkRequest = new WykopRequest.Builder()
                .url(WYKOP_URL + "/Addlink/Add/key/string/")
                .namedParam("key", newLink.key())
                .postParam("title", newLink.title())
                .postParam("description", newLink.description())
                .postParam("tags", newLink.tags())
                .postParam("url", newLink.url())
                .postParam("plus18", String.valueOf(newLink.isAdult()));
        newLink.photoKey().ifPresent(photoKey -> linkRequest.postParam("photo", photoKey));
        return new Chain<>(linkRequest.build(), Link.class);
    }

    // Links

    /**
     * Fetches first page of promoted links.
     *
     * @return list of links.
     */
    public Chain<List<Link>> getPromotedLinks() {
        return getPromotedLinks(Page.of(1));
    }

    /**
     * Fetches a given page of promoted links.
     *
     * @param page page.
     * @return list of links.
     */
    public Chain<List<Link>> getPromotedLinks(Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Links/Promoted/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .build(), new TypeReference<>() {
        });
    }

    /**
     * Fetches first page of upcoming links.
     *
     * @return list of links.
     */
    public Chain<List<Link>> getUpcomingLinks() {
        return getUpcomingLinks(Page.of(1));
    }

    /**
     * Fetches a given page of upcoming links.
     *
     * @param page page.
     * @return list of links.
     */
    public Chain<List<Link>> getUpcomingLinks(Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Links/Upcoming/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .build(), new TypeReference<>() {
        });
    }

    /**
     * Fetches first page of favorite links.
     *
     * @return list of links.
     */
    public Chain<List<Link>> getFavoriteLinks() {
        return getFavoriteLinks(Page.of(1));
    }

    /**
     * Fetches a given page of favorite links.
     *
     * @param page page.
     * @return list of links
     */
    public Chain<List<Link>> getFavoriteLinks(Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Links/Observed/page/int/")   //yep, it returns favorite links, not observed ones
                .namedParam("page", String.valueOf(page.value()))
                .build(), new TypeReference<>() {
        });
    }

    /**
     * Fetches a link without comments.
     *
     * @param linkId link's id.
     * @return possible link without comments.
     */
    public Chain<Optional<Link>> getLink(int linkId) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Links/Link/id/")
                .apiParam("id", String.valueOf(linkId))
                .build(), new TypeReference<>() {
        });
    }

    /**
     * Fetches a link with comments.
     *
     * @param linkId link's id.
     * @return possible link with comments.
     */
    public Chain<Optional<LinkWithComments>> getLinkWithComments(int linkId) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Links/Link/id/withcomments/true/")
                .apiParam("id", String.valueOf(linkId))
                .build(), new TypeReference<>() {
        });
    }

    /**
     * Votes up given link.
     *
     * @param linkId link's id.
     * @return link's vote data.
     * @throws ArchivalContentException when id is invalid.
     */
    public Chain<LinkVoteData> voteUpLink(int linkId) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Links/VoteUp/id/")
                .apiParam("id", String.valueOf(linkId))
                .build(), LinkVoteData.class);
    }

    /**
     * Removes vote from given link.
     *
     * @param linkId link's id.
     * @return link's vote data.
     * @throws ArchivalContentException when id is invalid.
     */
    public Chain<LinkVoteData> voteRemoveFromLink(int linkId) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Links/VoteRemove/id/")
                .apiParam("id", String.valueOf(linkId))
                .build(), LinkVoteData.class);
    }

    /**
     * Votes down given link.
     *
     * @param linkId link's id.
     * @return link's vote data.
     * @throws ArchivalContentException when id is invalid.
     */
    public Chain<LinkVoteData> voteDownLink(int linkId, VoteDownReason voteDownReason) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Links/VoteDown/id/voteType/")
                .apiParam("id", String.valueOf(linkId))
                .apiParam("voteType", String.valueOf(voteDownReason.value()))
                .build(), LinkVoteData.class);
    }

    /**
     * Fetches all upvotes for a given link.
     *
     * @param linkId link's id.
     * @return list of upvotes.
     */
    public Chain<List<Vote>> getAllUpvotesFromLink(int linkId) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Links/Upvoters/link_id/")
                .apiParam("link_id", String.valueOf(linkId))
                .build(), new TypeReference<>() {
        });
    }

    /**
     * Fetches all downvotes for a given link.
     *
     * @param linkId link's id.
     * @return list of downvotes.
     */
    public Chain<List<Vote>> getAllDownvotesFromLink(int linkId) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Links/Downvoters/link_id/")
                .apiParam("link_id", String.valueOf(linkId))
                .build(), new TypeReference<>() {
        });
    }

    /**
     * Fetches top links from a given year.
     *
     * @param year year.
     * @return list of links.
     */
    public Chain<List<Link>> getTopLinks(Year year) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Links/Top/year/")
                .apiParam("year", year.toString())
                .build(), new TypeReference<>() {
        });
    }

    /**
     * Fetches top links from a given year and month.
     *
     * @param year  year.
     * @param month month.
     * @return list of links.
     */
    public Chain<List<Link>> getTopLinks(Year year, Month month) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Links/Top/year/month/")
                .apiParam("year", year.toString())
                .apiParam("month", String.valueOf(month.getValue()))
                .build(), new TypeReference<>() {
        });
    }

    /**
     * Fetches link's comments sorted by the best.
     *
     * @param linkId link's id.
     * @return list of link's comments
     */
    public Chain<List<LinkComment>> getLinkComments(int linkId) {
        return getLinkComments(linkId, LinkCommentsSorting.BEST);
    }

    /**
     * Fetches link's comments with a given sorting.
     *
     * @param linkId              link's id.
     * @param linkCommentsSorting type of sorting.
     * @return list of link's comments
     */
    public Chain<List<LinkComment>> getLinkComments(int linkId, LinkCommentsSorting linkCommentsSorting) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Links/Comments/link/sort/string/")
                .apiParam("link", String.valueOf(linkId))
                .namedParam("sort", linkCommentsSorting.value())
                .build(), new TypeReference<>() {
        });
    }

    /**
     * Votes up given link's comment.
     *
     * @param linkId        link's id.
     * @param linkCommentId link's comment id.
     * @return vote data.
     * @throws LinkCommentNotExistException when link's comment does not exist.
     */
    public Chain<LinkCommentVoteData> voteUpLinkComment(int linkId, int linkCommentId) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Links/CommentVoteUp/link/comment/")
                .apiParam("link", String.valueOf(linkId))
                .apiParam("comment", String.valueOf(linkCommentId))
                .build(), LinkCommentVoteData.class);
    }

    /**
     * Votes down given link's comment.
     *
     * @param linkId        link's id.
     * @param linkCommentId link's comment id.
     * @return vote data.
     * @throws LinkCommentNotExistException when link's comment does not exist.
     */
    public Chain<LinkCommentVoteData> voteDownLinkComment(int linkId, int linkCommentId) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Links/CommentVoteDown/link/comment/")
                .apiParam("link", String.valueOf(linkId))
                .apiParam("comment", String.valueOf(linkCommentId))
                .build(), LinkCommentVoteData.class);
    }

    /**
     * Removes vote from a given link's comment.
     *
     * @param linkId        link's id.
     * @param linkCommentId link's comment id.
     * @return vote data.
     * @throws LinkCommentNotExistException when link's comment does not exist.
     */
    public Chain<LinkCommentVoteData> removeVoteFromLinkComment(int linkId, int linkCommentId) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Links/CommentVoteCancel/link/comment/")
                .apiParam("link", String.valueOf(linkId))
                .apiParam("comment", String.valueOf(linkCommentId))
                .build(), LinkCommentVoteData.class);
    }

    /**
     * Adds a new comment to the link.
     *
     * @param linkId         link's id.
     * @param newLinkComment comment to be added to link.
     * @return link's comment.
     */
    public Chain<LinkComment> addLinkComment(int linkId, NewLinkComment newLinkComment) {
        WykopRequest.Builder builder = new WykopRequest.Builder()
                .url(WYKOP_URL + "/Links/CommentAdd/link/")
                .apiParam("link", String.valueOf(linkId));
        newLinkComment.body().ifPresent(body -> builder.postParam("body", body));
        newLinkComment.urlEmbed().ifPresent(urlEmbed -> builder.postParam("embed", urlEmbed));
        newLinkComment.fileEmbed().ifPresent(file ->
                newLinkComment.shownFileName().ifPresentOrElse(shownFileName ->
                                builder.file(file, shownFileName),
                        () -> builder.file(file)));
        return new Chain<>(builder.build(), LinkComment.class);
    }

    /**
     * Adds a new comment to the link's comment.
     *
     * @param linkId         link's id.
     * @param linkCommentId  link's comment id.
     * @param newLinkComment comment to be added to link's comment.
     * @return link's comment.
     */
    public Chain<LinkComment> addLinkComment(int linkId, int linkCommentId, NewLinkComment newLinkComment) {
        WykopRequest.Builder builder = new WykopRequest.Builder()
                .url(WYKOP_URL + "/Links/CommentAdd/link/comment_id/")
                .apiParam("comment_id", String.valueOf(linkCommentId))
                .apiParam("link", String.valueOf(linkId));
        newLinkComment.body().ifPresent(body -> builder.postParam("body", body));
        newLinkComment.urlEmbed().ifPresent(urlEmbed -> builder.postParam("embed", urlEmbed));
        newLinkComment.fileEmbed().ifPresent(file ->
                newLinkComment.shownFileName().ifPresentOrElse(shownFileName ->
                                builder.file(file, shownFileName),
                        () -> builder.file(file)));
        return new Chain<>(builder.build(), LinkComment.class);
    }

    /**
     * Changes given comment with new {@link NewLinkComment}.<br>
     * <p>
     * This method is not properly tested - Wykop has no sandbox, all tests run on live server.<br>
     * Hence, some exceptions may be thrown while using this method. All known exceptions are listed below.<br>
     * I'll be glad if you create an issue on <a href="https://www.github.com/benzwreck/wykop4j">github</a> or add a new pull request.
     *
     * @param linkCommentId  comment id.
     * @param newLinkComment comment to be changed with given linkCommentId.
     * @return edited link's comment.
     * @throws CannotEditCommentsWithAnswerException when you try to edit comment when somebody has already answered it.
     */
    public Chain<LinkComment> editLinkComment(int linkCommentId, NewLinkComment newLinkComment) {
        WykopRequest.Builder builder = new WykopRequest.Builder()
                .url(WYKOP_URL + "/Links/CommentEdit/comment_id/")
                .apiParam("comment_id", String.valueOf(linkCommentId));
        newLinkComment.body().ifPresent(body -> builder.postParam("body", body));
        newLinkComment.urlEmbed().ifPresent(urlEmbed -> builder.postParam("embed", urlEmbed));
        newLinkComment.fileEmbed().ifPresent(file ->
                newLinkComment.shownFileName().ifPresentOrElse(shownFileName ->
                                builder.file(file, shownFileName),
                        () -> builder.file(file)));
        return new Chain<>(builder.build(), LinkComment.class);
    }

    /**
     * Deletes given comment.<br>
     * <p>
     * This method is not properly tested - Wykop has no sandbox, all tests run on live server.<br>
     * Hence, some exceptions may be thrown while using this method. All known exceptions are listed below.<br>
     * I'll be glad if you create an issue on <a href="https://www.github.com/benzwreck/wykop4j">github</a> or add a new pull request.
     *
     * @param linkCommentId comment id.
     * @return deleted link's comment.
     * @throws CannotReplyOnDeletedObjectsException when link does not exist or somebody has already answered to this comment.
     * @throws BodyContainsOnlyPmException          when you're trying to change a comment which aren't yours.
     */
    public Chain<LinkComment> deleteLinkComment(int linkCommentId) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Links/CommentDelete/comment_id/")
                .apiParam("comment_id", String.valueOf(linkCommentId))
                .build(), LinkComment.class);
    }

    /**
     * Fetches {@link LinkComment} with given id or returns empty Optional.
     *
     * @param id comment's id.
     * @return possible {@link LinkComment}.
     */
    public Chain<Optional<LinkComment>> getLinkComment(int id) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Links/Comment/comment/")
                .apiParam("comment", String.valueOf(id))
                .build(), new TypeReference<>() {
        });
    }

    /**
     * Fetches all related links of link with given linkId.
     *
     * @param linkId link's id.
     * @return List of related links.
     */
    public Chain<List<RelatedLink>> getRelatedLinks(int linkId) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Links/Related/link/")
                .apiParam("link", String.valueOf(linkId))
                .build(), new TypeReference<>() {
        });
    }

    /**
     * Adds a {@link RelatedLink} to given {@link Link}.
     * <p>
     * This method is not properly tested - Wykop has no sandbox, all tests run on live server.<br>
     * Hence, some exceptions may be thrown while using this method. All known exceptions are listed below.<br>
     * I'll be glad if you create an issue on <a href="https://www.github.com/benzwreck/wykop4j">github</a> or add a new pull request.
     *
     * @param linkId         link's id.
     * @param newRelatedLink link you'd like to add to related links.
     * @return related link.
     */
    public Chain<RelatedLink> addRelatedLink(int linkId, NewRelatedLink newRelatedLink) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Links/RelatedAdd/link/")
                .apiParam("link", String.valueOf(linkId))
                .postParam("title", newRelatedLink.title())
                .postParam("url", newRelatedLink.url().toString())
                .postParam("plus18", String.valueOf(newRelatedLink.isAdultOnly()))
                .build(), RelatedLink.class);
    }

    /**
     * Votes up given related link.
     *
     * <p>
     * This method is not properly tested - Wykop has no sandbox, all tests run on live server.<br>
     * Hence, some exceptions may be thrown while using this method. All known exceptions are listed below.<br>
     * I'll be glad if you create an issue on <a href="https://www.github.com/benzwreck/wykop4j">github</a> or add a new pull request.
     *
     * @param linkId        link's id.
     * @param relatedLinkId related link's id.
     * @return vote data with vote count.
     */
    public Chain<RelatedLinkVoteData> voteUpRelatedLink(int linkId, int relatedLinkId) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Links/RelatedVoteUp/link_id/related_link_id/")
                .apiParam("link_id", String.valueOf(linkId))
                .apiParam("related_link_id", String.valueOf(relatedLinkId))
                .build(), RelatedLinkVoteData.class);
    }

    /**
     * Votes down given related link.
     *
     * <p>
     * This method is not properly tested - Wykop has no sandbox, all tests run on live server.<br>
     * Hence, some exceptions may be thrown while using this method. All known exceptions are listed below.<br>
     * I'll be glad if you create an issue on <a href="https://www.github.com/benzwreck/wykop4j">github</a> or add a new pull request.
     *
     * @param linkId        link's id.
     * @param relatedLinkId related link's id.
     * @return vote data with vote count.
     */
    public Chain<RelatedLinkVoteData> voteDownRelatedLink(int linkId, int relatedLinkId) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Links/RelatedVoteDown/link_id/related_link_id/")
                .apiParam("link_id", String.valueOf(linkId))
                .apiParam("related_link_id", String.valueOf(relatedLinkId))
                .build(), RelatedLinkVoteData.class);
    }

    /**
     * Toggles favorite state.<br>
     * TBH I have no idea what this API call does. It should toggle favorite, it returns value, but on live site it seems to do nothing ¯\_(ツ)\_/¯
     *
     * @param linkId link's id.
     * @return mostly true, but what it does? No idea.
     */
    public Chain<Boolean> toggleLinkFavorite(int linkId) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Links/Favorite/id/int/")
                .namedParam("int", String.valueOf(linkId))
                .build(), Boolean.class);
    }

    // Wykop Connect

    /**
     * Returns a html page of Wykop Connect site.<br>
     * Use to provide user credentials returned to redirectURL.
     *
     * @param redirectURL redirection url
     * @return Wykop Connect html page
     */
    public Chain<String> getWykopConnectHtmlPage(URL redirectURL) {
        String secure = wykopConnect.getSecureCode(redirectURL);
        String encodedRedirectUrl = wykopConnect.encodeURL(redirectURL);
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Login/Connect/secure/string/redirect/string/")
                .namedParam("secure", secure)
                .namedParam("redirect", encodedRedirectUrl)
                .build(), String.class);
    }

    /**
     * Returns a html page of Wykop Connect site.<br>
     * Use to provide user credentials returned to https://www.wykop.pl/user/ConnectSuccess/appkey/{APPKEY}/login/{LOGIN}/token/{USERKEY}/.
     *
     * @return Wykop Connect html page
     */
    public Chain<String> getWykopConnectHtmlPage() {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Login/Connect/")
                .build(), String.class);
    }

    /**
     * Parses Wykop Connect response.
     *
     * @param response response from {@link #getWykopConnectHtmlPage(URL redirectUrl)}
     * @return login data
     */
    public WykopConnectLoginData parseWykopConnectLoginResponse(URL response) {
        return wykopConnect.parseResponse(response);
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
            if (applicationCredentials == null)
                throw new IllegalArgumentException("Application Credentials must be provided.");
            WykopHttpClient client = new WykopHttpClient(userCredentials, applicationCredentials);
            WykopObjectMapper wykopObjectMapper = new WykopObjectMapper();
            WykopConnect wykopConnect = new WykopConnect(wykopObjectMapper, applicationCredentials);
            return new WykopClient(client, wykopObjectMapper, wykopConnect);
        }
    }

    class Chain<T> {
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
