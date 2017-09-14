package com.github.joraclista.kraken.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.github.joraclista.kraken.config.Mode.LIVE;

/**
 * Created by Alisa
 * version 1.0.
 */
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class KrakenConfig {

    private String key;
    private String secret;
    private String url;
    private int connectTimeoutMs;
    private int readTimeoutMs;
    private Mode mode = LIVE;

    public KrakenConfig(String key, String secret, String url) {
        this.key = key;
        this.secret = secret;
        this.url = url;
    }

    public boolean isDevMode() {
        return !isLiveMode();
    }

    public boolean isLiveMode() {
        return LIVE.equals(this.mode);
    }
}
