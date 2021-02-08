package io.github.benzwreck.wykop4j.mapping

import io.github.benzwreck.wykop4j.WykopMappingTestObject
import io.github.benzwreck.wykop4j.entries.Answer
import io.github.benzwreck.wykop4j.entries.Entry
import io.github.benzwreck.wykop4j.entries.Survey
import io.github.benzwreck.wykop4j.profiles.Color
import spock.lang.Specification

import java.time.LocalDateTime

class EntryMappingSpec extends Specification {
    WykopMappingTestObject mapper = new WykopMappingTestObject()

    def "should map json to Entry class"() {
        when:
        String json = "{\n" +
                "   \"data\":{\n" +
                "      \"id\":55333215,\n" +
                "      \"date\":\"2021-02-03 21:35:36\",\n" +
                "      \"body\":\"Ju\\u017c za chwilk\\u0119\",\n" +
                "      \"author\":{\n" +
                "         \"login\":\"quatroo96\",\n" +
                "         \"color\":1,\n" +
                "         \"avatar\":\"https:\\/\\/www.wykop.pl\\/cdn\\/c3397992\\/avatar_def,q150.png\",\n" +
                "         \"signup_at\":\"2018-05-21 10:04:29\",\n" +
                "         \"violation_url\":\"https:\\/\\/a2.wykop.pl\\/naruszenia\\/form\\/ot\\/profile\\/od\\/1502911\\/ud\\/5xOL\\/hs\\/6f8032abfc29cc66d25ce46e12488af58eb88708\\/rn\\/ZhasKJWMCf\\/\"\n" +
                "      },\n" +
                "      \"blocked\":false,\n" +
                "      \"favorite\":false,\n" +
                "      \"vote_count\":6,\n" +
                "      \"comments_count\":0,\n" +
                "      \"status\":\"visible\",\n" +
                "      \"embed\":{\n" +
                "         \"type\":\"image\",\n" +
                "         \"url\":\"https:\\/\\/www.wykop.pl\\/cdn\\/c3201142\\/comment_1612384536orZ67OolQ7sBDR7ndIFbr5.jpg\",\n" +
                "         \"source\":\"1612384532815.gif\",\n" +
                "         \"preview\":\"https:\\/\\/www.wykop.pl\\/cdn\\/c3201142\\/comment_1612384536orZ67OolQ7sBDR7ndIFbr5,w400.jpg\",\n" +
                "         \"plus18\":false,\n" +
                "         \"size\":\"276KB\",\n" +
                "         \"animated\":true,\n" +
                "         \"ratio\":0.982\n" +
                "      },\n" +
                "      \"user_vote\":0,\n" +
                "      \"app\":\"Android\",\n" +
                "      \"violation_url\":\"https:\\/\\/a2.wykop.pl\\/naruszenia\\/form\\/ot\\/entry\\/od\\/55333215\\/ud\\/5xOL\\/hs\\/69ab7ab81eef21c24ac559438e9ed9125d73d5cb\\/rn\\/RZj76Aozhe\\/\",\n" +
                "      \"original\":\"#jp2gmd #2137 #jp2 #papiez\\nJu\\u017c za chwilk\\u0119\",\n" +
                "      \"url\":\"https:\\/\\/www.wykop.pl\\/wpis\\/55333215\\/jp2gmd-2137-jp2-papiez-juz-za-chwilke\\/\"\n" +
                "   }\n" +
                "}"
        def entry = mapper.map(json, Entry.class)
        then:
        entry.id() == 55333215
        entry.date() == LocalDateTime.of(2021, 02, 03, 21, 35, 36)
        entry.body() == "Już za chwilkę"
        def author = entry.author()
        author.login() == "quatroo96"
        author.color() == Color.ORANGE
        author.sex() == Optional.empty()
        author.avatar() == "https://www.wykop.pl/cdn/c3397992/avatar_def,q150.png"
        author.signupAt() == LocalDateTime.of(2018, 05, 21, 10, 04, 29)
        author.background() == Optional.empty()
        author.violationUrl() == "https://a2.wykop.pl/naruszenia/form/ot/profile/od/1502911/ud/5xOL/hs/6f8032abfc29cc66d25ce46e12488af58eb88708/rn/ZhasKJWMCf/"
        entry.blocked() == false
        entry.favorite() == false
        entry.voteCount() == 6
        entry.commentsCount() == 0
        entry.status() == "visible"
        def embed = entry.embed().get()
        embed.type() == "image"
        embed.url() == "https://www.wykop.pl/cdn/c3201142/comment_1612384536orZ67OolQ7sBDR7ndIFbr5.jpg"
        embed.source() == "1612384532815.gif"
        embed.preview() == "https://www.wykop.pl/cdn/c3201142/comment_1612384536orZ67OolQ7sBDR7ndIFbr5,w400.jpg"
        embed.plus18() == false
        embed.size() == "276KB"
        embed.animated() == true
        embed.ratio() == 0.982f
        entry.userVote() == 0
        entry.app().get() == "Android"
        entry.violationUrl().get() == "https://a2.wykop.pl/naruszenia/form/ot/entry/od/55333215/ud/5xOL/hs/69ab7ab81eef21c24ac559438e9ed9125d73d5cb/rn/RZj76Aozhe/"
        entry.original() == "#jp2gmd #2137 #jp2 #papiez\nJuż za chwilkę"
        entry.url() == "https://www.wykop.pl/wpis/55333215/jp2gmd-2137-jp2-papiez-juz-za-chwilke/"
    }

