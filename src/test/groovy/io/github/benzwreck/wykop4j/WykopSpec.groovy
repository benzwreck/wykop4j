package io.github.benzwreck.wykop4j


import io.github.benzwreck.wykop4j.client.WykopClient
import spock.lang.Specification

class WykopSpec extends Specification {
    WykopClient wykop = TestWykopClient.getInstance()

    def "allEntriestest"() {
        when:
        def entries = wykop.getAllEntries(6).execute()
//        ByteBuffer buffer = StandardCharsets.UTF_8.encode(entries)
//        String result = StandardCharsets.UTF_8.decode(buffer).toString()
        then:
        println(entries)
        1 == 1
    }
}
