package io.github.benzwreck.wykop4j.integration

import io.github.benzwreck.wykop4j.IntegrationWykopClient
import io.github.benzwreck.wykop4j.WykopClient
import io.github.benzwreck.wykop4j.profiles.Color
import spock.lang.Specification

class ProfileSpec extends Specification {
    WykopClient wykop = IntegrationWykopClient.getInstance()
    String nonexistentLogin = UUID.randomUUID().toString()
    String adminLogin = "m__b"

    def "should return existing user profile"() {
        when:
        def profile = wykop.profile(adminLogin).execute().get()
        then:
        profile.login() == adminLogin
        profile.color() == Color.ADMIN
    }

    def "should return empty Optional"() {
        when:
        def profile = wykop.profile(nonexistentLogin).execute()
        then:
        profile == Optional.empty()
    }

    def "should fetch, parse and map to object"() {
        when:
        wykop.profileActions(adminLogin).execute()
        then:
        noExceptionThrown()
    }

    def "should return user's added links"() {
        when:
        def links = wykop.profileAddedLinks(adminLogin).execute()
        then:
        links.stream().allMatch(link -> link.author().login() == adminLogin)
    }

    def "should return an empty list"() {
        when:
        def links = wykop.profileAddedLinks(nonexistentLogin).execute()
        then:
        links.isEmpty()
    }
}
