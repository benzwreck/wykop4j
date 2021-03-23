package io.github.benzwreck.wykop4j.integration

import io.github.benzwreck.wykop4j.IntegrationWykopClient
import io.github.benzwreck.wykop4j.WykopClient
import io.github.benzwreck.wykop4j.links.Link
import io.github.benzwreck.wykop4j.search.DateRange
import io.github.benzwreck.wykop4j.search.EntrySearchQuery
import io.github.benzwreck.wykop4j.search.LinkSearchQuery
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDateTime

class SearchSpec extends Specification {
    @Shared
    WykopClient wykop = IntegrationWykopClient.getInstance()
    static String adminLogin = "m__b"
    static String phrase = "zycie"
    static LinkSearchQuery linkSearchQuery = new LinkSearchQuery.Builder()
            .phrase(phrase)
            .build()
    static EntrySearchQuery entrySearchQuery = new EntrySearchQuery.Builder()
            .phrase(phrase)
            .build()

    @Unroll
    def "should return list of searched #name"() {
        expect:
        action.execute().stream().allMatch(predicate)
        where:
        name       | action                                | predicate
        "entries"  | wykop.searchEntries(entrySearchQuery) | (entry) -> entry.body().get().toLowerCase().contains(phrase)
        "entries"  | wykop.searchEntries(phrase)           | (entry) -> entry.body().get().toLowerCase().contains(phrase)
        "links"    | wykop.searchLinks(phrase)             | (link) -> link.description().toLowerCase().contains(phrase)
        "links"    | wykop.searchLinks(linkSearchQuery)    | (link) -> link.description().toLowerCase().contains(phrase)
        "profiles" | wykop.searchProfiles(adminLogin)      | (profile) -> profile.login().contains(adminLogin)
    }

    @Unroll
    def "should search for link types"() {
        expect:
        wykop.searchLinks(searchQuery).execute().stream().allMatch(predicate)
        where:
        searchQuery                                   | predicate
        linkWithType(LinkSearchQuery.Type.ALL)        | (link) -> !link.status().isEmpty()
        linkWithType(LinkSearchQuery.Type.PROMOTED)   | (link) -> link.status() == "promoted"
        linkWithType(LinkSearchQuery.Type.ARCHIVED)   | (link) -> link.status() == "upcoming"
        linkWithType(LinkSearchQuery.Type.DUPLICATES) | (link) -> link.status() == "rejected"
    }

    @Unroll
    def "should return entry results in given date range"() {
        expect:
        wykop.searchEntries(searchQuery).execute().stream().map(entry -> entry.date())
                .allMatch(date -> date.isAfter(after))
        where:
        searchQuery                             | after
        entryWithDateRange(DateRange.TODAY)     | LocalDateTime.now().minusDays(1)
        entryWithDateRange(DateRange.YESTERDAY) | LocalDateTime.now().minusDays(2)
        entryWithDateRange(DateRange.WEEK)      | LocalDateTime.now().minusDays(7)
        entryWithDateRange(DateRange.MONTH)     | LocalDateTime.now().minusMonths(1)
    }

    @Unroll
    def "should return link results in given date range"() {
        expect:
        wykop.searchLinks(searchQuery).execute().stream().map(link -> link.date())
                .allMatch(date -> date.isAfter(after))
        where:
        searchQuery                            | after
        linkWithDateRange(DateRange.TODAY)     | LocalDateTime.now().minusDays(1)
        linkWithDateRange(DateRange.YESTERDAY) | LocalDateTime.now().minusDays(2)
        linkWithDateRange(DateRange.WEEK)      | LocalDateTime.now().minusDays(7)
        linkWithDateRange(DateRange.MONTH)     | LocalDateTime.now().minusMonths(1)
    }

    @Unroll
    def "should return link results sorted by #name"() {
        when:
        List<Link> links = wykop.searchLinks(searchQuery).execute()
        then:
        List<Link> mutableList = new ArrayList<>(links)
        Link prev = mutableList.get(0)
        mutableList.tap { remove(0) }.forEach(link -> {
            assert result(prev, link)
            prev = link
        })
        where:
        name         | searchQuery                                      | result
        "diggs"      | linkWithSortingBy(LinkSearchQuery.Sorting.DIGGS) | (link1, link2) -> link1.voteCount() >= link2.voteCount()
        "the newest" | linkWithSortingBy(LinkSearchQuery.Sorting.NEW)   | (link1, link2) -> link1.date().isAfter(link2.date())
    }

    private EntrySearchQuery entryWithDateRange(DateRange dateRange) {
        new EntrySearchQuery.Builder().phrase(phrase).dateRange(dateRange).build()
    }

    private LinkSearchQuery linkWithDateRange(DateRange dateRange) {
        new LinkSearchQuery.Builder().phrase(phrase).dateRange(dateRange).build()
    }

    private LinkSearchQuery linkWithType(LinkSearchQuery.Type type) {
        new LinkSearchQuery.Builder().type(type).build()
    }

    private LinkSearchQuery linkWithSortingBy(LinkSearchQuery.Sorting sorting) {
        new LinkSearchQuery.Builder().phrase(phrase).sorting(sorting).build()
    }
}
