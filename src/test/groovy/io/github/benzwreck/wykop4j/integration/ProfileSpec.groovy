package io.github.benzwreck.wykop4j.integration

import io.github.benzwreck.wykop4j.IntegrationData
import io.github.benzwreck.wykop4j.IntegrationWykopClient
import io.github.benzwreck.wykop4j.TwoAccounts
import io.github.benzwreck.wykop4j.WykopClient
import io.github.benzwreck.wykop4j.profiles.Color
import io.github.benzwreck.wykop4j.profiles.InteractionStatus
import spock.lang.Specification
import spock.lang.Unroll

class ProfileSpec extends Specification {
    static WykopClient wykop = IntegrationWykopClient.getInstance()
    static String nonexistentLogin = UUID.randomUUID().toString()
    static String secondAccountLogin = IntegrationData.secondAccountLogin
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
        when:
        result.execute()
        then:
        noExceptionThrown()
        where:
        result                                    | _
        wykop.profileActions(adminLogin)          | _
        wykop.profileCommentedLinks(adminLogin)   | _
        wykop.profileLinksComments(adminLogin)    | _
        wykop.profileLinksPublished(adminLogin)   | _
        wykop.profileLinksPublished(adminLogin)   | _
        wykop.profileEntriesCommented(adminLogin) | _
        wykop.profileEntriesComments(adminLogin)  | _
        wykop.profileRelatedLinks(adminLogin)     | _
        wykop.profileFollowers(adminLogin)        | _
        wykop.profileFollowed(adminLogin)         | _
        wykop.profileBadges(adminLogin)           | _
        wykop.profileDiggedLinks(adminLogin)      | _
        wykop.profileBuriedLinks(adminLogin)      | _
        wykop.profileRanking()                    | _
    }

    def "should return user's added links"() {
        when:
        def links = wykop.profileAddedLinks(adminLogin).execute()
        then:
        links.stream().allMatch(link -> link.author().login() == adminLogin)
    }

    @TwoAccounts
    @Unroll
    def "should #name user"() {
        expect:
        with action.execute(), {
            reaction.isObserved() == isObserved()
            reaction.isBlocked() == isBlocked()
        }
        where:
        name        | action                              || reaction
        "observe"   | wykop.observeUser(secondAccountLogin) || new InteractionStatus(true, false)
        "unobserve" | wykop.unobserve(secondAccountLogin) || new InteractionStatus(false, false)
        "block"     | wykop.block(secondAccountLogin)     || new InteractionStatus(false, true)
        "unblock"   | wykop.unblock(secondAccountLogin)   || new InteractionStatus(false, false)
    }

    @Unroll
    def "should return an empty list"() {
        expect:
        links.execute().isEmpty()
        where:
        links                                           | _
        wykop.profileAddedLinks(nonexistentLogin)       | _
        wykop.profileCommentedLinks(nonexistentLogin)   | _
        wykop.profileLinksComments(nonexistentLogin)    | _
        wykop.profileLinksPublished(nonexistentLogin)   | _
        wykop.profileLinksPublished(nonexistentLogin)   | _
        wykop.profileEntriesCommented(nonexistentLogin) | _
        wykop.profileEntriesComments(nonexistentLogin)  | _
        wykop.profileRelatedLinks(nonexistentLogin)     | _
        wykop.profileFollowers(nonexistentLogin)        | _
        wykop.profileFollowed(nonexistentLogin)         | _
        wykop.profileBadges(nonexistentLogin)           | _
        wykop.profileDiggedLinks(nonexistentLogin)      | _
        wykop.profileBuriedLinks(nonexistentLogin)      | _
    }
}
