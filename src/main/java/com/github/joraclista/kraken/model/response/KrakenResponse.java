package com.github.joraclista.kraken.model.response;

import java.util.Map;

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

    String getGeneratedFileName();

    boolean isSuccess();

    String getMessage();

    Map<String, ResizeResponseItem> getResults();

}
