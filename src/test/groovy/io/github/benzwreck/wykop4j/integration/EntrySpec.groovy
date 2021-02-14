package io.github.benzwreck.wykop4j.integration

import io.github.benzwreck.wykop4j.IntegrationWykopClient
import io.github.benzwreck.wykop4j.WykopClient
import io.github.benzwreck.wykop4j.entries.NewComment
import io.github.benzwreck.wykop4j.entries.NewEntry
import io.github.benzwreck.wykop4j.entries.Page
import io.github.benzwreck.wykop4j.entries.Period
import io.github.benzwreck.wykop4j.entries.UserVote
import io.github.benzwreck.wykop4j.exceptions.ArchivalContentException
import io.github.benzwreck.wykop4j.exceptions.UnableToModifyEntryException
import spock.lang.Specification
import spock.lang.Unroll
import spock.util.concurrent.PollingConditions

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class EntrySpec extends Specification {
    WykopClient wykop = IntegrationWykopClient.getInstance()
    private final int nonexistingId = -111111
    private final def newEntryWithBodyAndUrlMedia = new NewEntry.Builder()
            .withBody("obraz")
            .withMedia("https://www.wykop.pl/cdn/c3201142/comment_1613001626Mwe2NcUAMJ1yLKZJumQQjC.jpg")
            .build()
    private final def newCommentWithBodyAndUrlMedia = new NewComment.Builder()
            .withBody("obraz")
            .withMedia("https://www.wykop.pl/cdn/c3201142/comment_1613001626Mwe2NcUAMJ1yLKZJumQQjC.jpg")
            .build()

    def "should return first and second page of entries' stream"() {
        when:
        def entries1 = wykop.entriesStream(Page.of(1)).execute()
        def entries2 = wykop.entriesStream(Page.of(2)).execute()

        then:
        def allEntries = entries1 + entries2
        for (i in 0..<allEntries.size() - 1) {
            allEntries[i].id() > allEntries[i + 1].id()
        }
    }

    def "should return list of entries where first entry is the next one of all entries' stream"() {
        when:
        def entriesStream = wykop.entriesStream().execute()
        def execute = wykop.entriesStream(entriesStream.get(0).id()).execute()
        then:
        execute.get(0).id() <= entriesStream.get(0).id()
    }

    def "should return entry"() {
        when:
        def randomEntryId = wykop.hotEntries().execute().get(0).id()
        def entry = wykop.entry(randomEntryId).execute()
        then:
        randomEntryId == entry.get().id()
    }

    def "should return an empty Optional - entry id doesn't exist"() {
        when:
        def entry = wykop.entry(nonexistingId).execute()
        then:
        entry == Optional.empty()
    }

    @Unroll
    def "should return first #period.value() hours hot page"() {
        expect:
        wykop.hotEntries(period).execute()
                .stream()
                .map(entry -> entry.date())
                .filter(date -> date.until(LocalDateTime.now(), ChronoUnit.HOURS) > period.value())
                .count() == 0

        where:
        period << [Period.SIX_HOURS, Period.TWELVE_HOURS, Period.TWENTY_FOUR_HOURS]

    }

    def "should return list of active entries from first page"() {
        when:
        def entries = wykop.activeEntries().execute()
        then: "active entries must contain comments"
        entries.each { it.commentsCount() > 0 }
    }

    def "should return empty list of active entries from non-existent page"() {
        when:
        def entries = wykop.activeEntries(Page.of(10000)).execute()
        then:
        entries.isEmpty()
    }

    def "should return list of observed entries from first page"() {
        when:
        def entries = wykop.observedEntries().execute()
        then:
        entries.each { it.favorite() }
    }

    def "should return empty list of observed entries from non-existent page"() {
        when:
        def entries = wykop.observedEntries(Page.of(10000)).execute()
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
        def randomId = wykop.entriesStream().execute().get(0).id()
        when:
        wykop.deleteEntry(randomId).execute()

        then:
        thrown ArchivalContentException
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
        def randomId = wykop.entriesStream().execute().get(0).id()
        when:
        wykop.voteUp(randomId).execute()
        then:
        conditions.eventually {
            assert wykop.entry(randomId).execute()
                    .map(e -> e.userVote())
                    .filter(userVote -> userVote == UserVote.VOTED)
                    .isPresent()
        }

        when:
        wykop.removeVote(randomId).execute()
        then:
        conditions.eventually {
            assert wykop.entry(randomId).execute()
                    .map(e -> e.userVote())
                    .filter(userVote -> userVote == UserVote.NOT_VOTED)
                    .isPresent()
        }
    }

    def "should throw an exception when try to upvote non-existing entry"() {
        when:
        wykop.voteUp(nonexistingId).execute()

        then:
        thrown ArchivalContentException
    }

    def "should throw an exception when try to remove vote from non-existing entry"() {
        when:
        wykop.removeVote(nonexistingId).execute()

        then:
        thrown ArchivalContentException
    }

    def "should return list of upvoters"() {
        setup:
        def randomId = wykop.hotEntries().execute().get(0).id()
        when:
        def votes = wykop.allUpvotes(randomId).execute()
        then:
        !votes.isEmpty()
    }

    def "should return comment"() {
        setup:
        def hotEntries = wykop.hotEntries().execute()
        def entry = wykop.entry(hotEntries.get(0).id()).execute()
        def id = entry.get().comments().get().get(0).id()
        when:
        def comment = wykop.entryComment(id).execute()
        then:
        comment.isPresent()
    }

    def "should return empty Optional when given non-existent entry's id"() {
        when:
        def comment = wykop.entryComment(nonexistingId).execute()
        then:
        !comment.isPresent()
    }

    def "should add comment to entry"() {
        when:
        def addEntryId = wykop.addEntry(newEntryWithBodyAndUrlMedia).execute().id()
        def comment = wykop.addEntryComment(addEntryId, newCommentWithBodyAndUrlMedia).execute()
        then:
        wykop.entry(addEntryId).execute()
                .flatMap(e -> e.comments())
                .flatMap(list -> list.stream().filter(c -> c.id() == comment.id()).findFirst())
                .isPresent()
        cleanup:
        wykop.deleteEntry(addEntryId).execute()
    }

    def "should throw an exception when new comment is added to non-existing entry"() {
        when:
        wykop.addEntryComment(nonexistingId, newCommentWithBodyAndUrlMedia).execute()
        then:
        thrown ArchivalContentException
    }


}
