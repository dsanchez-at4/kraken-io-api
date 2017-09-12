package com.github.joraclista.kraken;

import com.github.joraclista.kraken.model.request.KrakenSyncRequestImpl.OptimizeRequestImpl;
import com.github.joraclista.kraken.model.response.AbstractKrakenResponse.OptimizeResponseImpl;
import junitparams.JUnitParamsRunner;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Alisa
 * version 1.0.
 */
@RunWith(JUnitParamsRunner.class)
@Slf4j
public class SyncOptimizeTest extends BaseTest {

    @Test
    public void optimizeImageTest() {
        OptimizeResponseImpl response = getKrakenApi().post(OptimizeRequestImpl.builder()
                .auth(getAuth())
                .url(getImageOriginalUrl())
                .lossy(true)
                .build());

        assertTrue(response.isSuccess());

        log.info("Response: krakedUrl = {}" , response.getKrakedUrl());

        assertTrue(response.getKrakedSize() <= response.getOriginalSize());
        assertTrue(response.getSavedBytes() >= 0);
        assertNotNull(response.getKrakedUrl());
    }
}
