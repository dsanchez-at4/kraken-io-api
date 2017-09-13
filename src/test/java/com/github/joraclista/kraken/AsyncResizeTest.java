package com.github.joraclista.kraken;

import com.github.joraclista.kraken.model.request.ResizeItem;
import com.github.joraclista.kraken.model.request.ResizeRequestImpl;
import com.github.joraclista.kraken.model.request.ResizeStrategy;
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
public class AsyncResizeTest extends BaseTest {

    @Test
    public void asyncResizeStrategyTest() {
        SingleResizeResponseImpl response = getKrakenApi().post(ResizeRequestImpl.asyncBuilder()
                .url(getImageOriginalUrl())
                .lossy(true)
                .callbackUrl("http://hhh.com/go")
                .resize(ResizeItem.builder().id("id").height(500).width(300).background("red").strategy(ResizeStrategy.FILL).build())
                .build());
        log.info("Response: {}" , response);
        assertTrue(response.isSuccess());
        assertNotNull(response.getId());
    }

    @Test
    public void asyncWrongUrlResizeStrategyTest() {
        SingleResizeResponseImpl response = getKrakenApi().post(ResizeRequestImpl.asyncBuilder()
                .url(getImageOriginalUrl())
                .lossy(true)
                .resize(ResizeItem.builder().id("id").height(500).width(300).background("red").strategy(ResizeStrategy.FILL).build())
                .build());
        log.info("Response: {}" , response);
        assertFalse(response.isSuccess());
        assertNotNull(response.getMessage());
        assertTrue(response.getMessage().contains("need to specify one of callback_url")
                || response.getMessage().contains("callback_url is not of a type(s) string"));
    }


}
