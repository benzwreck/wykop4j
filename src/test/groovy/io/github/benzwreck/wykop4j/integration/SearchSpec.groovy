package io.github.benzwreck.wykop4j.integration

import io.github.benzwreck.wykop4j.IntegrationWykopClient
import io.github.benzwreck.wykop4j.WykopClient
import io.github.benzwreck.wykop4j.search.SearchQuery
import spock.lang.Specification
import spock.lang.Unroll

class SearchSpec extends Specification {
    WykopClient wykop = IntegrationWykopClient.getInstance()

    def "should return list of searched links"() {
        given:
        String phrase = "zycie"
        SearchQuery searchQuery = new SearchQuery.Builder()
                .phrase(phrase)
                .build()
        when:
        def links = wykop.searchLinks(searchQuery).execute()
        then:
        links.stream().allMatch(link -> link.description().toLowerCase().contains(phrase))
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
