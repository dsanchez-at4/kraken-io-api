package com.github.joraclista.kraken;

import com.github.joraclista.kraken.auth.Auth;
import com.github.joraclista.kraken.model.request.KrakenSyncRequestImpl;
import com.github.joraclista.kraken.model.request.ResizeItem;
import com.github.joraclista.kraken.model.request.ResizeStrategy;
import com.github.joraclista.kraken.model.response.KrakenResponse;
import com.github.joraclista.kraken.model.response.ResizeResponseItem;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

/**
 * Created by Alisa
 * version 1.0.
 */
@RunWith(JUnitParamsRunner.class)
@Slf4j
public class SyncResizeTest extends BaseTest {

    @Parameters({
            "id1, 100, 100, portrait",
            "id2, 200, 200, portrait",
            "id3, 300, 300, portrait"
    })
    @Test
    public void resizePortraitTest(String id, int width, int height, String strategy) {
        KrakenResponse response = getKrakenApi().post(getRequest(id, width, height, ResizeStrategy.valueOf(strategy.toUpperCase()), null));
        log.info("Response: {}" , response);
        assertTrue(response.isSuccess());
        assertNotNull(response.getResults());

        ResizeResponseItem responseItem = response.getResults().get(id);
        assertNotNull(responseItem);

        log.info("Response: request id = {}, kraked image url : {}" , id, responseItem.getKrakedUrl());

        assertEquals(responseItem.getKrakedHeight(), height);
    }

    @Parameters({
            "id1, 100, 100, landscape",
            "id2, 200, 200, landscape"
    })
    @Test
    public void resizeLandscapeTest(String id, int width, int height, String strategy) {
        KrakenResponse response = getKrakenApi().post(getRequest(id, width, height, ResizeStrategy.valueOf(strategy.toUpperCase()), null));
        log.info("Response: {}" , response);
        assertTrue(response.isSuccess());
        assertNotNull(response.getResults());

        ResizeResponseItem responseItem = response.getResults().get(id);
        assertNotNull(response.getResults().get(id));

        log.info("Response: request id = {}, kraked image url : {}" , id, responseItem.getKrakedUrl());

        assertEquals(response.getResults().get(id).getKrakedWidth(), width);
    }

    @Parameters({
            "id1, 100, 100, fill, white",
            "id2, 1000, 1000, fill, white"
    })
    @Test
    public void resizeFillTest(String id, int width, int height, String strategy, String background) {
        KrakenResponse response = getKrakenApi().post(getRequest(id, width, height, ResizeStrategy.valueOf(strategy.toUpperCase()), background));
        log.info("Response: {}" , response);
        assertTrue(response.isSuccess());
        assertNotNull(response.getResults());

        ResizeResponseItem responseItem = response.getResults().get(id);
        assertNotNull(response.getResults().get(id));

        log.info("Response: request id = {}, kraked image url : {}" , id, responseItem.getKrakedUrl());

        assertEquals(response.getResults().get(id).getKrakedWidth(), width);
    }

    @Parameters({
            "id1, 100, 100, exact",
            "id2, 900, 200, exact"
    })
    @Test
    public void resizeExactTest(String id, int width, int height, String strategy) {
        KrakenResponse response = getKrakenApi().post(getRequest(id, width, height, ResizeStrategy.valueOf(strategy.toUpperCase()), null));
        log.info("Response: {}" , response);
        assertTrue(response.isSuccess());
        assertNotNull(response.getResults());

        ResizeResponseItem responseItem = response.getResults().get(id);
        assertNotNull(response.getResults().get(id));

        log.info("Response: request id = {}, kraked image url : {}" , id, responseItem.getKrakedUrl());

        assertEquals(response.getResults().get(id).getKrakedWidth(), width);
        assertEquals(response.getResults().get(id).getKrakedHeight(), height);
    }


    @Test
    public void wrongResizeStrategyTest() {
        KrakenResponse response = getKrakenApi().post(KrakenSyncRequestImpl.builder()
                .auth(new Auth(getKrakenConfig().getKey(), getKrakenConfig().getSecret()))
                .url(getImageOriginalUrl())
                .lossy(true)
                .resize(asList(ResizeItem.builder().id("id").width(100).height(200).strategy(null).build()))
                .build());
        log.info("Response: {}" , response);
        assertFalse(response.isSuccess());
        assertNull(response.getResults());
        assertNotNull(response.getMessage());
        assertTrue(response.getMessage().contains("Resize strategy must be one of the following"));
    }

    @Test
    public void wrongPortraitResizeStrategyTest() {
        KrakenResponse response = getKrakenApi().post(KrakenSyncRequestImpl.builder()
                .auth(new Auth(getKrakenConfig().getKey(), getKrakenConfig().getSecret()))
                .url(getImageOriginalUrl())
                .lossy(true)
                .resize(asList(ResizeItem.builder().id("id").strategy(ResizeStrategy.PORTRAIT).build()))
                .build());
        log.info("Response: {}" , response);
        assertFalse(response.isSuccess());
        assertNull(response.getResults());
        assertNotNull(response.getMessage());
        assertTrue(response.getMessage().contains("image height must be set"));
    }

