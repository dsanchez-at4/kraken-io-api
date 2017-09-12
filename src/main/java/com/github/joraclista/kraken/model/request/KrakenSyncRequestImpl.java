package com.github.joraclista.kraken.model.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.joraclista.kraken.auth.Auth;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by Alisa
 * version 1.0.
 */
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class KrakenSyncRequestImpl implements KrakenRequest {

    private Auth auth;
    private boolean wait;
    private boolean lossy;
    private int quality;
    private String url;
    private String imageFinalName;

    private KrakenSyncRequestImpl(Auth auth) {
        this.auth = auth;
    }

    @Data
    public static class SingleResizeRequestImpl extends KrakenSyncRequestImpl {
        private ResizeItem resize;

        @Builder
        public SingleResizeRequestImpl(Auth auth, boolean lossy, int quality, String url, ResizeItem resize){
            super(auth);
            setLossy(lossy);
            setQuality(quality);
            setUrl(url);
            setWait(true);
            this.resize = resize;
        }
    }

    @Data
    public static class MultipleResizeRequestImpl extends KrakenSyncRequestImpl {
        @JsonProperty("resize")
        private List<ResizeItem> resizes;

        @Builder
        public MultipleResizeRequestImpl(Auth auth, boolean lossy, int quality, String url, List<ResizeItem> resizes){
            super(auth);
            setLossy(lossy);
            setQuality(quality);
            setUrl(url);
            setWait(true);
            this.resizes = resizes;
        }
    }
}
