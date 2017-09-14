package com.github.joraclista.kraken.http;

import lombok.Setter;
import org.apache.http.impl.NoConnectionReuseStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Alisa
 * version 1.0.
 */
@Setter
public class RestTemplateProxy {

    private static final int DEFAULT_CONNECTION_TIMEOUT = 2000;
    private static final int DEFAULT_READ_TIMEOUT = 2000;

    private int connectionTimeout;
    private int readTimeout;
    private String url;

    public RestTemplateProxy withConnectionTimeout(int connectionTimeout) {
        setConnectionTimeout(connectionTimeout);
        return this;
    }

    public RestTemplateProxy withReadTimeout(int readTimeout) {
        setReadTimeout(readTimeout);
        return this;
    }

    public RestTemplateProxy withUrl(String url) {
        setUrl(url);
        return this;
    }

    private RestTemplate getRestTemplate() {
        HttpClientBuilder httpClient = HttpClients.custom().useSystemProperties();
        httpClient.setConnectionReuseStrategy(NoConnectionReuseStrategy.INSTANCE);
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient.build());
        requestFactory.setConnectTimeout(connectionTimeout > 0 ? connectionTimeout : DEFAULT_CONNECTION_TIMEOUT);
        requestFactory.setReadTimeout(readTimeout > 0 ? readTimeout : DEFAULT_READ_TIMEOUT);
        return new RestTemplate(requestFactory);
    }

    public <T> String post(HttpEntity<T> entity) throws Exception {
        return getRestTemplate().postForObject(url, entity, String.class);
    }

    public <T> T post(Object request, Class<T> clazz) throws Exception {
        return getRestTemplate().postForObject(url, request, clazz);
    }

    public <T> ResponseEntity<T> exchange(HttpMethod method,
                                          HttpEntity<?> requestEntity, Class<T> responseType, Object... uriVariables) {
        return getRestTemplate().exchange(url, method, requestEntity, responseType, uriVariables);
    }
}