    @Test
    public void wrongLandscapeResizeStrategyTest() {
        KrakenResponse response = getKrakenApi().post(KrakenSyncRequestImpl.builder()
                .auth(new Auth(getKrakenConfig().getKey(), getKrakenConfig().getSecret()))
                .url(getImageOriginalUrl())
                .lossy(true)
                .resize(asList(ResizeItem.builder().id("id").strategy(ResizeStrategy.LANDSCAPE).build()))
                .build());
        log.info("Response: {}" , response);
        assertFalse(response.isSuccess());
        assertNull(response.getResults());
        assertNotNull(response.getMessage());
        assertTrue(response.getMessage().contains("image width must be set"));
    }

    @Test
    public void wrongExactResizeStrategyTest() {
        KrakenResponse response = getKrakenApi().post(KrakenSyncRequestImpl.builder()
                .auth(new Auth(getKrakenConfig().getKey(), getKrakenConfig().getSecret()))
                .url(getImageOriginalUrl())
                .lossy(true)
                .resize(asList(ResizeItem.builder().id("id").strategy(ResizeStrategy.EXACT).build()))
                .build());
        log.info("Response: {}" , response);
        assertFalse(response.isSuccess());
        assertNull(response.getResults());
        assertNotNull(response.getMessage());
        assertTrue(response.getMessage().contains("image width and height must be set"));
    }

    @Test
    public void wrongAutoResizeStrategyTest() {
        KrakenResponse response = getKrakenApi().post(KrakenSyncRequestImpl.builder()
                .auth(new Auth(getKrakenConfig().getKey(), getKrakenConfig().getSecret()))
                .url(getImageOriginalUrl())
                .lossy(true)
                .resize(asList(ResizeItem.builder().id("id").strategy(ResizeStrategy.AUTO).build()))
                .build());
        log.info("Response: {}" , response);
        assertFalse(response.isSuccess());
        assertNull(response.getResults());
        assertNotNull(response.getMessage());
        assertTrue(response.getMessage().contains("image width and height must be set"));
    }

    @Test
    public void wrongSquareResizeStrategyTest() {
        KrakenResponse response = getKrakenApi().post(KrakenSyncRequestImpl.builder()
                .auth(new Auth(getKrakenConfig().getKey(), getKrakenConfig().getSecret()))
                .url(getImageOriginalUrl())
                .lossy(true)
                .resize(asList(ResizeItem.builder().id("id").strategy(ResizeStrategy.SQUARE).build()))
                .build());
        log.info("Response: {}" , response);
        assertFalse(response.isSuccess());
        assertNull(response.getResults());
        assertNotNull(response.getMessage());
        assertTrue(response.getMessage().contains("image size must be set"));
    }

    @Test
    public void squareResizeStrategyTest() {
        KrakenResponse response = getKrakenApi().post(KrakenSyncRequestImpl.builder()
                .auth(new Auth(getKrakenConfig().getKey(), getKrakenConfig().getSecret()))
                .url(getImageOriginalUrl())
                .lossy(true)
                .resize(asList(ResizeItem.builder().id("id").size(500).strategy(ResizeStrategy.SQUARE).build()))
                .build());
        log.info("Response: {}" , response);
        assertTrue(response.isSuccess());
        assertNotNull(response.getResults());
    }

    @Test
    public void fillResizeStrategyTest() {
        KrakenResponse response = getKrakenApi().post(KrakenSyncRequestImpl.builder()
                .auth(new Auth(getKrakenConfig().getKey(), getKrakenConfig().getSecret()))
                .url(getImageOriginalUrl())
                .lossy(true)
                .resize(asList(ResizeItem.builder().id("id").height(500).width(300).background("red").strategy(ResizeStrategy.FILL).build()))
                .build());
        log.info("Response: {}" , response);
        assertTrue(response.isSuccess());
        assertNotNull(response.getResults());
    }

    @Test
    public void cropResizeStrategyTest() {
        KrakenResponse response = getKrakenApi().post(KrakenSyncRequestImpl.builder()
                .auth(new Auth(getKrakenConfig().getKey(), getKrakenConfig().getSecret()))
                .url(getImageOriginalUrl())
                .lossy(true)
                .resize(asList(ResizeItem.builder().id("id").height(500).width(300).strategy(ResizeStrategy.CROP).build()))
                .build());
        log.info("Response: {}" , response);
        assertTrue(response.isSuccess());
        assertNotNull(response.getResults());
    }

    @Test
    public void cropScaleResizeStrategyTest() {
        KrakenResponse response = getKrakenApi().post(KrakenSyncRequestImpl.builder()
                .auth(new Auth(getKrakenConfig().getKey(), getKrakenConfig().getSecret()))
                .url(getImageOriginalUrl())
                .lossy(true)
                .resize(asList(ResizeItem.builder().id("id").height(500).width(300).scale(150).strategy(ResizeStrategy.CROP).build()))
                .build());
        log.info("Response: {}" , response);
        assertTrue(response.isSuccess());
        assertNotNull(response.getResults());
    }

    @Test
    public void wrongCropResizeStrategyTest() {
        KrakenResponse response = getKrakenApi().post(KrakenSyncRequestImpl.builder()
                .auth(new Auth(getKrakenConfig().getKey(), getKrakenConfig().getSecret()))
                .url(getImageOriginalUrl())
                .lossy(true)
                .resize(asList(ResizeItem.builder().id("id").strategy(ResizeStrategy.CROP).build()))
                .build());
        log.info("Response: {}" , response);
        assertFalse(response.isSuccess());
        assertNull(response.getResults());
        assertNotNull(response.getMessage());
        assertTrue(response.getMessage().contains("image width and height must be set"));
    }
}
