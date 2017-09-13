package com.github.joraclista.kraken.model.response;

/**
 * Created by Alisa
 * version 1.0.
 */
public interface KrakenResponse {

    Integer getHttpStatusCode();

    String getHttpStatusText();

    String getImageOriginalUrl();

    boolean isSuccess();

    String getMessage();

    String getId();
}
