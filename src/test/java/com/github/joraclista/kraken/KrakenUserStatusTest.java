package com.github.joraclista.kraken;

import com.github.joraclista.kraken.model.response.AbstractKrakenResponse.UserStatusResponseImpl;
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
public class KrakenUserStatusTest extends BaseTest {

    @Test
    public void userStatusData() {
        if (!getKrakenApi().isLiveMode())
            return;
        UserStatusResponseImpl status = getKrakenApi().getStatus();

        assertTrue(status.isSuccess());
        assertTrue(status.isActive());
        assertNotNull(status.getPlanName());
        assertTrue(status.getQuotaUsed() >= 0);
    }

}
