package io.github.benzwreck.wykop4j.mapping

import io.github.benzwreck.wykop4j.WykopMappingTestObject
import io.github.benzwreck.wykop4j.entries.Entry
import io.github.benzwreck.wykop4j.profiles.Color
import spock.lang.Specification

import java.time.LocalDateTime

class EntryMappingSpec extends Specification {

    def "should map json to Entry class"() {
        when:
        WykopMappingTestObject mapper = new WykopMappingTestObject()
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
        author.signupAt() == LocalDateTime.of(2018,05,21,10,04,29)
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
        entry.violationUrl() == "https://a2.wykop.pl/naruszenia/form/ot/entry/od/55333215/ud/5xOL/hs/69ab7ab81eef21c24ac559438e9ed9125d73d5cb/rn/RZj76Aozhe/"
        entry.original() == "#jp2gmd #2137 #jp2 #papiez\nJuż za chwilkę"
        entry.url() == "https://www.wykop.pl/wpis/55333215/jp2gmd-2137-jp2-papiez-juz-za-chwilke/"
    }


}
