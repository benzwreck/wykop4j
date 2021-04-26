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
import io.github.benzwreck.wykop4j.exceptions.CommentDoesNotExistException;
import io.github.benzwreck.wykop4j.exceptions.LinkAlreadyExistsException;
import io.github.benzwreck.wykop4j.exceptions.LinkCommentNotExistException;
import io.github.benzwreck.wykop4j.exceptions.NiceTryException;
import io.github.benzwreck.wykop4j.exceptions.UnableToModifyEntryException;
import io.github.benzwreck.wykop4j.links.HitsOption;
import io.github.benzwreck.wykop4j.links.Link;
import io.github.benzwreck.wykop4j.links.LinkComment;
import io.github.benzwreck.wykop4j.links.LinkCommentVoteData;
import io.github.benzwreck.wykop4j.links.LinkCommentsSorting;
import io.github.benzwreck.wykop4j.links.LinkDraft;
import io.github.benzwreck.wykop4j.links.LinkVoteData;
import io.github.benzwreck.wykop4j.links.LinkWithComments;
import io.github.benzwreck.wykop4j.links.NewLink;
import io.github.benzwreck.wykop4j.links.NewLinkComment;
import io.github.benzwreck.wykop4j.links.VoteDownReason;
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

import java.io.File;
import java.time.DateTimeException;
import java.time.Month;
import java.time.Year;
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

    //Entries

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
    public Chain<Void> entryVoteUp(int entryId) {
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
    public Chain<Void> entryRemoveVote(int entryId) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Entries/VoteRemove/id/")
                .apiParam("id", String.valueOf(entryId))
                .build(), Void.class);
    }

    /**
     * @param entryId entry's id to fetch voters from.
     * @return List of {@link Vote}s.
     */
    public Chain<List<Vote>> entryAllUpvotes(int entryId) {
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
     * @param entryId         entry's id.
     * @param newEntryComment new comment to be added.
     * @return Added comment.
     * @throws ArchivalContentException when non-existing id is provided.
     */
    public Chain<EntryComment> addEntryComment(int entryId, NewEntryComment newEntryComment) {
        WykopRequest.Builder requestBuilder = new WykopRequest.Builder()
                .url(WYKOP_URL + "/Entries/CommentAdd/entry_id/")
                .apiParam("entry_id", String.valueOf(entryId));
        newEntryComment.body().ifPresent(body -> requestBuilder.postParam("body", body));
        newEntryComment.urlEmbed().ifPresent(url -> requestBuilder.postParam("embed", url));
        Optional<File> fileEmbed = newEntryComment.fileEmbed();
        if (fileEmbed.isPresent()) {
            Optional<String> shownFileName = newEntryComment.shownFileName();
            if (shownFileName.isPresent()) {
                requestBuilder.file(fileEmbed.get(), shownFileName.get());
            } else {
                requestBuilder.file(fileEmbed.get());
            }
        }
        return new Chain<>(requestBuilder.build(), EntryComment.class);
    }

    /**
     * @param commentId       comment's id.
     * @param newEntryComment new comment to be changed.
     * @return Changed comment.
     * @throws UnableToModifyEntryException when provided commentId does not belong to user's comment.
     * @throws ArchivalContentException     when provided commentId does not exist.
     */
    public Chain<EntryComment> editEntryComment(int commentId, NewEntryComment newEntryComment) {
        WykopRequest.Builder requestBuilder = new WykopRequest.Builder()
                .url(WYKOP_URL + "/Entries/CommentEdit/comment_id/")
                .apiParam("comment_id", String.valueOf(commentId));
        newEntryComment.body().ifPresent(body -> requestBuilder.postParam("body", body));
        newEntryComment.urlEmbed().ifPresent(url -> requestBuilder.postParam("embed", url));
        Optional<File> fileEmbed = newEntryComment.fileEmbed();
        if (fileEmbed.isPresent()) {
            Optional<String> shownFileName = newEntryComment.shownFileName();
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
     * @param entryId  id of entry with survey.
     * @param answerId answer's id.
     * @return Survey with answered question.
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
     * @param option type of links to retrieve.
     * @return list of chosen links.
     */
    public Chain<List<Link>> linkHits(HitsOption option) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Hits/" + option.value() + "/")
                .build(), new TypeReference<List<Link>>() {
        });
    }

    /**
     * @param month month of link's date.
     * @return list of chosen links.
     * @throws DateTimeException when illegal {@link Month} value is passed.
     */
    public Chain<List<Link>> linkHits(Month month) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Hits/Month/year/month")
                .apiParam("year", Year.now().toString())
                .apiParam("month", String.valueOf(month.getValue()))
                .build(), new TypeReference<List<Link>>() {
        });
    }

    /**
     * @param year year of link's date.
     * @return list of chosen links.
     */
    public Chain<List<Link>> linkHits(Year year) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Hits/Year/year")
                .apiParam("year", year.toString())
                .build(), new TypeReference<List<Link>>() {
        });
    }

    /**
     * @param month month of link's date.
     * @param year  year of link's date.
     * @return list of chosen links.
     * @throws DateTimeException when illegal {@link Month} value is passed.
     */
    public Chain<List<Link>> linkHits(Month month, Year year) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Hits/Month/year/month/")
                .apiParam("year", year.toString())
                .apiParam("month", String.valueOf(month.getValue()))
                .build(), new TypeReference<List<Link>>() {
        });
    }

    //Notifications

    /**
     * @return First page of user's directed notifications.
     */
    public Chain<List<Notification>> directedNotifications() {
        return directedNotifications(Page.of(1));
    }

    /**
     * @param page page you want to fetch.
     * @return Given page of user's directed notifications.
     */
    public Chain<List<Notification>> directedNotifications(Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Notifications/Index/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .fullData(false)        //wykop api crashes otherwise - returns error html page
                .build(), new TypeReference<List<Notification>>() {
        });
    }

    /**
     * @return user's directed notifications count.
     */
    public Chain<Integer> directedNotificationCount() {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Notifications/Count/")
                .build(), Integer.class);
    }

    /**
     * @return First page of user's tags notifications.
     */
    public Chain<List<Notification>> tagsNotifications() {
        return tagsNotifications(Page.of(1));
    }

    /**
     * @param page page you want to fetch.
     * @return Given page of user's tags notifications.
     */
    public Chain<List<Notification>> tagsNotifications(Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Notifications/HashTags/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .fullData(false)
                .build(), new TypeReference<List<Notification>>() {
        });
    }

    /**
     * @return user's tags notifications count.
     */
    public Chain<Integer> tagsNotificationCount() {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Notifications/HashTagsCount/")
                .build(), Integer.class);
    }

    /**
     * Combines direct notifications and tags notifications.
     *
     * @return First page of all user's notifications.
     */
    public Chain<List<Notification>> allNotifications() {
        return allNotifications(Page.of(1));
    }

    /**
     * Combines direct notifications and tags notifications.
     *
     * @param page page you want to fetch.
     * @return Given page of all user's notifications.
     */
    public Chain<List<Notification>> allNotifications(Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Notifications/Total/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .fullData(false)
                .build(), new TypeReference<List<Notification>>() {
        });
    }

    /**
     * Combines direct notifications and tags notifications count.
     *
     * @return total notification count.
     */
    public Chain<Integer> allNotificationCount() {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Notifications/TotalCount/")
                .build(), Integer.class);
    }

    /**
     * @return nothing but reads all user's notifications.
     */
    public Chain<Void> readAllNotifications() {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Notifications/ReadAllNotifications/")
                .build(), Void.class);
    }

    /**
     * @return nothing but reads all user's directed notifications.
     */
    public Chain<Void> readAllDirectedNotifications() {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Notifications/ReadDirectedNotifications/")
                .build(), Void.class);
    }

    /**
     * @return nothing but reads all user's tags notifications.
     */
    public Chain<Void> readAllTagsNotifications() {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Notifications/ReadHashTagsNotifications/")
                .build(), Void.class);
    }

    /**
     * @param notificationId notification' id you'd like to mark as read.
     * @return nothing, but marks a notification as read.
     */
    public Chain<Void> markNotificationAsRead(long notificationId) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Notifications/MarkAsRead/notification/")
                .apiParam("notification", String.valueOf(notificationId))
                .build(), Void.class);
    }

    //Pm

    /**
     * @return list of conversation's basic information.
     */
    public Chain<List<ConversationInfo>> conversationsList() {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Pm/ConversationsList/")
                .fullData(false)
                .build(), new TypeReference<List<ConversationInfo>>() {
        });
    }

    /**
     * @param login user's login.
     * @return list of messages.
     */
    public Chain<List<Message>> conversation(String login) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Pm/Conversation/receiver/")
                .apiParam("receiver", login)
                .build(), new TypeReference<List<Message>>() {
        });
    }

    /**
     * @param login      user's login.
     * @param newMessage message you'd like to send.
     * @return sent message.
     */
    public Chain<Message> sendMessage(String login, NewMessage newMessage) {
        WykopRequest.Builder builder = new WykopRequest.Builder()
                .url(WYKOP_URL + "/Pm/SendMessage/receiver/")
                .postParam("adultmedia", String.valueOf(newMessage.adultOnly()))
                .apiParam("receiver", login);
        newMessage.body().ifPresent(body -> builder.postParam("body", body));
        newMessage.urlEmbed().ifPresent(urlEmbed -> builder.postParam("embed", urlEmbed));
        Optional<File> fileEmbed = newMessage.fileEmbed();
        if (fileEmbed.isPresent()) {
            Optional<String> shownFileName = newMessage.shownFileName();
            if (shownFileName.isPresent()) {
                builder.file(fileEmbed.get(), shownFileName.get());
            } else {
                builder.file(fileEmbed.get());
            }
        }
        return new Chain<>(builder.build(), Message.class);
    }

    /**
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
     * @param login user's login.
     * @return user's profile.
     */
    public Chain<Optional<FullProfile>> profile(String login) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Profiles/Index/login/")
                .apiParam("login", login)
                .build(), new TypeReference<Optional<FullProfile>>() {
        });
    }

    /**
     * @param login user's login.
     * @return user's actions.
     */
    public Chain<Actions> profileActions(String login) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Profiles/Actions/login/")
                .apiParam("login", login)
                .build(), Actions.class);
    }

    /**
     * @param login user's login.
     * @return first page of links added by user.
     */
    public Chain<List<Link>> profileAddedLinks(String login) {
        return profileAddedLinks(login, Page.of(1));
    }

    /**
     * @param login user's login.
     * @param page  page.
     * @return given page of links added by user.
     */
    public Chain<List<Link>> profileAddedLinks(String login, Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Profiles/Added/login/page/int/")
                .apiParam("login", login)
                .namedParam("page", String.valueOf(page.value()))
                .build(), new TypeReference<List<Link>>() {
        });
    }

    /**
     * @param login user's login.
     * @return first page of links commented by user.
     */
    public Chain<List<Link>> profileCommentedLinks(String login) {
        return profileCommentedLinks(login, Page.of(1));
    }

    /**
     * @param login user's login.
     * @param page  page.
     * @return given page of links commented by user.
     */
    public Chain<List<Link>> profileCommentedLinks(String login, Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Profiles/Commented/login/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .apiParam("login", login)
                .build(), new TypeReference<List<Link>>() {
        });
    }

    /**
     * @param login user's login.
     * @return first page of links' comments added by user.
     */
    public Chain<List<LinkComment>> profileLinksComments(String login) {
        return profileLinksComments(login, Page.of(1));
    }

    /**
     * @param login user's login.
     * @param page  page.
     * @return given page of links' comments added by user.
     */
    public Chain<List<LinkComment>> profileLinksComments(String login, Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Profiles/Comments/login/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .apiParam("login", login)
                .build(), new TypeReference<List<LinkComment>>() {
        });
    }

    /**
     * @param login user's login.
     * @return first page of links published by user.
     */
    public Chain<List<Link>> profileLinksPublished(String login) {
        return profileLinksPublished(login, Page.of(1));
    }

    /**
     * @param login user's login.
     * @param page  page.
     * @return given page of links published by user.
     */
    public Chain<List<Link>> profileLinksPublished(String login, Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Profiles/Published/login/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .apiParam("login", login)
                .build(), new TypeReference<List<Link>>() {
        });
    }

    /**
     * @param login user's login.
     * @return first page of entries added by user.
     */
    public Chain<List<Entry>> profileEntries(String login) {
        return profileEntries(login, Page.of(1));
    }

    /**
     * @param login user's login.
     * @param page  page.
     * @return given page of entries added by user.
     */
    public Chain<List<Entry>> profileEntries(String login, Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Profiles/Entries/login/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .apiParam("login", login)
                .build(), new TypeReference<List<Entry>>() {
        });
    }

    /**
     * @param login user's login.
     * @return first page of entries commented by user.
     */
    public Chain<List<Entry>> profileEntriesCommented(String login) {
        return profileEntriesCommented(login, Page.of(1));
    }

    /**
     * @param login user's login.
     * @param page  page.
     * @return given page of entries commented by user.
     */
    public Chain<List<Entry>> profileEntriesCommented(String login, Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Profiles/CommentedEntries/login/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .apiParam("login", login)
                .build(), new TypeReference<List<Entry>>() {
        });
    }

    /**
     * @param login user's login.
     * @return first page of entries' comments by user.
     */
    public Chain<List<EntryComment>> profileEntriesComments(String login) {
        return profileEntriesComments(login, Page.of(1));
    }

    /**
     * @param login user's login.
     * @param page  page.
     * @return given page of entries' comments by user.
     */
    public Chain<List<EntryComment>> profileEntriesComments(String login, Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Profiles/EntriesComments/login/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .apiParam("login", login)
                .build(), new TypeReference<List<EntryComment>>() {
        });
    }

    /**
     * @param login user's login.
     * @return first page of related links added by user.
     */
    public Chain<List<Link>> profileRelatedLinks(String login) {
        return profileRelatedLinks(login, Page.of(1));
    }

    /**
     * @param login user's login.
     * @param page  page.
     * @return given page of related links added by user.
     */
    public Chain<List<Link>> profileRelatedLinks(String login, Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Profiles/EntriesComments/login/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .apiParam("login", login)
                .build(), new TypeReference<List<Link>>() {
        });
    }

    /**
     * @param login user's login.
     * @return first page of users following particular user.
     */
    public Chain<List<FullProfile>> profileFollowers(String login) {
        return profileFollowers(login, Page.of(1));
    }

    /**
     * @param login user's login.
     * @param page  page.
     * @return given page of users following particular user.
     */
    public Chain<List<FullProfile>> profileFollowers(String login, Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Profiles/Followers/login/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .apiParam("login", login)
                .build(), new TypeReference<List<FullProfile>>() {
        });
    }

    /**
     * @param login user's login.
     * @return first page of users being followed by particular user.
     */
    public Chain<List<FullProfile>> profileFollowed(String login) {
        return profileFollowed(login, Page.of(1));
    }

    /**
     * @param login user's login.
     * @param page  page.
     * @return given page of users being followed by particular user.
     */
    public Chain<List<FullProfile>> profileFollowed(String login, Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Profiles/Followed/login/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .apiParam("login", login)
                .build(), new TypeReference<List<FullProfile>>() {
        });
    }

    /**
     * @param login user's login.
     * @return first page of user's badges.
     */
    public Chain<List<Badge>> profileBadges(String login) {
        return profileBadges(login, Page.of(1));
    }

    /**
     * @param login user's login.
     * @param page  page.
     * @return given page of user's badges.
     */
    public Chain<List<Badge>> profileBadges(String login, Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Profiles/Badges/login/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .apiParam("login", login)
                .build(), new TypeReference<List<Badge>>() {
        });
    }

    /**
     * @param login user's login.
     * @return first page of user's digged links.
     */
    public Chain<List<Link>> profileDiggedLinks(String login) {
        return profileDiggedLinks(login, Page.of(1));
    }

    /**
     * @param login user's login.
     * @param page  page.
     * @return given page of user's digged links.
     */
    public Chain<List<Link>> profileDiggedLinks(String login, Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Profiles/Digged/login/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .apiParam("login", login)
                .build(), new TypeReference<List<Link>>() {
        });
    }

    /**
     * @param login user's login.
     * @return first page of user's buried links.
     */
    public Chain<List<Link>> profileBuriedLinks(String login) {
        return profileBuriedLinks(login, Page.of(1));
    }

    /**
     * @param login user's login.
     * @param page  page.
     * @return given page of user's buried links.
     */
    public Chain<List<Link>> profileBuriedLinks(String login, Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Profiles/Buried/login/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .apiParam("login", login)
                .build(), new TypeReference<List<Link>>() {
        });
    }

    /**
     * @return first page of profiles from user rank.
     */
    public Chain<List<FullProfile>> profileRanking() {
        return profileRanking(Page.of(1));
    }

    /**
     * @param page page.
     * @return given page of profiles from user rank.
     */
    public Chain<List<FullProfile>> profileRanking(Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Profiles/Rank/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .build(), new TypeReference<List<FullProfile>>() {
        });
    }

    /**
     * @return interaction status - being observed or blocked.
     */
    public Chain<InteractionStatus> observeUser(String login) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Profiles/Observe/login/")
                .apiParam("login", login)
                .build(), InteractionStatus.class);
    }

    /**
     * @return interaction status - being observed or blocked.
     */
    public Chain<InteractionStatus> unobserveUser(String login) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Profiles/UnObserve/login/")
                .apiParam("login", login)
                .build(), InteractionStatus.class);
    }

    /**
     * @return interaction status - being observed or blocked.
     */
    public Chain<InteractionStatus> blockUser(String login) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Profiles/Block/login/")
                .apiParam("login", login)
                .build(), InteractionStatus.class);
    }

    /**
     * @return interaction status - being observed or blocked.
     */
    public Chain<InteractionStatus> unblockUser(String login) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Profiles/UnBlock/login/")
                .apiParam("login", login)
                .build(), InteractionStatus.class);
    }

    // Terms

    /**
     * @return terms of use.
     */
    public Chain<Terms> terms() {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Terms/Index/")
                .build(), Terms.class);
    }

    /**
     * @return confirmation terms status.
     */
    public Chain<Boolean> confirmTerms() {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Terms/Confirm/")
                .build(), Boolean.class);
    }

    // Tags

    /**
     * @param tag name of the tag, either with or without '#'.
     * @return first page of actions.
     */
    public Chain<Actions> tagActions(String tag) {
        return tagActions(tag, Page.of(1));
    }

    /**
     * @param tag  name of the tag, either with or without '#'.
     * @param page page.
     * @return given page of actions.
     */
    public Chain<Actions> tagActions(String tag, Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Tags/Index/tag/page/int/")
                .apiParam("tag", tag)
                .namedParam("page", String.valueOf(page.value()))
                .build(), Actions.class);
    }

    /**
     * @param tag name of the tag, either with or without '#'.
     * @return first page of list of links.
     */
    public Chain<List<Link>> tagLinks(String tag) {
        return tagLinks(tag, Page.of(1));
    }

    /**
     * @param tag  name of the tag, either with or without '#'.
     * @param page page.
     * @return given page of list of links.
     */
    public Chain<List<Link>> tagLinks(String tag, Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Tags/Links/tag/page/int/")
                .apiParam("tag", tag)
                .namedParam("page", String.valueOf(page.value()))
                .build(), new TypeReference<List<Link>>() {
        });
    }

    /**
     * @param tag name of the tag, either with or without '#'.
     * @return first page of list of entries.
     */
    public Chain<List<Entry>> tagEntries(String tag) {
        return tagEntries(tag, Page.of(1));
    }

    /**
     * @param tag  name of the tag, either with or without '#'.
     * @param page page.
     * @return given page of list of entries.
     */
    public Chain<List<Entry>> tagEntries(String tag, Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Tags/Entries/tag/page/int/")
                .apiParam("tag", tag)
                .namedParam("page", String.valueOf(page.value()))
                .build(), new TypeReference<List<Entry>>() {
        });
    }

    /**
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
     * Enable tag notifications.
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
     * Disable tag notifications.
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
     * @param tag tag's name.
     * @return list of tag suggestions.
     */
    public Chain<List<TagSuggestion>> suggestTags(String tag) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Suggest/Tags/tag/")
                .apiParam("tag", tag)
                .build(), new TypeReference<List<TagSuggestion>>() {
        });
    }

    /**
     * @param login user's login.
     * @return list of user suggestions.
     */
    public Chain<List<SimpleProfile>> suggestUsers(String login) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Suggest/Users/login/")
                .apiParam("login", login)
                .build(), new TypeReference<List<SimpleProfile>>() {
        });
    }

    // Mywykop

    /**
     * Actions from observed users and tags from MyWykop.
     *
     * @return first page of list of all actions.
     */
    public Chain<Actions> myWykopIndexActions() {
        return myWykopIndexActions(Page.of(1));
    }

    /**
     * Actions from observed users and tags from MyWykop.
     *
     * @param page page.
     * @return given page of list of all actions.
     */
    public Chain<Actions> myWykopIndexActions(Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Mywykop/Index/type/string/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .build(), Actions.class);
    }

    /**
     * Actions from observed users and tags from MyWykop.
     *
     * @param type type of returning value.
     * @return first page of list of {@link ActionType} actions.
     */
    public Chain<Actions> myWykopIndexActions(ActionType type) {
        return myWykopIndexActions(type, Page.of(1));
    }

    /**
     * Actions from observed users and tags from MyWykop.
     *
     * @param type type of returning value.
     * @param page page.
     * @return given page of list of {@link ActionType} actions.
     */
    public Chain<Actions> myWykopIndexActions(ActionType type, Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Mywykop/Index/type/string/page/int/")
                .namedParam("type", type.value())
                .namedParam("page", String.valueOf(page.value()))
                .build(), Actions.class);
    }

    /**
     * Actions from MyWykop's observed tags.
     *
     * @return first page of list of all actions.
     */
    public Chain<Actions> myWykopTagsActions() {
        return myWykopTagsActions(Page.of(1));
    }

    /**
     * Actions from MyWykop's observed tags.
     *
     * @param page page.
     * @return given page of list of all actions.
     */
    public Chain<Actions> myWykopTagsActions(Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Mywykop/Tags/type/string/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .build(), Actions.class);
    }

    /**
     * Actions from MyWykop's observed tags.
     *
     * @param type type of returning value.
     * @return first page of list of {@link ActionType} actions.
     */
    public Chain<Actions> myWykopTagsActions(ActionType type) {
        return myWykopIndexActions(type, Page.of(1));
    }

    /**
     * Actions from MyWykop's observed tags.
     *
     * @param type type of returning value.
     * @param page page.
     * @return given page of list of {@link ActionType} actions.
     */
    public Chain<Actions> myWykopTagsActions(ActionType type, Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Mywykop/Tags/type/string/page/int/")
                .namedParam("type", type.value())
                .namedParam("page", String.valueOf(page.value()))
                .build(), Actions.class);
    }

    /**
     * Actions from MyWykop's observed users.
     *
     * @return first page of list of all actions.
     */
    public Chain<Actions> myWykopUsersActions() {
        return myWykopUsersActions(Page.of(1));
    }

    /**
     * Actions from MyWykop's observed users.
     *
     * @param page page.
     * @return given page of list of all actions.
     */
    public Chain<Actions> myWykopUsersActions(Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Mywykop/Users/type/string/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .build(), Actions.class);
    }

    /**
     * Actions from MyWykop's observed users.
     *
     * @param type type of returning value.
     * @return first page of list of {@link ActionType} actions.
     */
    public Chain<Actions> myWykopUsersActions(ActionType type) {
        return myWykopUsersActions(type, Page.of(1));
    }

    /**
     * Actions from MyWykop's observed users.
     *
     * @param type type of returning value.
     * @param page page.
     * @return given page of list of {@link ActionType} actions.
     */
    public Chain<Actions> myWykopUsersActions(ActionType type, Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Mywykop/Users/type/string/page/int/")
                .namedParam("type", type.value())
                .namedParam("page", String.valueOf(page.value()))
                .build(), Actions.class);
    }

    /**
     * Entries from observed users and tags.
     *
     * @return list of entries.
     */
    public Chain<List<Entry>> myWykopEntries() {
        return myWykopEntries(Page.of(1));
    }

    /**
     * Entries from observed users and tags.
     *
     * @param page page.
     * @return list of entries.
     */
    public Chain<List<Entry>> myWykopEntries(Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Mywykop/Entries/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .build(), new TypeReference<List<Entry>>() {
        });
    }

    /**
     * Links from observed users and tags.
     *
     * @return list of links.
     */
    public Chain<List<Link>> myWykopLinks() {
        return myWykopLinks(Page.of(1));
    }

    /**
     * Links from observed users and tags.
     *
     * @param page page.
     * @return list of links.
     */
    public Chain<List<Link>> myWykopLinks(Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Mywykop/Links/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .build(), new TypeReference<List<Link>>() {
        });
    }

    // Search

    /**
     * @param phrase search phrase.
     * @return first page of list of links
     */
    public Chain<List<Link>> searchLinks(String phrase) {
        return searchLinks(phrase, Page.of(1));
    }

    /**
     * @param phrase search phrase.
     * @param page   page.
     * @return given page of list of links.
     */
    public Chain<List<Link>> searchLinks(String phrase, Page page) {
        return searchLinks(new LinkSearchQuery.Builder().phrase(phrase).build(), page);
    }

    /**
     * @param linkSearchQuery search query.
     * @return first page of list of links.
     */
    public Chain<List<Link>> searchLinks(LinkSearchQuery linkSearchQuery) {
        return searchLinks(linkSearchQuery, Page.of(1));
    }

    /**
     * @param linkSearchQuery search query.
     * @param page            page.
     * @return given page of list of links.
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
        return new Chain<>(builder.build(), new TypeReference<List<Link>>() {
        });
    }

    /**
     * @param phrase search phrase.
     * @return first page of list of entries.
     */
    public Chain<List<Entry>> searchEntries(String phrase) {
        return searchEntries(phrase, Page.of(1));
    }

    /**
     * @param phrase search phrase.
     * @param page   page.
     * @return given page of list of entries.
     */
    public Chain<List<Entry>> searchEntries(String phrase, Page page) {
        return searchEntries(new EntrySearchQuery.Builder().phrase(phrase).build(), page);
    }

    /**
     * @param entrySearchQuery search query.
     * @return first page of list of entries.
     */
    public Chain<List<Entry>> searchEntries(EntrySearchQuery entrySearchQuery) {
        return searchEntries(entrySearchQuery, Page.of(1));
    }

    /**
     * @param entrySearchQuery search query.
     * @param page             page.
     * @return given page of list of entries.
     */
    public Chain<List<Entry>> searchEntries(EntrySearchQuery entrySearchQuery, Page page) {
        WykopRequest.Builder builder = new WykopRequest.Builder()
                .url(WYKOP_URL + "/Search/Entries/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .postParam("q", entrySearchQuery.phrase())
                .postParam("when", entrySearchQuery.dateRange().value());
        return new Chain<>(builder.build(), new TypeReference<List<Entry>>() {
        });
    }

    /**
     * @param login login.
     * @return list of searched profiles.
     */
    public Chain<List<SimpleProfile>> searchProfiles(String login) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Search/Profiles/")
                .postParam("q", login)
                .build(), new TypeReference<List<SimpleProfile>>() {
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
    public Chain<LinkDraft> linkPrepareDraft(String url){
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Addlink/Draft/")
                .postParam("url", url)
                .build(), LinkDraft.class);
    }

    public Chain<String> linkPrepareImage(String key){
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Addlink/Images/key/string/")
                .namedParam("key", key)
                .build(), String.class);
    }

    public Chain<Link> linkAdd(NewLink newLink){
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Addlink/Add/key/string/")
                .namedParam("key", "595878326141494341524578")
                .postParam("title", "testapi")
                .postParam("description", "testuje api")
                .postParam("tags", "testapi")
                .postParam("url", "https://www.youtube.com/watch?v=Bm5iA4Zupek")
                .build(), Link.class);
    }

    // Links

    /**
     * @return first page of promoted links.
     */
    public Chain<List<Link>> promotedLinks() {
        return promotedLinks(Page.of(1));
    }

    /**
     * @param page page.
     * @return given page of promoted links.
     */
    public Chain<List<Link>> promotedLinks(Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Links/Promoted/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .build(), new TypeReference<List<Link>>() {
        });
    }

    /**
     * @return first page of upcoming links.
     */
    public Chain<List<Link>> upcomingLinks() {
        return upcomingLinks(Page.of(1));
    }

    /**
     * @param page page.
     * @return given page of upcoming links.
     */
    public Chain<List<Link>> upcomingLinks(Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Links/Upcoming/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .build(), new TypeReference<List<Link>>() {
        });
    }

    /**
     * @return first page of favorite links.
     */
    public Chain<List<Link>> favoriteLinks() {
        return favoriteLinks(Page.of(1));
    }

    /**
     * @param page page.
     * @return given page of favorite links.
     */
    public Chain<List<Link>> favoriteLinks(Page page) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Links/Observed/page/int/")   //yep, it returns favorite links, not observed ones
                .namedParam("page", String.valueOf(page.value()))
                .build(), new TypeReference<List<Link>>() {
        });
    }

    /**
     * @param linkId link's id.
     * @return possible link without comments.
     */
    public Chain<Optional<Link>> link(int linkId) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Links/Link/id/")
                .apiParam("id", String.valueOf(linkId))
                .build(), new TypeReference<Optional<Link>>() {
        });
    }

    /**
     * @param linkId link's id.
     * @return possible link with comments.
     */
    public Chain<Optional<LinkWithComments>> linkWithComments(int linkId) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Links/Link/id/withcomments/true/")
                .apiParam("id", String.valueOf(linkId))
                .build(), new TypeReference<Optional<LinkWithComments>>() {
        });
    }

    /**
     * Votes up given link.
     *
     * @param linkId link's id.
     * @return link's vote data.
     * @throws ArchivalContentException when id is invalid.
     */
    public Chain<LinkVoteData> linkVoteUp(int linkId) {
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
    public Chain<LinkVoteData> linkVoteRemove(int linkId) {
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
    public Chain<LinkVoteData> linkVoteDown(int linkId, VoteDownReason voteDownReason) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Links/VoteDown/id/voteType/")
                .apiParam("id", String.valueOf(linkId))
                .apiParam("voteType", String.valueOf(voteDownReason.value()))
                .build(), LinkVoteData.class);
    }

    /**
     * @param linkId link's id.
     * @return list of upvotes for a given link.
     */
    public Chain<List<Vote>> linkAllUpvotes(int linkId) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Links/Upvoters/link_id/")
                .apiParam("link_id", String.valueOf(linkId))
                .build(), new TypeReference<List<Vote>>() {
        });
    }

    /**
     * @param linkId link's id.
     * @return list of downvotes for a given link.
     */
    public Chain<List<Vote>> linkAllDownvotes(int linkId) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Links/Downvoters/link_id/")
                .apiParam("link_id", String.valueOf(linkId))
                .build(), new TypeReference<List<Vote>>() {
        });
    }

    /**
     * @param year year.
     * @return list of top links from given year.
     */
    public Chain<List<Link>> linkTop(Year year) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Links/Top/year/")
                .apiParam("year", year.toString())
                .build(), new TypeReference<List<Link>>() {
        });
    }

    /**
     * @param year  year.
     * @param month month.
     * @return list of top links from a given year and month.
     */
    public Chain<List<Link>> linkTop(Year year, Month month) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Links/Top/year/month/")
                .apiParam("year", year.toString())
                .apiParam("month", String.valueOf(month.getValue()))
                .build(), new TypeReference<List<Link>>() {
        });
    }

    /**
     * @param linkId link's id.
     * @return list of link's comments
     */
    public Chain<List<LinkComment>> linkComments(int linkId) {
        return linkComments(linkId, LinkCommentsSorting.BEST);
    }

    /**
     * @param linkId              link's id.
     * @param linkCommentsSorting type of sorting.
     * @return list of link's comments
     */
    public Chain<List<LinkComment>> linkComments(int linkId, LinkCommentsSorting linkCommentsSorting) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Links/Comments/link/sort/string/")
                .apiParam("link", String.valueOf(linkId))
                .namedParam("sort", linkCommentsSorting.value())
                .build(), new TypeReference<List<LinkComment>>() {
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
    public Chain<LinkCommentVoteData> linkCommentVoteUp(int linkId, int linkCommentId) {
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
    public Chain<LinkCommentVoteData> linkCommentVoteDown(int linkId, int linkCommentId) {
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
    public Chain<LinkCommentVoteData> linkCommentVoteRemove(int linkId, int linkCommentId) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Links/CommentVoteCancel/link/comment/")
                .apiParam("link", String.valueOf(linkId))
                .apiParam("comment", String.valueOf(linkCommentId))
                .build(), LinkCommentVoteData.class);
    }

    /**
     * @param linkId         link's id.
     * @param newLinkComment comment to be added to link.
     * @return link's comment.
     */
    public Chain<LinkComment> linkAddComment(int linkId, NewLinkComment newLinkComment) {
        WykopRequest.Builder builder = new WykopRequest.Builder()
                .url(WYKOP_URL + "/Links/CommentAdd/link/")
                .apiParam("link", String.valueOf(linkId));
        newLinkComment.body().ifPresent(body -> builder.postParam("body", body));
        newLinkComment.urlEmbed().ifPresent(urlEmbed -> builder.postParam("embed", urlEmbed));
        newLinkComment.shownFileName().ifPresentOrElse(shownFileName ->
                        newLinkComment.fileEmbed().ifPresent(file -> builder.file(file, shownFileName)),
                () -> newLinkComment.fileEmbed().ifPresent(builder::file));
        return new Chain<>(builder.build(), LinkComment.class);
    }

    /**
     * @param linkId         link's id.
     * @param linkCommentId  link's comment id.
     * @param newLinkComment comment to be added to link's comment.
     * @return link's comment.
     */
    public Chain<LinkComment> linkAddComment(int linkId, int linkCommentId, NewLinkComment newLinkComment) {
        WykopRequest.Builder builder = new WykopRequest.Builder()
                .url(WYKOP_URL + "/Links/CommentAdd/link/comment_id/")
                .apiParam("comment_id", String.valueOf(linkCommentId))
                .apiParam("link", String.valueOf(linkId));
        newLinkComment.body().ifPresent(body -> builder.postParam("body", body));
        newLinkComment.urlEmbed().ifPresent(urlEmbed -> builder.postParam("embed", urlEmbed));
        newLinkComment.shownFileName().ifPresentOrElse(shownFileName ->
                        newLinkComment.fileEmbed().ifPresent(file -> builder.file(file, shownFileName)),
                () -> newLinkComment.fileEmbed().ifPresent(builder::file));

        return new Chain<>(builder.build(), LinkComment.class);
    }

    /**
     * @param linkCommentId  comment id.
     * @param newLinkComment comment to be changed with given linkCommentId.
     * @return edited link's comment.
     */
    public Chain<LinkComment> linkEditComment(int linkCommentId, NewLinkComment newLinkComment) {
        WykopRequest.Builder builder = new WykopRequest.Builder()
                .url(WYKOP_URL + "/Links/CommentEdit/comment_id/")
                .apiParam("comment_id", String.valueOf(linkCommentId));
        newLinkComment.body().ifPresent(body -> builder.postParam("body", body));
        newLinkComment.urlEmbed().ifPresent(urlEmbed -> builder.postParam("embed", urlEmbed));
        newLinkComment.shownFileName().ifPresentOrElse(shownFileName ->
                        newLinkComment.fileEmbed().ifPresent(file -> builder.file(file, shownFileName)),
                () -> newLinkComment.fileEmbed().ifPresent(builder::file));
        return new Chain<>(builder.build(), LinkComment.class);
    }

    /**
     * @param linkCommentId comment id.
     * @return deleted link's comment.
     */
    public Chain<LinkComment> linkDeleteComment(int linkCommentId) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Links/CommentDelete/comment_id/")
                .apiParam("comment_id", String.valueOf(linkCommentId))
                .build(), LinkComment.class);
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
                throw new IllegalStateException("Application Credentials must be provided.");
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
