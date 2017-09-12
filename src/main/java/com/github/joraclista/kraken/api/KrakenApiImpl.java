package com.github.joraclista.kraken.api;

import com.github.joraclista.kraken.api.exceptions.KrakenApiException;
import com.github.joraclista.kraken.config.KrakenConfig;
import com.github.joraclista.kraken.helpers.Mapper;
import com.github.joraclista.kraken.http.RestTemplateProxy;
import com.github.joraclista.kraken.model.request.KrakenRequest;
import com.github.joraclista.kraken.model.request.KrakenSyncRequestImpl.MultipleResizeRequestImpl;
import com.github.joraclista.kraken.model.request.KrakenSyncRequestImpl.OptimizeRequestImpl;
import com.github.joraclista.kraken.model.request.KrakenSyncRequestImpl.SingleResizeRequestImpl;
import com.github.joraclista.kraken.model.response.AbstractKrakenResponse;
import com.github.joraclista.kraken.model.response.AbstractKrakenResponse.MultipleResizeResponseImpl;
import com.github.joraclista.kraken.model.response.AbstractKrakenResponse.OptimizeResponseImpl;
import com.github.joraclista.kraken.model.response.AbstractKrakenResponse.SingleResizeResponseImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;

import static org.springframework.http.HttpStatus.OK;

/**
 * Created by Alisa
 * version 1.0.
 */
@Slf4j
@AllArgsConstructor
public class KrakenApiImpl implements KrakenApi {

    private KrakenConfig config;

    @Override
    public OptimizeResponseImpl post(OptimizeRequestImpl request) {
        return post(request, OptimizeResponseImpl.class);
    }

    @Override
    public SingleResizeResponseImpl post(SingleResizeRequestImpl request) {
        return post(request, SingleResizeResponseImpl.class);
    }

    @Override
    public MultipleResizeResponseImpl post(MultipleResizeRequestImpl request) {
        return post(request, MultipleResizeResponseImpl.class);
    }

    public <T extends AbstractKrakenResponse> T post(KrakenRequest request, Class<T> clazz) {
        try {
            T result = new RestTemplateProxy()
                    .withUrl(config.getUrl())
                    .withConnectionTimeout(config.getConnectTimeoutMs())
                    .withReadTimeout(config.getReadTimeoutMs())
                    .post(request, clazz);
            result.setHttpStatusCode(OK.value());
            result.setHttpStatusText(OK.getReasonPhrase());
            result.setImageOriginalUrl(request.getUrl());
            return result;
        } catch (HttpClientErrorException e) {
            log.error("post: couldn't post request due to : {}", e.getMessage());
            return getErrorResult(request.getUrl(), clazz, getErrorMessage(e), e.getStatusCode().value(), e.getStatusText());
        } catch (Exception e) {
           log.error("post: couldn't post request due to : {}", e.getMessage());
            return getErrorResult(request.getUrl(), clazz, e.getMessage(), null, null);
        }
    }

    private <T extends AbstractKrakenResponse> T getErrorResult(String url, Class<T> clazz, String errorMessage, Integer statusCode, String statusText) {
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

    private String getErrorMessage(HttpClientErrorException e) {
        String errorMessage = e.getMessage();
        try {
            errorMessage = Mapper.readValue(e.getResponseBodyAsString(), HashMap.class).get("message") + "";
        } catch (Exception exc) {
            log.warn("Couldn't get error message from http response due to {}", exc.getMessage());
        }
        return errorMessage;
    }
}
