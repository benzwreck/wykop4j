package io.github.benzwreck.wykop4j.integration

import io.github.benzwreck.wykop4j.IntegrationWykopClient
import io.github.benzwreck.wykop4j.WykopClient
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class TagSpec extends Specification {
    @Shared
    WykopClient wykop = IntegrationWykopClient.getInstance()
    private static final String nonexistentTag = "ponsaddnasdnsaodnasodnodnaonandoanoan"
    private static final String wykopTag = "wykop"

    def "should return non-empty actions"() {
        when:
        def actions = wykop.tagActions(wykopTag).execute()
        then:
        !actions.entries().isEmpty() || !actions.links().isEmpty()
    }

    def "should return an empty actions"() {
        when:
        def actions = wykop.tagActions(nonexistentTag).execute()
        then:
        actions.entries().isEmpty() && actions.links().isEmpty()
    }

    @Unroll
    def "should return non-empty list"() {
        expect:
        !result.execute().isEmpty()
        where:
        result                     | _
        wykop.tagLinks(wykopTag)   | _
        wykop.tagEntries(wykopTag) | _
    }

    @Unroll
    def "should return empty list"() {
        expect:
        result.execute().isEmpty()
        where:
        result                           | _
        wykop.tagLinks(nonexistentTag)   | _
        wykop.tagEntries(nonexistentTag) | _
    }

    def "should observe tag"(){
        expect:
        with wykop.observeTag(wykopTag).execute(), {
            isObserved()
            !isBlocked()
        }

    }

}
