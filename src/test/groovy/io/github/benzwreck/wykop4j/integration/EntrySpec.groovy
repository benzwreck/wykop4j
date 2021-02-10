package io.github.benzwreck.wykop4j.integration

import io.github.benzwreck.wykop4j.IntegrationWykopClient
import io.github.benzwreck.wykop4j.WykopClient
import io.github.benzwreck.wykop4j.entries.Page
import io.github.benzwreck.wykop4j.entries.Period
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class EntrySpec extends Specification {
    WykopClient wykop = IntegrationWykopClient.getInstance()

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

    def "should return list of entries where first entry is the second one from all entries' stream"() {
        when:
        def entriesStream = wykop.entriesStream().execute()
        def execute = wykop.entriesStream(entriesStream.get(0).id()).execute()
        then:
        execute.get(0).id() == entriesStream.get(1).id()
    }

    def "should return entry"() {
        when:
        def entry = wykop.entry(54760047).execute()
        then:
        54760047 == entry.get().id()
    }

    def "should return an empty Optional - entry id doesn't exist"() {
        when:
        def entry = wykop.entry(-1000000).execute()
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
}
