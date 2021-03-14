package io.github.benzwreck.wykop4j.integration

import io.github.benzwreck.wykop4j.IntegrationWykopClient
import io.github.benzwreck.wykop4j.WykopClient
import io.github.benzwreck.wykop4j.profiles.Color
import spock.lang.Specification
import spock.lang.Unroll

class ProfileSpec extends Specification {
    static WykopClient wykop = IntegrationWykopClient.getInstance()
    static String nonexistentLogin = UUID.randomUUID().toString()
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
        wykop.profileCommentedLinks(adminLogin).execute()
        wykop.profileLinksComments(adminLogin).execute()
        wykop.profileLinksPublished(adminLogin).execute()
        then:
        noExceptionThrown()
    }

    def "should return user's added links"() {
        when:
        def links = wykop.profileAddedLinks(adminLogin).execute()
        then:
        links.stream().allMatch(link -> link.author().login() == adminLogin)
    }

    @Unroll
    def "should return an empty list"() {
        expect:
        links.isEmpty()
        where:
        links                                                   | _
        wykop.profileAddedLinks(nonexistentLogin).execute()     | _
        wykop.profileCommentedLinks(nonexistentLogin).execute() | _
        wykop.profileLinksComments(nonexistentLogin).execute()  | _
        wykop.profileLinksPublished(nonexistentLogin).execute() | _
    }
}
