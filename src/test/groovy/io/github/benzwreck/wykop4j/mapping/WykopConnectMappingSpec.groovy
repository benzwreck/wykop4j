package io.github.benzwreck.wykop4j.mapping

import io.github.benzwreck.wykop4j.WykopMappingTestObject
import io.github.benzwreck.wykop4j.login.WykopConnectLoginData
import spock.lang.Specification

class WykopConnectMappingSpec extends Specification {
    WykopMappingTestObject mapper = new WykopMappingTestObject()
    SampleWykopConnect sampleWykopConnect = new SampleWykopConnect()
    def "should map json to Wykop Connect login data"() {
        when:
        WykopConnectLoginData loginData = mapper.map(sampleWykopConnect.loginData, WykopConnectLoginData)
        then:
        with loginData, {
            appkey() == "1234papa"
            login() == "loginek"
            token() == "tokentoken"
            sign() == "znaczek"
        }
    }
}
