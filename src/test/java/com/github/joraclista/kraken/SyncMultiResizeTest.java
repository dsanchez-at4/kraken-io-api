package com.github.joraclista.kraken;

import com.github.joraclista.kraken.model.request.ResizeItem;
import com.github.joraclista.kraken.model.request.ResizeStrategy;
import com.github.joraclista.kraken.model.response.AbstractKrakenResponse.MultipleResizeResponseImpl;
import junitparams.JUnitParamsRunner;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.*;

/**
 * Created by Alisa
 * version 1.0.
 */
@RunWith(JUnitParamsRunner.class)
@Slf4j
public class SyncMultiResizeTest extends BaseTest {

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
                ResizeItem.builder().id("id6").width(100).height(100).strategy(ResizeStrategy.AUTO).build());

    }


    @Test
    public void multiResizeOnValidaDataTest() {
        //only works in live mode, as response.result is a Map in live mode and list in sandbox mode
        if (!getKrakenApi().isLiveMode())
            return;
        MultipleResizeResponseImpl response = getKrakenApi().post(getRequest(resizeItemList));

        assertTrue(response.isSuccess());
        assertNotNull(response.getResults());

        log.info("Response: {}" , response);

        assertEquals(response.getResults().size(), resizeItemList.size());
        assertNotNull(response.getResults().get("id1"));
    }

    /**
     * All resize IDs should be unique in case of more than 1 resize item in request
     */
    @Test
    public void multiResizeOnInvalidDataTest() {
        MultipleResizeResponseImpl response = getKrakenApi().post(getRequest(resizeItemList.stream()
                .map(resizeItem -> {
                    resizeItem.setId("id");
                    return resizeItem;
                })
        .collect(toList())));

        assertFalse(response.isSuccess());
        assertNull(response.getResults());

        log.info("Response: {}" , response);

        assertEquals(response.getMessage(), "When using multi-resize you must provide a unique 'id' property for each size you request");
    }
}
