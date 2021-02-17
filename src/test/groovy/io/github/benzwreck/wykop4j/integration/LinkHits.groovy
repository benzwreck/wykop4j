package io.github.benzwreck.wykop4j.integration

import io.github.benzwreck.wykop4j.IntegrationWykopClient
import io.github.benzwreck.wykop4j.WykopClient
import io.github.benzwreck.wykop4j.links.HitsOption
import spock.lang.Specification

import java.time.LocalDateTime

class LinkHits extends Specification {
    WykopClient wykop = IntegrationWykopClient.getInstance()

    def "should return popular link hits"() {
        when:
        def popular = wykop.linkHits(HitsOption.POPULAR).execute()
        then:
        popular.stream().allMatch(link -> link.isHot())
    }

    def "should return popular links for last day"() {
        when:
        def links = wykop.linkHits(HitsOption.DAY).execute()
        LocalDateTime dayBefore = LocalDateTime.now().minusDays(1)
        then:
        links.stream().allMatch(link -> link.date().isAfter(dayBefore))
    }

    def "should return popular links for last week"() {
        when:
        def links = wykop.linkHits(HitsOption.WEEK).execute()
        LocalDateTime weekBefore = LocalDateTime.now().minusDays(7)
        then:
        links.stream().allMatch(link -> link.date().isAfter(weekBefore))
    }

    def "should return popular links for last month"() {
        when:
        def links = wykop.linkHits(HitsOption.MONTH).execute()
        LocalDateTime monthBefore = LocalDateTime.now().minusMonths(1)
        then:
        links.stream().allMatch(link -> link.date().isAfter(monthBefore))
    }

    def "should return popular links for last year"() {
        when:
        def links = wykop.linkHits(HitsOption.YEAR).execute()
        LocalDateTime monthBefore = LocalDateTime.now().minusYears(1)
        then:
        links.stream().allMatch(link -> link.date().isAfter(monthBefore))
    }
}
