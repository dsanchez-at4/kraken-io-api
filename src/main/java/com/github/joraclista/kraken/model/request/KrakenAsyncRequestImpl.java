package com.github.joraclista.kraken.model.request;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.joraclista.kraken.auth.Auth;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by Alisa
 * version 1.0.
 */
@Data
@NoArgsConstructor(access = AccessLevel.NONE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class KrakenAsyncRequestImpl implements KrakenRequest {
    private Auth auth;

//    @JsonProperty("s3_store")
//    private S3Config s3Store;
    private boolean wait;
    private boolean lossy;
    private String url;

    private List<ResizeItem> resize;

    @JsonProperty("callback_url")
    private String callbackUrl;
    @JsonIgnore
    private String imageFinalName;

}
