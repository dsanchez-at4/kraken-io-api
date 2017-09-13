package com.github.joraclista.kraken.config;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Alisa
 * version 1.0.
 */
@Data
@NoArgsConstructor
public class KrakenConfig {
    private String key;
    private String secret;
    private String url;
    private int connectTimeoutMs;
    private int readTimeoutMs;

    public KrakenConfig(String key, String secret, String url) {
        this.key = key;
        this.secret = secret;
        this.url = url;
    }
}
