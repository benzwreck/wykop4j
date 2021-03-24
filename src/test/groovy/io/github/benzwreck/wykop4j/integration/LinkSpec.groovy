package io.github.benzwreck.wykop4j.integration

import io.github.benzwreck.wykop4j.IntegrationWykopClient
import io.github.benzwreck.wykop4j.WykopClient
import io.github.benzwreck.wykop4j.links.Link
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class LinkSpec extends Specification {
    @Shared
    WykopClient wykop = IntegrationWykopClient.getInstance()

    @Unroll
    def "should return #name links"() {
        expect:
        links.execute().stream().allMatch(predicate)
        where:
        name       | links                 | predicate
        "promoted" | wykop.promotedLinks() | (link) -> link.status() == "promoted"
        "upcoming" | wykop.upcomingLinks() | (link) -> link.status() == "upcoming"
    }

    def "should return upcoming links"() {
        when:
        def links = wykop.upcomingLinks().execute()
        then:
        links.stream().allMatch((link) -> link.status() == "upcoming")
    }
}
