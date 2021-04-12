## What is Wykop4j?

Wykop4j is a simple Wykop SDK for Java. Inspired by [wykop-sdk-reborn](https://github.com/krasnoludkolo/wykop-sdk-reborn/).
It's just a PoC version. The API might change in the future.
If you have found a bug or have an idea how to improve that project just create an issue, I will do my best to help you ;)

If you'd like to read more there's also a [wiki](https://www.github.com/benzwreck/wykop4j/wiki) written in Polish. 

## Getting started

#### Dependencies

Add those lines to your:

* pom.xml

```xml
<dependency>
    <groupId>io.github.benzwreck</groupId>
    <artifactId>wykop4j</artifactId>
    <version>1.0.0</version>
</dependency>
```

* gradle.build

```groovy
implementation group: 'io.github.benzwreck', name: 'wykop4j', version: '1.0.0'
```

#### Creating WykopClient

To create an instance of `WykopClient` you will need your `ApplicationCredentials` and optionally `UserCredentials` (keep in mind that some methods will not work if `UserCredentials` is not passed). How to get one? Check out [this (Polish)](https://github.com/benzwreck/wykop4j/wiki/ApplicationCredentials-i-UserCredentials).

```java
WykopClient wykop = new WykopClient.Builder()
    .withApplicationCredentials(new ApplicationCredentials("appkey", "secret"))
    .withUserCredentials(new UserCredentials("login", "accountkey"))
    .build()
```

#### Using WykopClient

We start off with `wykop.methodName()` which returns some `Chain<Something>` and then we can execute our code synchronously with `.execute()` or (in the future - because it's not implemented yet) asynchronously with `.executeAsync()`. E.g.

```java
Optional<Entry> entry = wykop.entry(entryId).execute();

List<Entry> entries = wykop.entriesStream().execute();
```

All methods that have been implemented so far are shown [here (Polish only)](https://github.com/benzwreck/wykop4j/wiki/Zaimplementowane-metody).

## Plans for the future

- [ ] Implement missing API methods.
- [ ] Support async operations.
- [ ] Add more configuration features.
- [ ] Replace Okhttp with Java 11 Http Client.
- [ ] Replace Jackson with own json mapping implementation.
- [ ] Rewrite this project to Kotlin or create new one.

## External dependencies

* Http client: OkHttp 4.7.2
* Json mapper: Jackson 2.12.1



