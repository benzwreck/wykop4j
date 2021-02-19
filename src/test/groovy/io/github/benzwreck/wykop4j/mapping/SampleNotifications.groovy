package io.github.benzwreck.wykop4j.mapping

class SampleNotifications {
    static String directNotificationsJson = "{\n" +
            "  \"data\": [\n" +
            "    {\n" +
            "      \"id\": 1000000000,\n" +
            "      \"author\": {\n" +
            "        \"login\": \"nobodycares\",\n" +
            "        \"color\": 1,\n" +
            "        \"sex\": \"female\",\n" +
            "        \"avatar\": \"https://www.wykop.pl/cdn/c331111/blabla,q150.jpg\"\n" +
            "      },\n" +
            "      \"date\": \"2021-02-18 21:16:32\",\n" +
            "      \"body\": \"nobodycares napisała do Ciebie w komentarzu do \\\"/Jestem tu\\\".\",\n" +
            "      \"type\": \"entry_comment_directed\",\n" +
            "      \"item_id\": \"88888888\",\n" +
            "      \"subitem_id\": \"123123123\",\n" +
            "      \"url\": \"https://www.wykop.pl/wpis/88888888/jestem-tu-/#comment-123123123\",\n" +
            "      \"new\": true\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 2222222222,\n" +
            "      \"author\": {\n" +
            "        \"login\": \"ziomeczek\",\n" +
            "        \"color\": 0,\n" +
            "        \"sex\": \"male\",\n" +
            "        \"avatar\": \"https://www.wykop.pl/cdn/c111111/ziomeczek,q150.jpg\"\n" +
            "      },\n" +
            "      \"date\": \"2021-02-18 09:42:36\",\n" +
            "      \"body\": \"ziomeczek napisał do Ciebie w komentarzu do \\\"Jestem tam\\\".\",\n" +
            "      \"type\": \"entry_comment_directed\",\n" +
            "      \"item_id\": \"55555555\",\n" +
            "      \"subitem_id\": \"321321321\",\n" +
            "      \"url\": \"https://www.wykop.pl/wpis/55555555/notifications-index-gt-jestem-tam-/#comment-321321321\",\n" +
            "      \"new\": false\n" +
            "    }\n" +
            "  ],\n" +
            "  \"pagination\": {\n" +
            "    \"next\": \"https://a2.wykop.pl/powiadomienia/Index/page/2/output/clear/appkey/app/userkey/user/\"\n" +
            "  }\n" +
            "}"
    static String tagsNotificationsJson = "{\n" +
            "  \"data\": [\n" +
            "    {\n" +
            "      \"id\": 1000000000,\n" +
            "      \"author\": {\n" +
            "        \"login\": \"nobodycares\",\n" +
            "        \"color\": 1,\n" +
            "        \"sex\": \"female\",\n" +
            "        \"avatar\": \"https://www.wykop.pl/cdn/c331111/blabla,q150.jpg\"\n" +
            "      },\n" +
            "      \"date\": \"2021-02-18 21:16:32\",\n" +
            "      \"body\": \"nobodycares napisała do Ciebie w komentarzu do \\\"/Jestem tu\\\".\",\n" +
            "      \"type\": \"entry_directed\",\n" +
            "      \"item_id\": \"88888888\",\n" +
            "      \"subitem_id\": \"123123123\",\n" +
            "      \"url\": \"https://www.wykop.pl/wpis/88888888/jestem-tu-/#comment-123123123\",\n" +
            "      \"new\": true\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 2222222222,\n" +
            "      \"author\": {\n" +
            "        \"login\": \"ziomeczek\",\n" +
            "        \"color\": 0,\n" +
            "        \"sex\": \"male\",\n" +
            "        \"avatar\": \"https://www.wykop.pl/cdn/c111111/ziomeczek,q150.jpg\"\n" +
            "      },\n" +
            "      \"date\": \"2021-02-18 09:42:36\",\n" +
            "      \"body\": \"ziomeczek napisał do Ciebie w komentarzu do \\\"Jestem tam\\\".\",\n" +
            "      \"type\": \"entry_directed\",\n" +
            "      \"item_id\": \"55555555\",\n" +
            "      \"subitem_id\": \"321321321\",\n" +
            "      \"url\": \"https://www.wykop.pl/wpis/55555555/notifications-index-gt-jestem-tam-/#comment-321321321\",\n" +
            "      \"new\": false\n" +
            "    }\n" +
            "  ],\n" +
            "  \"pagination\": {\n" +
            "    \"next\": \"https://a2.wykop.pl/powiadomienia/Index/page/2/output/clear/appkey/app/userkey/user/\"\n" +
            "  }\n" +
            "}"
    static String emptyNotificationsJson = "{\n" +
            "  \"data\": [],\n" +
            "  \"pagination\": {\n" +
            "    \"next\": \"https://a2.wykop.pl/powiadomienia/Index/page/2/output/clear/appkey/app/userkey/user/\"\n" +
            "  }\n" +
            "}"
    static String zeroNotificationCount = "{\"data\":{\"count\":0}}"
    static String totalNotificationCount = "{\"data\":{\"count\":1,\"hastagcount\":4}}"
}
