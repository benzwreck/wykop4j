package io.github.benzwreck.wykop4j.integration

import io.github.benzwreck.wykop4j.IntegrationWykopClient
import io.github.benzwreck.wykop4j.Page
import io.github.benzwreck.wykop4j.WykopClient
import io.github.benzwreck.wykop4j.entries.Entry
import io.github.benzwreck.wykop4j.entries.NewEntry
import io.github.benzwreck.wykop4j.entries.NewEntryComment
import io.github.benzwreck.wykop4j.entries.Period
import io.github.benzwreck.wykop4j.exceptions.ActionForbiddenException
import io.github.benzwreck.wykop4j.exceptions.ArchivalContentException
import io.github.benzwreck.wykop4j.exceptions.CommentDoesNotExistException
import io.github.benzwreck.wykop4j.exceptions.NiceTryException
import io.github.benzwreck.wykop4j.exceptions.UnableToDeleteCommentException
import io.github.benzwreck.wykop4j.exceptions.UnableToModifyEntryException
import io.github.benzwreck.wykop4j.shared.UserVote
import spock.lang.Specification
import spock.lang.Unroll
import spock.util.concurrent.PollingConditions

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class EntrySpec extends Specification {
    WykopClient wykop = IntegrationWykopClient.getInstance()
    private int nonexistentId = -111111
    private def newEntryWithBodyAndUrlMedia = new NewEntry.Builder()
            .withBody("obraz")
            .withMedia("https://www.wykop.pl/cdn/c3201142/comment_1613001626Mwe2NcUAMJ1yLKZJumQQjC.jpg")
            .build()
    private def newCommentWithBodyAndUrlMedia = new NewEntryComment.Builder()
            .withBody("obraz")
            .withMedia("https://www.wykop.pl/cdn/c3201142/comment_1613001626Mwe2NcUAMJ1yLKZJumQQjC.jpg")
            .build()

    def "should return first and second page of entries' stream"() {
        when:
        def entries1 = wykop.getEntriesStream(Page.of(1)).execute()
        def entries2 = wykop.getEntriesStream(Page.of(2)).execute()

        then:
        def allEntries = entries1 + entries2
        for (i in 0..<allEntries.size() - 1) {
            allEntries[i].id() > allEntries[i + 1].id()
        }
    }

    def "should return list of entries where first entry is the next one of all entries' stream"() {
        when:
        def entriesStream = wykop.getEntriesStream().execute()
        def execute = wykop.getEntriesStream(entriesStream.get(0).id()).execute()
        then:
        execute.get(0).id() <= entriesStream.get(0).id()
    }

    def "should return entry"() {
        when:
        def randomEntryId = wykop.getHotEntries().execute().get(0).id()
        def entry = wykop.getEntry(randomEntryId).execute()
        then:
        randomEntryId == entry.get().id()
    }

    def "should return an empty Optional - entry id doesn't exist"() {
        when:
        def entry = wykop.getEntry(nonexistentId).execute()
        then:
        entry == Optional.empty()
    }

    @Unroll
    def "should return first #period.value() hours hot page"() {
        expect:
        wykop.getHotEntries(period).execute()
                .stream()
                .map(entry -> entry.date())
                .filter(date -> date.until(LocalDateTime.now(), ChronoUnit.HOURS) > period.value())
                .count() == 0
        where:
        period << [Period.SIX_HOURS, Period.TWELVE_HOURS, Period.TWENTY_FOUR_HOURS]

    }

    def "should return list of active entries from first page"() {
        when:
        def entries = wykop.getActiveEntries().execute()
        then: "active entries must contain comments"
        entries.each { it.commentsCount() > 0 }
    }

    def "should return empty list of active entries from non-existent page"() {
        when:
        def entries = wykop.getActiveEntries(Page.of(10000)).execute()
        then:
        entries.isEmpty()
    }

    def "should return list of observed entries from first page"() {
        when:
        def entries = wykop.getObservedEntries().execute()
        then:
        entries.forEach { it.favorite() }
    }

    def "should return empty list of observed entries from non-existent page"() {
        when:
        def entries = wykop.getObservedEntries(Page.of(10000)).execute()
        then:
        entries.isEmpty()
    }

    def "should add and delete entry"() {
        when:
        def addEntry = wykop.addEntry(newEntryWithBodyAndUrlMedia).execute()
        def deletedEntry = wykop.deleteEntry(addEntry.id()).execute()
        then:
        deletedEntry.status() == "deleted"
    }

    def "should throw an exception when try to delete somebody else's entry"() {
        setup:
        def randomId = wykop.getEntriesStream().execute().get(0).id()
        when:
        wykop.deleteEntry(randomId).execute()
        then:
        thrown ActionForbiddenException
    }

    def "should add new entry with jpg file"() {
        def newEntry = new NewEntry.Builder()
                .withBody("obraz")
                .withMedia(new File("src/test/resources/white.jpg"), "bialo")
                .adultOnly()
                .build()
        when:
        def addEntry = wykop.addEntry(newEntry).execute()
        then:
        addEntry.body().get() == "obraz"
        def embed = addEntry.embed().get()
        embed.source() == "bialo"
        embed.plus18()
        cleanup:
        wykop.deleteEntry(addEntry.id()).execute()
    }

    def "should add new entry with jpg url"() {
        when:
        def addEntry = wykop.addEntry(newEntryWithBodyAndUrlMedia).execute()
        then:
        addEntry.body().get() == "obraz"
        def embed = addEntry.embed().get()
        embed.source() == "wykop.pl"
        !embed.plus18()
        cleanup:
        wykop.deleteEntry(addEntry.id()).execute()
    }

    def "should add new entry without embed when given media url does not exist"() {
        def newEntry = new NewEntry.Builder()
                .withMedia("https://www.wykop.pl/cdn/c3201142/comment_1613001626Mwe2NcUAMJ1yLKZJumQQjC.jpg")
                .build()
        when:
        def entry = wykop.addEntry(newEntry).execute()
        then:
        entry.embed().isPresent()
        !entry.body().isPresent()
        cleanup:
        wykop.deleteEntry(entry.id()).execute()
    }

    def "should throw an exception when neither body nor media is provided"() {
        when:
        new NewEntry.Builder()
                .adultOnly()
                .build()
        then:
        thrown IllegalArgumentException
    }

    def "should be able to edit entry"() {
        setup:
        def newEntry = new NewEntry.Builder()
                .withBody("obraz")
                .withMedia("https://www.wykop.pl/cdn/c3201142/comment_1613001626Mwe2NcUAMJ1yLKZJumQQjC.jpg")
                .build()
        def addEntry = wykop.addEntry(newEntry).execute()
        def editedNewEntry = new NewEntry.Builder(newEntry)
                .adultOnly()
                .build()
        when:
        def editedEntry = wykop.editEntry(addEntry.id(), editedNewEntry).execute()
        then:
        addEntry.embed().filter(e -> !e.plus18()).isPresent()
        editedEntry.embed().filter(e -> e.plus18()).isPresent()
        cleanup:
        wykop.deleteEntry(addEntry.id()).execute()
        wykop.deleteEntry(editedEntry.id()).execute()
    }

    def "should throw an exception when try to edit entry"() {
        setup:
        def editedNewEntry = new NewEntry.Builder()
                .withBody("obraz")
                .withMedia("https://www.wykop.pl/cdn/c3201142/comment_1613001626Mwe2NcUAMJ1yLKZJumQQjC.jpg")
                .build()
        when:
        wykop.editEntry(-100000, editedNewEntry).execute()
        then:
        thrown UnableToModifyEntryException
    }

    def "should upvote entry and then remove vote"() {
        def conditions = new PollingConditions(timeout: 5, initialDelay: 1)
        def randomId = wykop.getEntriesStream().execute().get(0).id()
        when:
        wykop.voteUpEntry(randomId).execute()
        then:
        conditions.eventually {
            assert wykop.getEntry(randomId).execute()
                    .map(e -> e.userVote())
                    .filter(userVote -> userVote == UserVote.VOTED)
                    .isPresent()
        }
        when:
        wykop.voteRemoveFromEntry(randomId).execute()
        then:
        conditions.eventually {
            assert wykop.getEntry(randomId).execute()
                    .map(e -> e.userVote())
                    .filter(userVote -> userVote == UserVote.NOT_VOTED)
                    .isPresent()
        }
    }

    def "should throw an exception when try to upvote non-existing entry"() {
        when:
        wykop.voteUpEntry(nonexistentId).execute()
        then:
        thrown ArchivalContentException
    }

    def "should throw an exception when try to remove vote from non-existing entry"() {
        when:
        wykop.voteRemoveFromEntry(nonexistentId).execute()
        then:
        thrown ArchivalContentException
    }

    def "should return list of upvoters"() {
        setup:
        def randomId = wykop.getHotEntries().execute().get(0).id()
        when:
        def votes = wykop.getAllUpvotesFromEntry(randomId).execute()
        then:
        !votes.isEmpty()
    }

    def "should return comment"() {
        setup:
        def hotEntries = wykop.getHotEntries().execute()
        def entry = wykop.getEntry(hotEntries.get(0).id()).execute()
        def id = entry.get().comments().get().get(0).id()
        when:
        def comment = wykop.getEntryComment(id).execute()
        then:
        comment.isPresent()
    }

    def "should return empty Optional when given non-existent entry's id"() {
        when:
        def comment = wykop.getEntryComment(nonexistentId).execute()
        then:
        !comment.isPresent()
    }

    def "should add comment to entry"() {
        when:
        def addEntryId = wykop.addEntry(newEntryWithBodyAndUrlMedia).execute().id()
        def comment = wykop.addEntryComment(addEntryId, newCommentWithBodyAndUrlMedia).execute()
        then:
        wykop.getEntry(addEntryId).execute()
                .flatMap(e -> e.comments())
                .flatMap(list -> list.stream().filter(c -> c.id() == comment.id()).findFirst())
                .isPresent()
        cleanup:
        wykop.deleteEntry(addEntryId).execute()
    }

    def "should throw an exception when new comment is added to non-existing entry"() {
        when:
        wykop.addEntryComment(nonexistentId, newCommentWithBodyAndUrlMedia).execute()
        then:
        thrown ArchivalContentException
    }

    def "should edit comment"() {
        def differentNewComment = new NewEntryComment.Builder()
                .withBody("lolol")
                .build()
        when:
        def addEntryId = wykop.addEntry(newEntryWithBodyAndUrlMedia).execute().id()
        def entryComment = wykop.addEntryComment(addEntryId, newCommentWithBodyAndUrlMedia).execute()
        then:
        entryComment.body() == "obraz"
        when:
        def editedComment = wykop.editEntryComment(entryComment.id(), differentNewComment).execute()
        then:
        editedComment.body() == "lolol"
        cleanup:
        wykop.deleteEntry(addEntryId).execute()
    }

    def "should throw an exception when given commentId does not belong to user's entry"() {
        when:
        def randomId = wykop.getActiveEntries().execute().get(0).id()
        def randomEntryCommentId = wykop.getEntry(randomId).execute().get().comments().get().get(0).id()
        wykop.editEntryComment(randomEntryCommentId, newCommentWithBodyAndUrlMedia).execute()
        then:
        thrown UnableToModifyEntryException
    }

    def "should throw an exception when given commentId does not exist"() {
        when:
        wykop.editEntryComment(nonexistentId, newCommentWithBodyAndUrlMedia).execute()
        then:
        thrown ArchivalContentException
    }

    def "should delete comment"() {
        when:
        def entryId = wykop.addEntry(newEntryWithBodyAndUrlMedia).execute().id()
        def commentId = wykop.addEntryComment(entryId, newCommentWithBodyAndUrlMedia).execute().id()
        def deletedComment = wykop.deleteEntryComment(commentId).execute()
        then:
        deletedComment.body() == "[Komentarz usunięty]"
        cleanup:
        wykop.deleteEntry(entryId).execute()
    }

    def "should throw an exception when try to delete unreachable comment"() {
        when:
        wykop.deleteEntryComment(nonexistentId).execute()
        then:
        thrown ArchivalContentException
    }

    def "should throw an exception when try to delete a comment which does not belong to user"() {
        when:
        def randomEntryId = wykop.getActiveEntries().execute().get(0).id()
        def randomCommentId = wykop.getEntry(randomEntryId).execute().get().comments().get().get(0).id()
        wykop.deleteEntryComment(randomCommentId).execute()
        then:
        thrown UnableToDeleteCommentException
    }

    def "should vote up entry's comment and remove vote right after"() {
        def conditions = new PollingConditions(timeout: 5, initialDelay: 1)
        when:
        def activeEntryId = wykop.getActiveEntries().execute().get(0).id()
        def entryCommentId = wykop.getEntry(activeEntryId).execute().get().comments().get().get(0).id()
        wykop.voteUpEntryComment(entryCommentId).execute()
        then:
        conditions.eventually {
            assert wykop.getEntryComment(entryCommentId).execute().get().userVote() == UserVote.VOTED
        }
        when:
        wykop.removeVoteFromEntryComment(entryCommentId).execute()
        then:
        conditions.eventually {
            assert wykop.getEntryComment(entryCommentId).execute().get().userVote() == UserVote.NOT_VOTED
        }
    }

    def "should throw an exception when trying to vote up entry where comment id does not exist"() {
        when:
        wykop.voteUpEntryComment(nonexistentId).execute()
        then:
        thrown ArchivalContentException
    }

    def "should throw an exception when trying to remove entry's vote where comment id does not exist"() {
        when:
        wykop.removeVoteFromEntryComment(nonexistentId).execute()
        then:
        thrown ArchivalContentException
    }

    def "should return observed comments"() {
        when:
        def observedComments = wykop.getObservedEntryComments().execute()
        then: "depending on the user's profile - either nothing or all favorites"
        observedComments.isEmpty() || observedComments.stream().allMatch(entryComment -> entryComment.favorite())
    }

    def "should toggle on and off entry favorite"() {
        def activeEntry = wykop.getActiveEntries().execute().get(0)
        def randomId = activeEntry.id()
        def favorite = activeEntry.favorite()
        when:
        def toggle = wykop.toggleEntryFavorite(randomId).execute()
        then:
        toggle != favorite
        cleanup:
        wykop.toggleEntryFavorite(randomId).execute()
    }

    def "should vote on survey"() {
        when:
        Optional<Entry> entryWithSurvey = Optional.empty()
        for (i in 0..<1000) {
            def activeEntries = wykop.getActiveEntries(Page.of(i)).execute()
            entryWithSurvey = activeEntries.stream().filter(e -> e.survey().isPresent()).findFirst()
            if (entryWithSurvey.isPresent()) break
        }
        def id = entryWithSurvey.get().id()
        wykop.answerSurvey(id, 1).execute()
        then:
        wykop.getEntry(id).execute().get().survey().get().userAnswer().get() == 1
    }

    def "should throw an exception when try to answer non-existent survey"() {
        when:
        wykop.answerSurvey(nonexistentId, 1).execute()
        then:
        thrown ArchivalContentException
    }

    def "should throw an exception when try to pass non-existent answer"() {
        when:
        Optional<Entry> entryWithSurvey = Optional.empty()
        for (i in 0..<1000) {
            def activeEntries = wykop.getActiveEntries(Page.of(i)).execute()
            entryWithSurvey = activeEntries.stream().filter(e -> e.survey().isPresent()).findFirst()
            if (entryWithSurvey.isPresent()) break
        }
        def id = entryWithSurvey.get().id()
        wykop.answerSurvey(id, 111111).execute()
        then:
        thrown NiceTryException
    }

    def "should toggle on and off entry's comment favorite"() {
        def activeEntry = wykop.getActiveEntries().execute().get(0)
        def fullEntry = wykop.getEntry(activeEntry.id()).execute()
        def activeEntryComment = fullEntry.get().comments().get().get(0)
        def favorite = activeEntryComment.favorite()
        when:
        def toggle = wykop.toggleEntryCommentFavorite(activeEntryComment.id()).execute()
        then:
        toggle != favorite
        cleanup:
        wykop.toggleEntryCommentFavorite(activeEntryComment.id()).execute()
    }

    def "should throw an exception when such comment does not exist"() {
        when:
        wykop.toggleEntryCommentFavorite(nonexistentId).execute()
        then:
        thrown CommentDoesNotExistException
    }
}
