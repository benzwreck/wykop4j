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
        def profile = wykop.getProfile(adminLogin).execute().get()
        then:
        profile.login() == adminLogin
        profile.color() == Color.ADMIN
    }

    def "should return empty Optional"() {
        when:
        def profile = wykop.getProfile(nonexistentLogin).execute()
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
        result                                       | _
        wykop.getProfileActions(adminLogin)          | _
        wykop.getProfileCommentedLinks(adminLogin)   | _
        wykop.getProfileLinksComments(adminLogin)    | _
        wykop.getProfilePublishedLinks(adminLogin)   | _
        wykop.getProfilePublishedLinks(adminLogin)   | _
        wykop.getProfileCommentedEntries(adminLogin) | _
        wykop.getProfileEntriesComments(adminLogin)  | _
        wykop.getProfileRelatedLinks(adminLogin)     | _
        wykop.getProfileFollowers(adminLogin)        | _
        wykop.getProfileFollowed(adminLogin)         | _
        wykop.getProfileBadges(adminLogin)           | _
        wykop.getProfileDiggedLinks(adminLogin)      | _
        wykop.getProfileBuriedLinks(adminLogin)      | _
        wykop.getProfileRanking()                    | _
    }

    def "should return user's added links"() {
        when:
        def links = wykop.getProfileAddedLinks(adminLogin).execute()
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
        name        | action                                  || reaction
        "observe"   | wykop.observeUser(secondAccountLogin)   || new InteractionStatus(true, false)
        "unobserve" | wykop.unobserveUser(secondAccountLogin) || new InteractionStatus(false, false)
        "block"     | wykop.blockUser(secondAccountLogin)     || new InteractionStatus(false, true)
        "unblock"   | wykop.unblockUser(secondAccountLogin)   || new InteractionStatus(false, false)
    }

    @Unroll
    def "should return an empty list"() {
        expect:
        links.execute().isEmpty()
        where:
        links                                              | _
        wykop.getProfileAddedLinks(nonexistentLogin)       | _
        wykop.getProfileCommentedLinks(nonexistentLogin)   | _
        wykop.getProfileLinksComments(nonexistentLogin)    | _
        wykop.getProfilePublishedLinks(nonexistentLogin)   | _
        wykop.getProfilePublishedLinks(nonexistentLogin)   | _
        wykop.getProfileCommentedEntries(nonexistentLogin) | _
        wykop.getProfileEntriesComments(nonexistentLogin)  | _
        wykop.getProfileRelatedLinks(nonexistentLogin)     | _
        wykop.getProfileFollowers(nonexistentLogin)        | _
        wykop.getProfileFollowed(nonexistentLogin)         | _
        wykop.getProfileBadges(nonexistentLogin)           | _
        wykop.getProfileDiggedLinks(nonexistentLogin)      | _
        wykop.getProfileBuriedLinks(nonexistentLogin)      | _
    }
}
