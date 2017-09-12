package com.github.joraclista.kraken.model.response;

/**
 * Created by Alisa
 * version 1.0.
 */
public interface KrakenResponse {

    int HTTP_200 = 200;

    String HTTP_200_OK = "Ok";

    Integer getHttpStatusCode();

    String getHttpStatusText();

    String getImageOriginalUrl();

    boolean isSuccess();

    String getMessage();

}
