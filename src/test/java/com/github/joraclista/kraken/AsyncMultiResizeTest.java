package com.github.joraclista.kraken;

import com.github.joraclista.kraken.model.request.MultipleResizeRequestImpl;
import com.github.joraclista.kraken.model.request.ResizeItem;
import com.github.joraclista.kraken.model.request.ResizeStrategy;
import com.github.joraclista.kraken.model.response.AbstractKrakenResponse;
import junitparams.JUnitParamsRunner;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Alisa
 * version 1.0.
 */
@RunWith(JUnitParamsRunner.class)
@Slf4j
public class AsyncMultiResizeTest extends BaseTest {

    private List<ResizeItem> resizeItemList;

    @Before
    public void setUp() throws IOException {
        super.setUp();
        resizeItemList = Arrays.asList(
                ResizeItem.builder().id("id1").width(100).height(100).strategy(ResizeStrategy.PORTRAIT).build(),
                ResizeItem.builder().id("id2").width(300).height(300).strategy(ResizeStrategy.CROP).build(),
                ResizeItem.builder().id("id3").width(400).height(400).strategy(ResizeStrategy.FILL).background("red").build(),
                ResizeItem.builder().id("id4").width(100).height(100).strategy(ResizeStrategy.LANDSCAPE).build(),
                ResizeItem.builder().id("id5").width(1000).height(1000).strategy(ResizeStrategy.EXACT).build(),
                ResizeItem.builder().id("id6").strategy(ResizeStrategy.NONE).build());

    }
    @Test
    public void asyncMultiResizeCorrectData() {
        if (getKrakenApi().isLiveMode()) {
            AbstractKrakenResponse response = getKrakenApi().post(MultipleResizeRequestImpl.asyncBuilder()
                    .url(getImageOriginalUrl())
                    .lossy(true)
                    .resizes(resizeItemList)
                    .callbackUrl("http://86fbe85b.ngrok.io/kraken/callback/multi")
                    .build());

            log.info("Response: {}" , response);
            assertTrue(response.isSuccess());
            assertNotNull(response.getId());
        }

    }

    @Test
    public void asyncInvalidImageUrl() {
        if (getKrakenApi().isLiveMode()) {
            AbstractKrakenResponse response = getKrakenApi().post(MultipleResizeRequestImpl.asyncBuilder()
                    .url(getImageOriginalUrl()+"88888")
                    .lossy(true)
                    .resizes(resizeItemList)
                    .callbackUrl("http://86fbe85b.ngrok.io/kraken/callback/multi")
                    .build());

            log.info("Response: {}" , response);
            assertTrue(response.isSuccess());

            assertNotNull(response.getId());
        }

    }
}
