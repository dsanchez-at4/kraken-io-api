package com.github.joraclista.kraken;

import com.github.joraclista.kraken.model.request.ResizeRequestImpl;
import com.github.joraclista.kraken.model.request.ResizeItem;
import com.github.joraclista.kraken.model.request.ResizeStrategy;
import com.github.joraclista.kraken.model.response.AbstractKrakenResponse.SingleResizeResponseImpl;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
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
public class SyncResizeTest extends BaseTest {

    @Parameters({
            "100, 100, portrait",
            "200, 200, portrait",
            "300, 300, portrait"
    })
    @Test
    public void resizePortraitTest(int width, int height, String strategy) {
        SingleResizeResponseImpl response = getKrakenApi().post(getRequest(width, height, ResizeStrategy.valueOf(strategy.toUpperCase()), null));
        log.info("Response: {}" , response);
        assertTrue(response.isSuccess());

        assertNotNull(response.getKrakedUrl());

        if (getKrakenApi().isLiveMode())
            assertEquals(response.getKrakedHeight(), height);

    }

    @Parameters({
            "100, 100, landscape",
            "200, 200, landscape"
    })
    @Test
    public void resizeLandscapeTest(int width, int height, String strategy) {
        SingleResizeResponseImpl response = getKrakenApi().post(getRequest(width, height, ResizeStrategy.valueOf(strategy.toUpperCase()), null));
        log.info("Response: {}" , response);
        assertTrue(response.isSuccess());

        assertNotNull(response.getKrakedUrl());
        if (getKrakenApi().isLiveMode()) {
            assertEquals(response.getKrakedWidth(), width);
        }
    }

    @Parameters({
            "100, 100, fill, white",
            "1000, 1000, fill, white"
    })
    @Test
    public void resizeFillTest(int width, int height, String strategy, String background) {
        SingleResizeResponseImpl response = getKrakenApi().post(getRequest(width, height, ResizeStrategy.valueOf(strategy.toUpperCase()), background));
        log.info("Response: {}" , response);
        assertTrue(response.isSuccess());

        assertNotNull(response.getKrakedUrl());
        if (getKrakenApi().isLiveMode()) {
            assertEquals(response.getKrakedWidth(), width);
            assertEquals(response.getKrakedHeight(), height);
        }

    }

    @Parameters({
            "100, 100, exact",
            "900, 200, exact"
    })
    @Test
    public void resizeExactTest(int width, int height, String strategy) {
        SingleResizeResponseImpl response = getKrakenApi().post(getRequest(width, height, ResizeStrategy.valueOf(strategy.toUpperCase()), null));


        log.info("Response: {}" , response);
        assertTrue(response.isSuccess());
        assertNotNull(response.getKrakedUrl());
        if (getKrakenApi().isLiveMode()) {
            assertEquals(response.getKrakedWidth(), width);
            assertEquals(response.getKrakedHeight(), height);
        }

    }


    @Test
    public void wrongResizeStrategyTest() {
        SingleResizeResponseImpl response = getKrakenApi().post(ResizeRequestImpl.syncBuilder()
                .url(getImageOriginalUrl())
                .lossy(true)
                .resize((ResizeItem.builder().id("id").width(100).height(200).strategy(null).build()))
                .build());
        log.info("Response: {}" , response);
        assertFalse(response.isSuccess());
        assertNotNull(response.getMessage());
        assertTrue(response.getMessage().contains("Resize strategy must be one of the following"));
    }

    @Test
    public void wrongPortraitResizeStrategyTest() {
        SingleResizeResponseImpl response = getKrakenApi().post(ResizeRequestImpl.syncBuilder()
                .url(getImageOriginalUrl())
                .lossy(true)
                .resize((ResizeItem.builder().id("id").strategy(ResizeStrategy.PORTRAIT).build()))
                .build());
        log.info("Response: {}" , response);
        assertFalse(response.isSuccess());
        assertNotNull(response.getMessage());
        assertTrue(response.getMessage().contains("image height must be set"));
    }

    @Test
    public void wrongLandscapeResizeStrategyTest() {
        SingleResizeResponseImpl response = getKrakenApi().post(ResizeRequestImpl.syncBuilder()
                .url(getImageOriginalUrl())
                .lossy(true)
                .resize(ResizeItem.builder().id("id").strategy(ResizeStrategy.LANDSCAPE).build())
                .build());
        log.info("Response: {}" , response);
        assertFalse(response.isSuccess());
        assertNotNull(response.getMessage());
        assertTrue(response.getMessage().contains("image width must be set"));
    }

