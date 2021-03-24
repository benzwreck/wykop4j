package io.github.benzwreck.wykop4j.integration

import io.github.benzwreck.wykop4j.IntegrationWykopClient
import io.github.benzwreck.wykop4j.WykopClient
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

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
    def "should vote up link"() {
        when:
        def data = wykop.linkVoteUp(linkId).execute()
        then:
        data.buries() != 0
        data.digs() != 0
    }
}
