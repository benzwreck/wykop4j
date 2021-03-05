package io.github.benzwreck.wykop4j.mapping

import io.github.benzwreck.wykop4j.WykopMappingTestObject
import io.github.benzwreck.wykop4j.profiles.Actions
import io.github.benzwreck.wykop4j.profiles.Color
import io.github.benzwreck.wykop4j.profiles.FullProfile
import io.github.benzwreck.wykop4j.profiles.Sex
import spock.lang.Specification

import java.time.LocalDateTime

class ProfileMappingSpec extends Specification {
    WykopMappingTestObject mapper = new WykopMappingTestObject()
    SampleProfiles sampleProfile = new SampleProfiles()

    def "should map json to profile"() {
        given:
        FullProfile fullProfile = mapper.map(sampleProfile.adminProfile, FullProfile)
        expect:
        with fullProfile, {
            login() == "m__b"
            color() == Color.ADMIN
            sex() == Sex.MALE
            avatar() == "https://www.wykop.pl/cdn/c3397992/avatar_def,q150.png"
            signupAt() == LocalDateTime.of(2008, 4, 2, 21, 42, 26)
            isObserved() == false
            isBlocked() == false
            email() == "biuro@wykop.pl"
            name() == "Michał Białek"
            www() == "http://www.wykop.pl"
            city() == "internet"
            facebook() == "https://www.facebook.com/bialek.michal"
            twitter() == "https://twitter.com/mihau81"
            instagram() == "https://www.instagram.com/mihau/"
            about() == "wykopuje, zakopuje, przekopuje..."
            linksAddedCount() == 33
            linksPublishedCount() == 7
            commentsCount() == 75
            rank() == 0
            followers() == 2433
            following() == 25
            entries() == 443
            entriesComments() == 2456
            diggs() == 28172
            violationUrl() == "https://a2.wykop.pl/naruszenia/form/ot/profile/od/33639/ud/5xOL/hs/bc2d92529ce3367938eeda280d806f0c8d637c57/rn/iiwjKoXzwp/"
        }
    }

    def "should map json to empty profile's actions"() {
        given:
        String json = sampleProfile.emptyActions
        when:
        def actions = mapper.map(json, Actions)
        then:
        actions.entries().isEmpty()
        actions.links().isEmpty()
    }

    def "should map json to profile's actions"() {
        given:
        String json = sampleProfile.actionsWithEntryAndLink
        when:
        def actions = mapper.map(json, Actions)
        then:
        actions.entries().size() == 2
        actions.links().size() == 1
    }

}
