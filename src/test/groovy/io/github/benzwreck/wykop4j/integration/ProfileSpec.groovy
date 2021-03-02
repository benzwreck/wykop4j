package io.github.benzwreck.wykop4j.integration

import io.github.benzwreck.wykop4j.IntegrationWykopClient
import io.github.benzwreck.wykop4j.WykopClient
import io.github.benzwreck.wykop4j.profiles.Color
import spock.lang.Specification

class ProfileSpec extends Specification {
    WykopClient wykop = IntegrationWykopClient.getInstance()
    String nonexistentLogin = UUID.randomUUID().toString()

    def "should return bialkov's profile"(){
        when:
        def profile = wykop.profile("m__b").execute().get()
        then:
        profile.login() == "m__b"
        profile.color() == Color.ADMIN
    }
    def "should return empty Optional"() {
        when:
        def profile = wykop.profile(nonexistentLogin).execute()
        then:
        profile == Optional.empty()
    }
}
