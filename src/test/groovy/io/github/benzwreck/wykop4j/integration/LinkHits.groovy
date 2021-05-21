package io.github.benzwreck.wykop4j.integration

import io.github.benzwreck.wykop4j.IntegrationWykopClient
import io.github.benzwreck.wykop4j.WykopClient
import io.github.benzwreck.wykop4j.links.HitsOption
import spock.lang.Specification

import java.time.LocalDateTime
import java.time.LocalTime
import java.time.Month
import java.time.Year
import java.time.YearMonth

class LinkHits extends Specification {
    WykopClient wykop = IntegrationWykopClient.getInstance()

    def "should return popular link hits"() {
        when:
        def popular = wykop.getHitLinks(HitsOption.POPULAR).execute()
        then:
        popular.stream().allMatch(link -> link.isHot())
    }

    def "should return popular links for last day"() {
        when:
        def links = wykop.getHitLinks(HitsOption.DAY).execute()
        LocalDateTime dayBefore = LocalDateTime.now().minusDays(1)
        then:
        links.stream().allMatch(link -> link.date().isAfter(dayBefore))
    }

    def "should return popular links for last week"() {
        when:
        def links = wykop.getHitLinks(HitsOption.WEEK).execute()
        LocalDateTime weekBefore = LocalDateTime.now().minusDays(7)
        then:
        links.stream().allMatch(link -> link.date().isAfter(weekBefore))
    }

    def "should return popular links for last month"() {
        when:
        def links = wykop.getHitLinks(HitsOption.MONTH).execute()
        LocalDateTime monthBefore = LocalDateTime.now().minusMonths(1)
        then:
        links.stream().allMatch(link -> link.date().isAfter(monthBefore))
    }

    def "should return popular links for last year"() {
        when:
        def links = wykop.getHitLinks(HitsOption.YEAR).execute()
        LocalDateTime monthBefore = LocalDateTime.now().minusYears(1)
        then:
        links.stream().allMatch(link -> link.date().isAfter(monthBefore))
    }

    def "should return popular links for a current month"() {
        when:
        def currentMonthLinks = wykop.getHitLinks(LocalDateTime.now().getMonth()).execute()
        LocalDateTime firstDay = LocalDateTime.of(YearMonth.now().atDay(1), LocalTime.MIN)
        LocalDateTime lastDay = LocalDateTime.of(YearMonth.now().atEndOfMonth(), LocalTime.MAX)
        then:
        currentMonthLinks.stream().allMatch(
                link -> link.date().isAfter(firstDay) && link.date().isBefore(lastDay))
    }

    def "should return popular links for a specific month"() {
        when:
        def januaryHits = wykop.getHitLinks(Month.JANUARY).execute()
        def yearMonth = YearMonth.of(Year.now().getValue(), Month.JANUARY)
        LocalDateTime firstDay = LocalDateTime.of(yearMonth.atDay(1), LocalTime.MIN)
        LocalDateTime lastDay = LocalDateTime.of(yearMonth.atEndOfMonth(), LocalTime.MAX)
        then:
        januaryHits.stream().allMatch(
                link -> link.date().isAfter(firstDay) && link.date().isBefore(lastDay))
    }

    def "should return popular links for a specific year"() {
        when:
        def lastYearHits = wykop.getHitLinks(Year.of(2018)).execute()
        def firstDay = LocalDateTime.of(2018, 1, 1, 0, 0, 0)
        def lastDay = LocalDateTime.of(2018, 12, 31, 23, 59, 59)
        then:
        lastYearHits.stream().allMatch(
                link -> link.date().isAfter(firstDay) && link.date().isBefore(lastDay))
    }

    def "should return empty list of popular links for a non-existing year"() {
        when:
        def lastYearHits = wykop.getHitLinks(Year.of(-2018)).execute()
        then:
        lastYearHits.isEmpty()
    }

    def "should return popular links for a specific month and year"() {
        when:
        def lastYearHits = wykop.getHitLinks(Month.JANUARY, Year.of(2018)).execute()
        def firstDay = LocalDateTime.of(2018, 1, 1, 0, 0, 0)
        def lastDay = LocalDateTime.of(2018, 1, 31, 23, 59, 59)
        then:
        lastYearHits.stream().allMatch(
                link -> link.date().isAfter(firstDay) && link.date().isBefore(lastDay))
    }
}
