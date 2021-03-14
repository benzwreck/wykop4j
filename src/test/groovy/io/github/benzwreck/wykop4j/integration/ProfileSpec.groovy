package io.github.benzwreck.wykop4j.integration

import io.github.benzwreck.wykop4j.IntegrationWykopClient
import io.github.benzwreck.wykop4j.WykopClient
import io.github.benzwreck.wykop4j.profiles.Color
import spock.lang.Specification
import spock.lang.Unroll

class ProfileSpec extends Specification {
    static WykopClient wykop = IntegrationWykopClient.getInstance()
    static String nonexistentLogin = UUID.randomUUID().toString()
    static String adminLogin = "m__b"

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

    @Unroll
    def "should fetch, parse and map to object"() {
        expect:
        !(result in Throwable)
        where:
        result                                              | _
        wykop.profileActions(adminLogin).execute()          | _
        wykop.profileCommentedLinks(adminLogin).execute()   | _
        wykop.profileLinksComments(adminLogin).execute()    | _
        wykop.profileLinksPublished(adminLogin).execute()   | _
        wykop.profileLinksPublished(adminLogin).execute()   | _
        wykop.profileEntriesCommented(adminLogin).execute() | _
        wykop.profileEntriesComments(adminLogin).execute()  | _
        wykop.profileRelatedLinks(adminLogin).execute()     | _
        wykop.profileFollowers(adminLogin).execute()        | _
        wykop.profileFollowed(adminLogin).execute()         | _
        wykop.profileBadges(adminLogin).execute()           | _
        wykop.profileDiggedLinks(adminLogin).execute()      | _
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
        links                                                     | _
        wykop.profileAddedLinks(nonexistentLogin).execute()       | _
        wykop.profileCommentedLinks(nonexistentLogin).execute()   | _
        wykop.profileLinksComments(nonexistentLogin).execute()    | _
        wykop.profileLinksPublished(nonexistentLogin).execute()   | _
        wykop.profileLinksPublished(nonexistentLogin).execute()   | _
        wykop.profileEntriesCommented(nonexistentLogin).execute() | _
        wykop.profileEntriesComments(nonexistentLogin).execute()  | _
        wykop.profileRelatedLinks(nonexistentLogin).execute()     | _
        wykop.profileFollowers(nonexistentLogin).execute()        | _
        wykop.profileFollowed(nonexistentLogin).execute()         | _
        wykop.profileBadges(nonexistentLogin).execute()           | _
        wykop.profileDiggedLinks(nonexistentLogin).execute()      | _
    }
}
