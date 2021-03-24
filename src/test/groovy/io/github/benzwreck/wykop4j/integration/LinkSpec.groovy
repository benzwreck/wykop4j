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
    int linkId = wykop.upcomingLinks().execute().get(0).id()
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

    def "should return link"() {
        when:
        def link = wykop.link(linkId).execute()
        then:
        link.isPresent()
    }

    def "should return link with comments"() {
        when:
        def link = wykop.linkWithComments(linkId).execute()
        then:
        link.isPresent()
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
}
