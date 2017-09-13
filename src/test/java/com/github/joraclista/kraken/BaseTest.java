package com.github.joraclista.kraken;

import com.github.joraclista.kraken.api.KrakenApi;
import com.github.joraclista.kraken.api.KrakenApiImpl;
import com.github.joraclista.kraken.model.request.MultipleResizeRequestImpl;
import com.github.joraclista.kraken.model.request.ResizeRequestImpl;
import com.github.joraclista.kraken.model.request.ResizeItem;
import com.github.joraclista.kraken.model.request.ResizeStrategy;
import lombok.Getter;
import org.junit.Before;

import java.io.IOException;
import java.util.List;

/**
 * Created by Alisa
 * version 1.0.
 */
public class BaseTest {

    private static final String KRAKEN_IO_CONFIG_JSON = "kraken-io-default-config.json";

    @Getter
    private String imageOriginalUrl = "https://upload.wikimedia.org/wikipedia/commons/f/f2/Beyonce_-_The_Formation_World_Tour%2C_at_Wembley_Stadium_in_London%2C_England.jpg";

    @Getter
    private KrakenApi krakenApi;


    @Before
    public void setUp() throws IOException {
        krakenApi = new KrakenApiImpl();
    }

    protected ResizeRequestImpl getRequest(int width, int height, ResizeStrategy strategy, String background) {
        return ResizeRequestImpl.syncBuilder()
                .url(getImageOriginalUrl())
                .lossy(false)
                .resize(ResizeItem.builder()
                        .width(width)
                        .height(height)
                        .strategy(strategy)
                        .background(background)
                        .build())
                .build();
    }

    protected MultipleResizeRequestImpl getRequest(List<ResizeItem> resizes) {
        return MultipleResizeRequestImpl.syncBuilder()
                .url(getImageOriginalUrl())
                .lossy(false)
                .resizes(resizes)
                .build();
    }
}
