package io.github.benzwreck.wykop4j.integration

import io.github.benzwreck.wykop4j.IntegrationWykopClient
import io.github.benzwreck.wykop4j.WykopClient
import io.github.benzwreck.wykop4j.exceptions.ArchivalContentException
import io.github.benzwreck.wykop4j.links.VoteDownReason
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import java.time.Month
import java.time.Year

class LinkSpec extends Specification {
    @Shared
    WykopClient wykop = IntegrationWykopClient.getInstance()
    @Shared
    int linkId = wykop.promotedLinks().execute().get(0).id()
    static int nonexistentId = -111

    @Unroll
    def "should return #name links"() {
        expect:
        links.execute().stream().allMatch(predicate)
        where:
        name       | links                 | predicate
        "promoted" | wykop.promotedLinks() | (link) -> link.status() == "promoted"
        "upcoming" | wykop.upcomingLinks() | (link) -> link.status() == "upcoming"
    }

    def "should return favorite links"() {
        when:
        def links = wykop.favoriteLinks().execute()
        then:
        links.isEmpty() || links.stream().allMatch(link -> link.userFavorite())
    }

    @Unroll
    def "should return #name"() {
        expect:
        link.execute().isPresent() == expected
        where:
        name                 | link                                  | expected
        "non-empty Optional" | wykop.link(linkId)                    | true
        "empty Optional"     | wykop.link(nonexistentId)             | false
        "non-empty Optional" | wykop.linkWithComments(linkId)        | true
        "empty Optional"     | wykop.linkWithComments(nonexistentId) | false
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
        "upvote"      | wykop.linkVoteUp(linkId)
        "remove vote" | wykop.linkVoteRemove(linkId)
        "downvote"    | wykop.linkVoteDown(linkId, VoteDownReason.DUPLICATE)
        "remove vote" | wykop.linkVoteRemove(linkId)
    }

    @Unroll
    def "should thrown an exception"() {
        when:
        state.execute()
        then:
        thrown(ArchivalContentException)
        where:
        state                                                       | _
        wykop.linkVoteUp(nonexistentId)                             | _
        wykop.linkVoteRemove(nonexistentId)                         | _
        wykop.linkVoteDown(nonexistentId, VoteDownReason.DUPLICATE) | _
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
        "upvotes"   | wykop.linkAllUpvotes(linkId)
        "downvotes" | wykop.linkAllDownvotes(linkId)
    }

    @Unroll
    def "should return top links from #name"() {
        expect:
        links.execute().stream().allMatch(link -> link.isHot())
        where:
        name           | links
        "2020"         | wykop.linkTop(Year.of(2020))
        "January 2019" | wykop.linkTop(Year.of(2019), Month.JANUARY)
    }
}