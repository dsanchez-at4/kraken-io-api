package com.github.joraclista.kraken.config;

import lombok.*;

import static com.github.joraclista.kraken.config.Mode.LIVE;

/**
 * Created by Alisa
 * version 1.0.
 */
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KrakenConfig {

    private String key;
    private String secret;
    private String url;
    private String userStatusUrl;
    private String directUploadUrl;
    private int connectTimeoutMs;
    private int readTimeoutMs;
    private Mode mode;

    @Builder
    public KrakenConfig(String key, String secret, String url, int connectTimeoutMs, int readTimeoutMs, Mode mode) {
        this.key = key;
        this.secret = secret;
        this.url = url;
        this.connectTimeoutMs = connectTimeoutMs;
        this.readTimeoutMs = readTimeoutMs;
        this.mode = mode == null ? LIVE : mode;
    }

    public boolean isDevMode() {
        return !isLiveMode();
    }

    public boolean isLiveMode() {
        return LIVE.equals(this.mode);
    }
}
