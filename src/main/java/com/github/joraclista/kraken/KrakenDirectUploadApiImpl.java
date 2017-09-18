package com.github.joraclista.kraken;

import com.github.joraclista.kraken.api.Base;
import com.github.joraclista.kraken.api.KrakenApiImpl;
import com.github.joraclista.kraken.api.KrakenDirectUploadApi;
import com.github.joraclista.kraken.auth.Auth;
import com.github.joraclista.kraken.config.ConfigLocation;
import com.github.joraclista.kraken.config.KrakenConfig;
import com.github.joraclista.kraken.http.RestTemplateProxy;
import com.github.joraclista.kraken.model.request.*;
import com.github.joraclista.kraken.model.response.AbstractKrakenResponse;
import com.github.joraclista.kraken.model.response.AbstractKrakenResponse.MultipleResizeResponseImpl;
import com.github.joraclista.kraken.model.response.AbstractKrakenResponse.OptimizeResponseImpl;
import com.github.joraclista.kraken.model.response.AbstractKrakenResponse.SingleResizeResponseImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;

import java.io.File;
import java.io.IOException;

import static com.github.joraclista.kraken.helpers.Mapper.readValue;
import static org.springframework.http.HttpMethod.POST;

/**
 * Created by Alisa
 * version 1.0.
 */
@Slf4j
@AllArgsConstructor
public class KrakenDirectUploadApiImpl extends Base implements KrakenDirectUploadApi {

    private final KrakenConfig config;

    public KrakenDirectUploadApiImpl() throws IOException {
        config = readValue(new ConfigLocation(KrakenApiImpl.class.getClassLoader()).getConfig(), KrakenConfig.class);
    }


    @Override
    public OptimizeResponseImpl post(File file, OptimizeRequestImpl request) {
        return post(request, file, OptimizeResponseImpl.class);
    }

    @Override
    public SingleResizeResponseImpl post(File file, ResizeRequestImpl request) {
        return post(request, file, SingleResizeResponseImpl.class);
    }

    @Override
    public MultipleResizeResponseImpl post(File file, MultipleResizeRequestImpl request) {
        return post(request, file, MultipleResizeResponseImpl.class);
    }

    public <T extends AbstractKrakenResponse> T post(KrakenRequest request, File tempFile, Class<T> responseClass) {
        try {
            MultiValueMap<String, Object> requestParts = new LinkedMultiValueMap<>();
            requestParts.add("data", request);
            requestParts.add("file", new FileSystemResource(tempFile));
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", MediaType.MULTIPART_FORM_DATA_VALUE);


            request.setAuth(new Auth(config.getKey(), config.getSecret()));
            ResponseEntity<T> response = new RestTemplateProxy()
                    .withUrl(config.getDirectUploadUrl())
                    .withConnectionTimeout(config.getConnectTimeoutMs())
                    .withReadTimeout(config.getReadTimeoutMs()).exchange(POST, new HttpEntity<>(requestParts, headers), responseClass);
            T result = response.getBody();
            result.setHttpStatusCode(response.getStatusCodeValue());
            result.setHttpStatusText(response.getStatusCode().getReasonPhrase());
            return result;
        } catch (HttpClientErrorException e) {
            log.error("post: couldn't post request due to : {}", e.getMessage());
            return getErrorResult(request.getUrl(), responseClass, getErrorMessage(e), e.getStatusCode().value(), e.getStatusText());
        } catch (Exception e) {
            log.error("post: couldn't post request due to : {}", e.getMessage());
            return getErrorResult(request.getUrl(), responseClass, e.getMessage(), null, null);
        }
    }


    @Override
    public boolean isLiveMode() {
        return config.isLiveMode();
    }
}
