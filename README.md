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

[More on the sandbox api here](https://kraken.io/docs/sandbox)

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

[More on async mode here](https://kraken.io/docs/wait-callback)

```java
  OptimizeResponseImpl response = getKrakenApi().post(OptimizeRequestImpl.asyncBuilder()
                .url(getImageOriginalUrl())
                .lossy(true)
                .callbackUrl("http://call.back.com/url")
                .build());
                
  log.info("Response: success = {}" , response.isSuccess());
  log.info("Response: request id = {}" , response.getId());
```

Image Resizing


Resize image via specified strategy.
use syncBuilder for sync request, asyncBuilder with callback url for waiting request.

[More on resizing here](https://kraken.io/docs/image-resizing)


```java
SingleResizeResponseImpl response = getKrakenApi().post(ResizeRequestImpl.syncBuilder()
                .url(getImageOriginalUrl())
                .lossy(true)
                .resize((ResizeItem.builder().id("id").width(100).height(200).strategy(ResizeStrategy.PORTRAIT).build()))
                .build());

log.info("Response: id = {}" , response.isSuccess());
log.info("Response: kraked url = {}" , response.getKrakedUrl());
```

Resize image to multiple set of sizes / strategies.

use syncBuilder for sync request, asyncBuilder with callback url for waiting request.
Mind that "NONE" strategy will  lead to just optimizing without resizing.

[More on multi-resizing here](https://kraken.io/docs/generating-image-sets)

```java
MultipleResizeResponseImpl response = getKrakenApi().post(MultipleResizeRequestImpl.syncBuilder()
                .url(getImageOriginalUrl())
                .resizes(Arrays.asList(
                        ResizeItem.builder().id("id1").width(100).height(100).strategy(ResizeStrategy.PORTRAIT).build(),
                        ResizeItem.builder().id("id2").width(300).height(300).strategy(ResizeStrategy.CROP).build(),
                        ResizeItem.builder().id("id3").width(400).height(400).strategy(ResizeStrategy.FILL).background("red").build(),
                        ResizeItem.builder().id("id4").width(100).height(100).strategy(ResizeStrategy.LANDSCAPE).build(),
                        ResizeItem.builder().id("id5").width(1000).height(1000).strategy(ResizeStrategy.EXACT).build(),
                        ResizeItem.builder().id("id6").strategy(ResizeStrategy.NONE).build(),
                        ResizeItem.builder().id("id7").width(100).height(100).strategy(ResizeStrategy.AUTO).build()))
                .build());

log.info("Response: id = {}" , response.isSuccess());
log.info("Response: results = {}" , response.getResults());
```

Get user status.

Get the plan info, quota info (remaining, used, total)

[More on user status here](https://kraken.io/docs/user-status)

```java
 UserStatusResponseImpl status = new KrakenApiImpl(KrakenConfig.builder()
                .key("YOUR_APP_KEY")
                .secret("YOUR_APP_SECRET")
                .build())
                .getStatus();

log.info("Response: success = {}" , status.isSuccess());
log.info("Response: quotaUsed = {}" , status.getQuotaUsed());
log.info("Response: planName = {}" , status.getPlanName());
```
