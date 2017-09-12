package com.github.joraclista.kraken.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by Alisa
 * version 1.0.
 */
@Data
@AllArgsConstructor
public class Auth {

    @JsonProperty("api_key")
    private String apiKey;

    @JsonProperty("api_secret")
    private String apiSecret;
}
