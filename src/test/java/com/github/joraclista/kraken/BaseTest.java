package com.github.joraclista.kraken;

import com.github.joraclista.kraken.api.KrakenApi;
import com.github.joraclista.kraken.api.KrakenApiImpl;
import com.github.joraclista.kraken.auth.Auth;
import com.github.joraclista.kraken.config.KrakenConfig;
import com.github.joraclista.kraken.helpers.ResourceLoader;
import com.github.joraclista.kraken.model.request.KrakenRequest;
import com.github.joraclista.kraken.model.request.KrakenSyncRequestImpl;
import com.github.joraclista.kraken.model.request.ResizeItem;
import com.github.joraclista.kraken.model.request.ResizeStrategy;
import lombok.Getter;
import org.junit.Before;

import java.io.IOException;
import java.util.List;

import static com.github.joraclista.kraken.helpers.Mapper.readValue;
import static java.util.Arrays.asList;

/**
 * Created by Alisa
 * version 1.0.
 */
public class BaseTest {

    private static final String KRAKEN_IO_CONFIG_JSON = "kraken-io-config.json";

    @Getter
    private String imageOriginalUrl = "https://upload.wikimedia.org/wikipedia/commons/f/f2/Beyonce_-_The_Formation_World_Tour%2C_at_Wembley_Stadium_in_London%2C_England.jpg";

    @Getter
    private KrakenApi krakenApi;

    @Getter
    private KrakenConfig krakenConfig;

    @Before
    public void setUp() throws IOException {
        krakenConfig = readValue(ResourceLoader.load(KRAKEN_IO_CONFIG_JSON), KrakenConfig.class);
        krakenApi = new KrakenApiImpl(krakenConfig);
    }

    protected KrakenRequest getRequest(String id, int width, int height, ResizeStrategy strategy, String background) {
        return KrakenSyncRequestImpl.builder()
                .auth(new Auth(
                        getKrakenConfig().getKey(),
                        getKrakenConfig().getSecret()))
                .url(getImageOriginalUrl())
                .lossy(true)
                .resize(asList(ResizeItem.builder()
                        .id(id)
                        .width(width)
                        .height(height)
                        .strategy(strategy)
                        .background(background)
                        .build()))
                .build();
    }

    protected KrakenRequest getRequest(List<ResizeItem> resizes) {
        return KrakenSyncRequestImpl.builder()
                .auth(new Auth(
                        getKrakenConfig().getKey(),
                        getKrakenConfig().getSecret()))
                .url(getImageOriginalUrl())
                .lossy(true)
                .resize(resizes)
                .build();
    }
}
