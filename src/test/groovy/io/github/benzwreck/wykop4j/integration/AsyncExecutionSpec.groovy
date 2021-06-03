package io.github.benzwreck.wykop4j.integration

import io.github.benzwreck.wykop4j.IntegrationWykopClient
import io.github.benzwreck.wykop4j.WykopClient
import io.github.benzwreck.wykop4j.exceptions.LinkCommentNotExistException
import spock.lang.Specification

import java.time.Year

class AsyncExecutionSpec extends Specification {
    WykopClient wykop = IntegrationWykopClient.getInstance()

    def "should return list of top links"() {
        when:
        def topLinks2017 = wykop.getTopLinks(Year.of(2017)).executeAsync()
        def topLinks2018 = wykop.getTopLinks(Year.of(2018)).executeAsync()
        def topLinks2019 = wykop.getTopLinks(Year.of(2019)).executeAsync()
        then:
        topLinks2017.thenApplyAsync(topLinks -> topLinks.stream().allMatch(link -> link.isHot())).join()
        topLinks2018.thenApplyAsync(topLinks -> topLinks.stream().allMatch(link -> link.isHot())).join()
        topLinks2019.thenApplyAsync(topLinks -> topLinks.stream().allMatch(link -> link.isHot())).join()
    }

    def "should throw an exception"() {
        given:
        def nonexistentId = -11111
        expect:
        wykop
                .voteUpLinkComment(nonexistentId, nonexistentId)
                .executeAsync()
                .exceptionally(ex -> ex.getCause() instanceof LinkCommentNotExistException)
                .join()
    }
}
