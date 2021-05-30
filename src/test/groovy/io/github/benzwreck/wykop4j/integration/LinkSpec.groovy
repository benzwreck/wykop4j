package io.github.benzwreck.wykop4j.integration

import io.github.benzwreck.wykop4j.IntegrationWykopClient
import io.github.benzwreck.wykop4j.WykopClient
import io.github.benzwreck.wykop4j.exceptions.ArchivalContentException
import io.github.benzwreck.wykop4j.exceptions.LinkAlreadyExistsException
import io.github.benzwreck.wykop4j.exceptions.LinkCommentNotExistException
import io.github.benzwreck.wykop4j.links.VoteDownReason
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import java.time.Month
import java.time.Year

class LinkSpec extends Specification {
    static final String imageUrl = "https://art-madam.pl/zdjecie/nowoczesny-obraz-do-salonu-drukowany-na-plotnie,swsbnckstxdjnwbh.jpg"
    static final String linkUrl = "https://jsonformatter.org/"
    @Shared
    WykopClient wykop = IntegrationWykopClient.getInstance()
    @Shared
    int linkId = wykop.getPromotedLinks().execute().get(0).id()
    @Shared
    int linkCommentId = wykop.getLinkComments(linkId).execute().get(0).id()
    static int nonexistentId = -111

    def "should throw an exception when trying to prepare link draft and link already exists"() {
        when:
        wykop.prepareLinkDraft("https://www.wykop.pl").execute()
        then:
        thrown LinkAlreadyExistsException
    }

    def "should return link draft"() {
        when:
        def linkDraft = wykop.prepareLinkDraft(linkUrl).execute()
        then:
        with linkDraft, {
            key() != null
            sourceUrl() != null
            !duplicates().isEmpty()
        }
    }

    def "should return image draft"() {
        given:
        def imageKey = wykop.prepareLinkDraft(imageUrl).execute().key()
        when:
        def image = wykop.prepareLinkImage(imageKey).execute().get()
        then:
        with image, {
            key() != null
            type() != null
            previewUrl() != null
            sourceUrl() != null
        }
    }

    @Unroll
    def "should return #name links"() {
        expect:
        links.execute().stream().allMatch(predicate)
        where:
        name       | links                    | predicate
        "promoted" | wykop.getPromotedLinks() | (link) -> link.status() == "promoted"
        "upcoming" | wykop.getUpcomingLinks() | (link) -> link.status() == "upcoming"
    }

    def "should return favorite links"() {
        when:
        def links = wykop.getFavoriteLinks().execute()
        then:
        links.isEmpty() || links.stream().allMatch(link -> link.userFavorite())
    }

    @Unroll
    def "should return #name"() {
        expect:
        link.execute().isPresent() == expected
        where:
        name                 | link                                     | expected
        "non-empty Optional" | wykop.getLink(linkId)                    | true
        "empty Optional"     | wykop.getLink(nonexistentId)             | false
        "non-empty Optional" | wykop.getLinkWithComments(linkId)        | true
        "empty Optional"     | wykop.getLinkWithComments(nonexistentId) | false
    }

    @Unroll
    def "should #name link"() {
        expect:
        with data.execute(), {
            buries() >= 0
            digs() >= 0
        }
        where:
        name          | data
        "upvote"      | wykop.voteUpLink(linkId)
        "remove vote" | wykop.voteRemoveFromLink(linkId)
        "downvote"    | wykop.voteDownLink(linkId, VoteDownReason.DUPLICATE)
        "remove vote" | wykop.voteRemoveFromLink(linkId)
    }

    @Unroll
    def "should thrown an exception when trying to #name link"() {
        when:
        state.execute()
        then:
        thrown(ArchivalContentException)
        where:
        name               | state
        "vote up"          | wykop.voteUpLink(nonexistentId)
        "vote down"        | wykop.voteDownLink(nonexistentId, VoteDownReason.DUPLICATE)
        "remove vote from" | wykop.voteRemoveFromLink(nonexistentId)
    }

    @Unroll
    def "should return list of #name"() {
        expect:
        votes.execute().forEach(vote -> {
            assert vote.author().login() != null
            assert vote.date() != null
        }
        )
        where:
        name        | votes
        "upvotes"   | wykop.getAllUpvotesFromLink(linkId)
        "downvotes" | wykop.getAllDownvotesFromLink(linkId)
    }

    @Unroll
    def "should return top links from #name"() {
        expect:
        links.execute().stream().allMatch(link -> link.isHot())
        where:
        name           | links
        "2020"         | wykop.getTopLinks(Year.of(2020))
        "January 2019" | wykop.getTopLinks(Year.of(2019), Month.JANUARY)
    }

    def "should return list of comments"() {
        when:
        def comments = wykop.getLinkComments(linkId).execute()
        then:
        comments.stream().allMatch(comment -> comment.linkId() == linkId)
    }

    @Unroll
    def "should #name a link comment"() {
        when:
        def linkCommentVoteData = action.execute()
        then:
        with linkCommentVoteData, {
            voteCount() >= 0
            voteCountPlus() >= 0
        }
        where:
        name               | action
        "vote up"          | wykop.voteUpLinkComment(linkId, linkCommentId)
        "vote down"        | wykop.voteDownLinkComment(linkId, linkCommentId)
        "remove vote from" | wykop.removeVoteFromLinkComment(linkId, linkCommentId)
    }

    @Unroll
    def "should throw an exception when trying to #name link's comment"() {
        when:
        state.execute()
        then:
        thrown(LinkCommentNotExistException)
        where:
        name               | state
        "vote up"          | wykop.voteUpLinkComment(nonexistentId, nonexistentId)
        "vote down"        | wykop.voteDownLinkComment(nonexistentId, nonexistentId)
        "remove vote from" | wykop.removeVoteFromLinkComment(nonexistentId, nonexistentId)
    }

    def "should return link's comment"() {
        given:
        def linkId = wykop.getTopLinks(Year.of(2020)).execute().get(0).id()
        def id = wykop.getLinkWithComments(linkId).execute().get().comments().get(0).id()
        when:
        def comment = wykop.getLinkComment(id).execute().get()
        then:
        comment.id() != null
    }

    def "link comment - should return an empty Optional"() {
        when:
        def comment = wykop.getLinkComment(nonexistentId).execute()
        then:
        !comment.isPresent()
    }

    def "should return list of related links"() {
        given:
        def linkId = wykop.getTopLinks(Year.of(2020)).execute().get(0).id()
        when:
        def listOfLinks = wykop.getRelatedLinks(linkId).execute()
        then:
        !listOfLinks.isEmpty()
    }

    def "related links - should return an empty Optional"() {
        when:
        def listOfLinks = wykop.getRelatedLinks(nonexistentId).execute()
        then:
        listOfLinks.isEmpty()
    }

    def "should toggle favorite link"() {
        given:
        def id = wykop.getTopLinks(Year.of(2020)).execute().get(0).id()
        when:
        def value = wykop.toggleLinkFavorite(id).execute()
        then:
        value
    }
}