    @Test
    public void wrongExactResizeStrategyTest() {
        SingleResizeResponseImpl response = getKrakenApi().post(ResizeRequestImpl.syncBuilder()
                .url(getImageOriginalUrl())
                .lossy(true)
                .resize(ResizeItem.builder().id("id").strategy(ResizeStrategy.EXACT).build())
                .build());
        log.info("Response: {}" , response);
        assertFalse(response.isSuccess());
        assertNotNull(response.getMessage());
        assertTrue(response.getMessage().contains("image width and height must be set"));
    }

    @Test
    public void wrongAutoResizeStrategyTest() {
        SingleResizeResponseImpl response = getKrakenApi().post(ResizeRequestImpl.syncBuilder()
                .url(getImageOriginalUrl())
                .lossy(true)
                .resize(ResizeItem.builder().id("id").strategy(ResizeStrategy.AUTO).build())
                .build());
        log.info("Response: {}" , response);
        assertFalse(response.isSuccess());
        assertNotNull(response.getMessage());
        assertTrue(response.getMessage().contains("image width and height must be set"));
    }

    @Test
    public void wrongSquareResizeStrategyTest() {
        SingleResizeResponseImpl response = getKrakenApi().post(ResizeRequestImpl.syncBuilder()
                .url(getImageOriginalUrl())
                .lossy(true)
                .resize(ResizeItem.builder().id("id").strategy(ResizeStrategy.SQUARE).build())
                .build());
        log.info("Response: {}" , response);
        assertFalse(response.isSuccess());
        assertNotNull(response.getMessage());
        assertTrue(response.getMessage().contains("image size must be set"));
    }

    @Test
    public void squareResizeStrategyTest() {
        SingleResizeResponseImpl response = getKrakenApi().post(ResizeRequestImpl.syncBuilder()
                .url(getImageOriginalUrl())
                .lossy(true)
                .resize(ResizeItem.builder().id("id").size(500).strategy(ResizeStrategy.SQUARE).build())
                .build());
        log.info("Response: {}" , response);
        assertTrue(response.isSuccess());
        assertNotNull(response.getKrakedUrl());
    }

    @Test
    public void fillResizeStrategyTest() {
        SingleResizeResponseImpl response = getKrakenApi().post(ResizeRequestImpl.syncBuilder()
                .url(getImageOriginalUrl())
                .lossy(true)
                .resize(ResizeItem.builder().id("id").height(500).width(300).background("red").strategy(ResizeStrategy.FILL).build())
                .build());
        log.info("Response: {}" , response);
        assertTrue(response.isSuccess());
        assertNotNull(response.getKrakedUrl());
    }

    @Test
    public void cropResizeStrategyTest() {
        SingleResizeResponseImpl response = getKrakenApi().post(ResizeRequestImpl.syncBuilder()
                .url(getImageOriginalUrl())
                .lossy(true)
                .resize(ResizeItem.builder().id("id").height(500).width(300).strategy(ResizeStrategy.CROP).build())
                .build());
        log.info("Response: {}" , response);
        assertTrue(response.isSuccess());
        assertNotNull(response.getKrakedUrl());
    }

    @Test
    public void cropScaleResizeStrategyTest() {
        SingleResizeResponseImpl response = getKrakenApi().post(ResizeRequestImpl.syncBuilder()
                .url(getImageOriginalUrl())
                .lossy(true)
                .resize(ResizeItem.builder().id("id").height(500).width(300).scale(150).strategy(ResizeStrategy.CROP).build())
                .build());
        log.info("Response: {}" , response);
        assertTrue(response.isSuccess());
        assertNotNull(response.getKrakedUrl());
    }

    @Test
    public void wrongCropResizeStrategyTest() {
        SingleResizeResponseImpl response = getKrakenApi().post(ResizeRequestImpl.syncBuilder()
                .url(getImageOriginalUrl())
                .lossy(true)
                .resize(ResizeItem.builder().id("id").strategy(ResizeStrategy.CROP).build())
                .build());
        log.info("Response: {}" , response);
        assertFalse(response.isSuccess());
        assertNotNull(response.getMessage());
        assertTrue(response.getMessage().contains("image width and height must be set"));
    }

}
