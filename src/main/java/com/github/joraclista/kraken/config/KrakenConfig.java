package com.github.joraclista.kraken.config;

import lombok.Data;

/**
 * Created by Alisa
 * version 1.0.
 */
@Data
public class KrakenConfig {
    private String key;
    private String secret;
    private String url;
    private int connectTimeoutMs;
    private int readTimeoutMs;
}
