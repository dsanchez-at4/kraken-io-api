package com.github.joraclista.kraken;

import com.github.joraclista.kraken.model.request.OptimizeRequestImpl;
import com.github.joraclista.kraken.model.response.AbstractKrakenResponse;
import junitparams.JUnitParamsRunner;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created by Alisa
 * version 1.0.
 */
@RunWith(JUnitParamsRunner.class)
@Slf4j
public class AsyncOptimizeTest extends BaseTest {

    @Test
    public void asyncNoCallbackUrl() {
        AbstractKrakenResponse.OptimizeResponseImpl response = getKrakenApi().post(OptimizeRequestImpl.asyncBuilder()
                .url(getImageOriginalUrl())
                .lossy(true)
                .build());

        log.info("Response: {}" , response);
        assertFalse(response.isSuccess());
        assertNotNull(response.getMessage());
        assertTrue(response.getMessage().contains("need to specify one of callback_url")
                || response.getMessage().contains("callback_url is not of a type(s) string"));
    }

    @Test
    public void asyncWrongCallbackUrl() {
        AbstractKrakenResponse.OptimizeResponseImpl response = getKrakenApi().post(OptimizeRequestImpl.asyncBuilder()
                .url(getImageOriginalUrl())
                .lossy(true)
                .callbackUrl("http")
                .build());

        log.info("Response: {}" , response);
        assertFalse(response.isSuccess());
        assertNotNull(response.getMessage());
        assertTrue(response.getMessage().contains("provide a valid URL"));
    }

    @Test
    public void asyncCorrectWaitMessage() {
        AbstractKrakenResponse.OptimizeResponseImpl response = getKrakenApi().post(OptimizeRequestImpl.asyncBuilder()
                .url(getImageOriginalUrl())
                .lossy(true)
                .callbackUrl("http://call.back.com/url")
                .build());

        log.info("Response: {}" , response);
        assertTrue(response.isSuccess());
        if (getKrakenApi().isLiveMode())
            assertNotNull(response.getId());
    }
}