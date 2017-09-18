package com.github.joraclista.kraken.api;

import com.github.joraclista.kraken.auth.Auth;
import com.github.joraclista.kraken.config.ConfigLocation;
import com.github.joraclista.kraken.config.KrakenConfig;
import com.github.joraclista.kraken.http.RestTemplateProxy;
import com.github.joraclista.kraken.model.request.KrakenRequest;
import com.github.joraclista.kraken.model.request.MultipleResizeRequestImpl;
import com.github.joraclista.kraken.model.request.OptimizeRequestImpl;
import com.github.joraclista.kraken.model.request.ResizeRequestImpl;
import com.github.joraclista.kraken.model.response.AbstractKrakenResponse;
import com.github.joraclista.kraken.model.response.AbstractKrakenResponse.MultipleResizeResponseImpl;
import com.github.joraclista.kraken.model.response.AbstractKrakenResponse.OptimizeResponseImpl;
import com.github.joraclista.kraken.model.response.AbstractKrakenResponse.SingleResizeResponseImpl;
import com.github.joraclista.kraken.model.response.AbstractKrakenResponse.UserStatusResponseImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;

import static com.github.joraclista.kraken.helpers.Mapper.readValue;
import static org.springframework.http.HttpStatus.OK;

/**
 * Created by Alisa
 * version 1.0.
 */
@Slf4j
@AllArgsConstructor
public class KrakenApiImpl extends Base implements KrakenApi {

    private final KrakenConfig config;

    public KrakenApiImpl() throws IOException {
        config = readValue(new ConfigLocation(KrakenApiImpl.class.getClassLoader()).getConfig(), KrakenConfig.class);
    }

    @Override
    public OptimizeResponseImpl post(OptimizeRequestImpl request) {
        return post(request, OptimizeResponseImpl.class);
    }

    @Override
    public SingleResizeResponseImpl post(ResizeRequestImpl request) {
        return post(request, SingleResizeResponseImpl.class);
    }

    @Override
    public MultipleResizeResponseImpl post(MultipleResizeRequestImpl request) {
        return post(request, MultipleResizeResponseImpl.class);
    }

    @Override
    public UserStatusResponseImpl getStatus() {
        return post(OptimizeRequestImpl.syncBuilder().build(), config.getUserStatusUrl(), UserStatusResponseImpl.class);
    }

    private <T extends AbstractKrakenResponse> T post(KrakenRequest request, Class<T> responseClazz) {
        return post(request, config.getUrl(), responseClazz);
    }

    private <T extends AbstractKrakenResponse> T post(KrakenRequest request, String url, Class<T> responseClazz) {
        try {
            request.setAuth(new Auth(config.getKey(), config.getSecret()));
            request.setDevMode(config.isDevMode());
            T result = new RestTemplateProxy()
                    .withUrl(url)
                    .withConnectionTimeout(config.getConnectTimeoutMs())
                    .withReadTimeout(config.getReadTimeoutMs())
                    .post(request, responseClazz);
            result.setHttpStatusCode(OK.value());
            result.setHttpStatusText(OK.getReasonPhrase());
            result.setImageOriginalUrl(request.getUrl());
            return result;
        } catch (HttpClientErrorException e) {
            log.error("post: couldn't post request due to : {}", e.getMessage());
            return getErrorResult(request.getUrl(), responseClazz, getErrorMessage(e), e.getStatusCode().value(), e.getStatusText());
        } catch (Exception e) {
           log.error("post: couldn't post request due to : {}", e.getMessage());
            return getErrorResult(request.getUrl(), responseClazz, e.getMessage(), null, null);
        }
    }


    public boolean isLiveMode() {
        return config.isLiveMode();
    }
}
