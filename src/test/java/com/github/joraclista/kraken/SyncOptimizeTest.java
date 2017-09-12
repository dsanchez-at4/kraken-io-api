package com.github.joraclista.kraken;

import com.github.joraclista.kraken.auth.Auth;
import com.github.joraclista.kraken.model.request.KrakenSyncRequestImpl;
import com.github.joraclista.kraken.model.response.KrakenResponseImpl;
import junitparams.JUnitParamsRunner;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNull;
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
        KrakenResponseImpl response = getKrakenApi().post(KrakenSyncRequestImpl.builder()
                .auth(new Auth(
                        getKrakenConfig().getKey(),
                        getKrakenConfig().getSecret()))
                .url(getImageOriginalUrl())
                .lossy(true)
                .build());

        assertTrue(response.isSuccess());
        assertNull(response.getResults());

        log.info("Response: krakedUrl = {}" , response.getKrakedUrl());

        assertTrue(response.getKrakedSize() <= response.getOriginalSize());
        assertNull(response.getKrakedUrl());
    }
}
