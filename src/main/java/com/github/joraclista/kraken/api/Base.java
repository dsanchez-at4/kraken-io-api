package com.github.joraclista.kraken.api;

import com.github.joraclista.kraken.api.exceptions.KrakenApiException;
import com.github.joraclista.kraken.helpers.Mapper;
import com.github.joraclista.kraken.model.response.AbstractKrakenResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;

/**
 * Created by Alisa
 * version 1.0.
 */
@Slf4j
public class Base {

    protected <T extends AbstractKrakenResponse> T getErrorResult(String url, Class<T> clazz, String errorMessage, Integer statusCode, String statusText) {
        try {
            T errorResult = clazz.newInstance();
            errorResult.setSuccess(false);
            errorResult.setHttpStatusCode(statusCode);
            errorResult.setHttpStatusText(statusText);
            errorResult.setMessage(errorMessage);
            errorResult.setImageOriginalUrl(url);
            return errorResult;
        } catch (Exception e) {
            log.error("Couldn't get error result due to {}", e.getMessage());
            throw new KrakenApiException(e.getMessage());
        }
    }

    protected String getErrorMessage(HttpClientErrorException e) {
        String errorMessage = e.getMessage();
        try {
            errorMessage = Mapper.readValue(e.getResponseBodyAsString(), HashMap.class).get("message") + "";
        } catch (Exception exc) {
            log.warn("Couldn't get error message from http response due to {}", exc.getMessage());
        }
        return errorMessage;
    }
}
