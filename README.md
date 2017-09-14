# kraken-io-api

Java client for image-optimization API [Kraken.io](https://kraken.io/)

This is a regular, Maven based project.
Just run `mvn clean package`

Mind that to make it work you need appropriate kraken api_key and api_secret
in `kraken-io-config.json` or `kraken-io-default-config.json` on classpath.

With correct api_key and api_secret auth test and other tests should not fail

### Setup client

Default implementation will look for a configuration json in classpath (first looking for `kraken-io-config.json`, then to `kraken-io-default-config.json`)
with kraken key / secret / url
Ideally you should keep  `kraken-io-config.json` on your classpath

```java
  KrakenApi krakenApi = new KrakenApiImpl();
```

Or you can as well pass all configuration programatically:

```java
  KrakenApi krakenApi = new KrakenApiImpl(KrakenConfig.builder()
                .key("your_kraken_api_key")
                .secret("your_kraken_api_secret")
                .url("url")
                .build());
```

Pls mind that client can be set up for either SANDBOX (will work with empty or invalid api key / secret) or LIVE (will only work for correct api key / secret) mode.
LIVE mode is the default one.

```java
  KrakenApi krakenApi = new KrakenApiImpl(KrakenConfig.builder()
                .key("your_kraken_api_key")
                .secret("your_kraken_api_secret")
                .url("url")
                .mode(Mode.SANDBOX)
                .build());
```

### Usage

Sync request will wait until kraken finishes image processing:

```java
  OptimizeResponseImpl response = krakenApi.post(OptimizeRequestImpl.syncBuilder()
                .url(getImageOriginalUrl())
                .lossy(true)
                .build());
                
  log.info("Response: success = {}" , response.isSuccess());
  log.info("Response: kraked url = {}" , response.getKrakedUrl());
  log.info("Response: original size = {}" , response.getOriginalSize());
  log.info("Response: kraked size = {}" , response.getKrakedSize());
```

ASync request will not wait until kraken finishes image processing, just will return success flag and id of the request.
Pls mind that valid callback url is necessary for this request

```java
  OptimizeResponseImpl response = getKrakenApi().post(OptimizeRequestImpl.asyncBuilder()
                .url(getImageOriginalUrl())
                .lossy(true)
                .callbackUrl("http://call.back.com/url")
                .build());
                
  log.info("Response: id = {}" , response.isSuccess());
  log.info("Response: kraked url = {}" , response.getId());
```
