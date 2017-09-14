package com.github.joraclista.kraken.model.request;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.joraclista.kraken.auth.Auth;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Alisa
 * version 1.0.
 */
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractKrakenRequest implements KrakenRequest {

    private Auth auth;

    private boolean wait;

    @JsonProperty("dev")
    private boolean devMode = false;

    private boolean lossy;

    private int quality;

    private String url;

    private String imageFinalName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("callback_url")
    private String callbackUrl;

}
