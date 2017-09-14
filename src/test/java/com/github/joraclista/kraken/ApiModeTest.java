package com.github.joraclista.kraken;

import com.github.joraclista.kraken.api.KrakenApi;
import com.github.joraclista.kraken.api.KrakenApiImpl;
import com.github.joraclista.kraken.config.KrakenConfig;
import com.github.joraclista.kraken.config.Mode;
import junitparams.JUnitParamsRunner;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Alisa
 * version 1.0.
 */
@RunWith(JUnitParamsRunner.class)
@Slf4j
public class ApiModeTest extends BaseTest {

    @Test
    public void sandboxModeTest() {
        KrakenApi krakenApi = new KrakenApiImpl(KrakenConfig.builder()
                .key("any")
                .secret("secret")
                .mode(Mode.SANDBOX)
                .url("url")
                .build());

        assertFalse(krakenApi.isLiveMode());
    }

    @Test
    public void liveModeTest() {
        KrakenApi krakenApi = new KrakenApiImpl(KrakenConfig.builder()
                .key("any")
                .secret("secret")
                .mode(Mode.LIVE)
                .url("url")
                .build());

        assertTrue(krakenApi.isLiveMode());
    }

    @Test
    public void defaultModeTest() {
        KrakenApi krakenApi = new KrakenApiImpl(KrakenConfig.builder()
                .key("any")
                .secret("secret")
                .url("url")
                .build());

        assertTrue(krakenApi.isLiveMode());
    }
}
