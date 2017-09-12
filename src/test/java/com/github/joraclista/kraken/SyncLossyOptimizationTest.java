package com.github.joraclista.kraken;

import com.github.joraclista.kraken.model.request.KrakenSyncRequestImpl;
import com.github.joraclista.kraken.model.response.AbstractKrakenResponse.SingleResizeResponseImpl;
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
public class SyncLossyOptimizationTest extends BaseTest {

    @Test
    public void invalidQualityLossParameter() {
        SingleResizeResponseImpl response = getKrakenApi().post(KrakenSyncRequestImpl.SingleResizeRequestImpl.builder()
                .auth(getAuth())
                .url(getImageOriginalUrl())
                .lossy(true)
                .quality(200)
                .build());

        assertFalse(response.isSuccess());
        assertNotNull(response.getMessage());
        assertTrue(response.getMessage().contains("quality setting has to be within the range of 1-100"));
    }

    @Test
    public void qualityLossParameter() {
        SingleResizeResponseImpl response = getKrakenApi().post(KrakenSyncRequestImpl.SingleResizeRequestImpl.builder()
                .auth(getAuth())
                .url(getImageOriginalUrl())
                .lossy(true)
                .quality(50)
                .build());

        assertTrue(response.isSuccess());
        assertNotNull(response.getKrakedUrl());
        assertTrue(response.getSavedBytes() >= 0);
        log.info("Response: krakedUrl = {}" , response.getKrakedUrl());
    }
}