    def "should map json to Entry class with survey and canVote"() {
        when:
        String json = "{\n" +
                "   \"data\":{\n" +
                "      \"id\":54760047,\n" +
                "      \"date\":\"2021-01-08 02:38:08\",\n" +
                "      \"body\":\"ankieta\",\n" +
                "      \"author\":{\n" +
                "         \"login\":\"ZycieJestNobelon\",\n" +
                "         \"color\":1,\n" +
                "         \"sex\":\"male\",\n" +
                "         \"avatar\":\"https:\\/\\/www.wykop.pl\\/cdn\\/c3397992\\/ZycieJestNobelon_2WW7g3RXYw,q150.jpg\",\n" +
                "         \"signup_at\":\"2017-12-31 19:36:07\",\n" +
                "         \"background\":\"https:\\/\\/www.wykop.pl\\/cdn\\/c3397992\\/profile_background-ZycieJestNobelon_RnfuZp4Nya,w500.jpg\"\n" +
                "      },\n" +
                "      \"blocked\":false,\n" +
                "      \"favorite\":false,\n" +
                "      \"vote_count\":0,\n" +
                "      \"comments_count\":0,\n" +
                "      \"status\":\"visible\",\n" +
                "      \"survey\":{\n" +
                "         \"question\":\"Ankieta\",\n" +
                "         \"answers\":[\n" +
                "            {\n" +
                "               \"id\":1,\n" +
                "               \"answer\":\"odp1\",\n" +
                "               \"count\":4,\n" +
                "               \"percentage\":36.36363636363637\n" +
                "            },\n" +
                "            {\n" +
                "               \"id\":2,\n" +
                "               \"answer\":\"odp2\",\n" +
                "               \"count\":7,\n" +
                "               \"percentage\":63.63636363636363\n" +
                "            }\n" +
                "         ]\n" +
                "      },\n" +
                "      \"can_comment\":true,\n" +
                "      \"user_vote\":0,\n" +
                "      \"original\":\"ankieta\",\n" +
                "      \"url\":\"https:\\/\\/www.wykop.pl\\/wpis\\/54760047\\/ankieta\\/\"\n" +
                "   }\n" +
                "}"
        def entry = mapper.map(json, Entry.class)
        then:
        entry.canComment() == true
        def survey = entry.survey().get()
        survey.question() == "Ankieta"
        def answers = survey.answers()
        def firstAnswer = answers.get(0)
        firstAnswer.id() == 1
        firstAnswer.answer() == "odp1"
        firstAnswer.count() == 4
        firstAnswer.percentage() == Float.valueOf("36.36363636363637")
        def secondAnswer = answers.get(1)
        secondAnswer.id() == 2
        secondAnswer.answer() == "odp2"
        secondAnswer.count() == 7
        secondAnswer.percentage() == Float.valueOf("63.63636363636363")

    }

}
