package io.github.benzwreck.wykop4j.mapping

class SampleNotifications {
    String directNotificationsJson = '''
{
  "data": [
    {
      "id": 1000000000,
      "author": {
        "login": "nobodycares",
        "color": 1,
        "sex": "female",
        "avatar": "https://www.wykop.pl/cdn/c331111/blabla,q150.jpg"
      },
      "date": "2021-02-18 21:16:32",
      "body": "nobodycares napisała do Ciebie w komentarzu do \\"/Jestem tu\\".",
      "type": "entry_comment_directed",
      "item_id": "88888888",
      "subitem_id": "123123123",
      "url": "https://www.wykop.pl/wpis/88888888/jestem-tu-/#comment-123123123",
      "new": true
    },
    {
      "id": 2222222222,
      "author": {
        "login": "ziomeczek",
        "color": 0,
        "sex": "male",
        "avatar": "https://www.wykop.pl/cdn/c111111/ziomeczek,q150.jpg"
      },
      "date": "2021-02-18 09:42:36",
      "body": "ziomeczek napisał do Ciebie w komentarzu do \\"Jestem tam\\".",
      "type": "entry_comment_directed",
      "item_id": "55555555",
      "subitem_id": "321321321",
      "url": "https://www.wykop.pl/wpis/55555555/notifications-index-gt-jestem-tam-/#comment-321321321",
      "new": false
    }
  ],
  "pagination": {
    "next": "https://a2.wykop.pl/powiadomienia/Index/page/2/output/clear/appkey/app/userkey/user/"
  }
}'''
    String tagsNotificationsJson = '''
{
  "data": [
    {
      "id": 1000000000,
      "author": {
        "login": "nobodycares",
        "color": 1,
        "sex": "female",
        "avatar": "https://www.wykop.pl/cdn/c331111/blabla,q150.jpg"
      },
      "date": "2021-02-18 21:16:32",
      "body": "nobodycares napisała do Ciebie w komentarzu do \\"/Jestem tu\\".",
      "type": "entry_directed",
      "item_id": "88888888",
      "subitem_id": "123123123",
      "url": "https://www.wykop.pl/wpis/88888888/jestem-tu-/#comment-123123123",
      "new": true
    },
    {
      "id": 2222222222,
      "author": {
        "login": "ziomeczek",
        "color": 0,
        "sex": "male",
        "avatar": "https://www.wykop.pl/cdn/c111111/ziomeczek,q150.jpg"
      },
      "date": "2021-02-18 09:42:36",
      "body": "ziomeczek napisał do Ciebie w komentarzu do \\"Jestem tam\\".",
      "type": "entry_directed",
      "item_id": "55555555",
      "subitem_id": "321321321",
      "url": "https://www.wykop.pl/wpis/55555555/notifications-index-gt-jestem-tam-/#comment-321321321",
      "new": false
    }
  ],
  "pagination": {
    "next": "https://a2.wykop.pl/powiadomienia/Index/page/2/output/clear/appkey/app/userkey/user/"
  }
}'''
    String emptyNotificationsJson = '''
{
  "data": [],
  "pagination": {
    "next": "https://a2.wykop.pl/powiadomienia/Index/page/2/output/clear/appkey/app/userkey/user/"
  }
}'''
    String zeroNotificationCount = '{"data":{"count":0}}'
    String totalNotificationCount = '{"data":{"count":1,"hastagcount":4}}'
}
