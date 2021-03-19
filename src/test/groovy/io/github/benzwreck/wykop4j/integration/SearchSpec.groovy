package io.github.benzwreck.wykop4j.integration

import io.github.benzwreck.wykop4j.IntegrationWykopClient
import io.github.benzwreck.wykop4j.WykopClient
import io.github.benzwreck.wykop4j.search.SearchQuery
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class SearchSpec extends Specification {
    @Shared
    WykopClient wykop = IntegrationWykopClient.getInstance()
    static String phrase = "zycie"
    static SearchQuery searchQuery = new SearchQuery.Builder()
            .phrase(phrase)
            .build()

    @Unroll
    def "should return list of searched #name"() {
        expect:
        action.execute().stream().allMatch(predicate)
        where:
        name       | action                           | predicate
        "entries"  | wykop.searchEntries(searchQuery) | (entry) -> entry.body().get().toLowerCase().contains(phrase)
        "links"    | wykop.searchLinks(searchQuery)   | (link) -> link.description().toLowerCase().contains(phrase)
        "profiles" | wykop.searchProfiles("m__b")     | (profile) -> profile.login().contains("m__b")
    }

    @Unroll
    def "should search for different types"() {
        expect:
        wykop.searchLinks(searchQuery).execute().stream().allMatch(predicate)
        where:
        searchQuery                           | predicate
        withType(SearchQuery.Type.ALL)        | (link) -> !link.status().isEmpty()
        withType(SearchQuery.Type.PROMOTED)   | (link) -> link.status() == "promoted"
        withType(SearchQuery.Type.ARCHIVED)   | (link) -> link.status() == "upcoming"
        withType(SearchQuery.Type.DUPLICATES) | (link) -> link.status() == "rejected"
    }

    private SearchQuery withType(SearchQuery.Type type) {
        new SearchQuery.Builder().type(type).build()
    }
}
