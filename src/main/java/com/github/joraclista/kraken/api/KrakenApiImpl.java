package com.github.joraclista.kraken.api;

import com.github.joraclista.kraken.config.KrakenConfig;
import com.github.joraclista.kraken.helpers.Mapper;
import com.github.joraclista.kraken.http.RestTemplateProxy;
import com.github.joraclista.kraken.model.request.KrakenRequest;
import com.github.joraclista.kraken.model.response.KrakenResponseImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;

import static com.github.joraclista.kraken.model.response.KrakenResponse.HTTP_200;
import static com.github.joraclista.kraken.model.response.KrakenResponse.HTTP_200_OK;

/**
 * Created by Alisa
 * version 1.0.
 */
@Slf4j
@AllArgsConstructor
public class KrakenApiImpl implements KrakenApi {

    private KrakenConfig config;

    @Override
    public KrakenResponseImpl post(KrakenRequest krakenRequest) {
        try {
            return new RestTemplateProxy()
                    .withUrl(config.getUrl())
                    .withConnectionTimeout(config.getConnectTimeoutMs())
                    .withReadTimeout(config.getReadTimeoutMs())
                    .post(krakenRequest, KrakenResponseImpl.class)
                    .withImageOriginalUrl(krakenRequest.getUrl())
                    .withHttpStatusCode(HTTP_200)
                    .withHttpStatusText(HTTP_200_OK);
        } catch (HttpClientErrorException e) {
            log.error("post: couldn't post request due to : ", e);
            return new KrakenResponseImpl()
                    .withSuccess(false)
                    .withHttpStatusCode(e.getStatusCode().value())
                    .withHttpStatusText(e.getStatusText())
                    .withMessage(getErrorMessage(e))
                    .withImageOriginalUrl(krakenRequest.getUrl());
        } catch (Exception e) {
           log.error("post: couldn't post request due to : ", e);
           return new KrakenResponseImpl()
                   .withSuccess(false)
                   .withMessage(e.getMessage())
                   .withImageOriginalUrl(krakenRequest.getUrl());
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
