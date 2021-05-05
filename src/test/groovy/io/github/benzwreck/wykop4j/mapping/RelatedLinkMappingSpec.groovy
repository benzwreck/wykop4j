package io.github.benzwreck.wykop4j.mapping

import io.github.benzwreck.wykop4j.WykopMappingTestObject
import io.github.benzwreck.wykop4j.links.RelatedLinkVoteData
import spock.lang.Specification

class RelatedLinkMappingSpec extends Specification{
    WykopMappingTestObject mapper = new WykopMappingTestObject()
    String data = """
{
  "data": {
    "vote_count": 62
  },
  "item": {
    "vote_count": 62
  }
}
"""
    def "test"(){
        when:
        def count = mapper.map(data, RelatedLinkVoteData)
        then:
        println(count)
    }
}
