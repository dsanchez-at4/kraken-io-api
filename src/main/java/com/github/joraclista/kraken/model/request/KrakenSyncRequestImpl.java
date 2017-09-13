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


    @Data
    public static class OptimizeRequestImpl extends KrakenSyncRequestImpl {

        @Builder
        public OptimizeRequestImpl(boolean lossy, int quality, String url){
            setLossy(lossy);
            setQuality(quality);
            setUrl(url);
            setWait(true);
        }
    }

    @Data
    public static class SingleResizeRequestImpl extends KrakenSyncRequestImpl {
        private ResizeItem resize;

        @Builder
        public SingleResizeRequestImpl(boolean lossy, int quality, String url, ResizeItem resize){
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
        public MultipleResizeRequestImpl(boolean lossy, int quality, String url, List<ResizeItem> resizes){
            setLossy(lossy);
            setQuality(quality);
            setUrl(url);
            setWait(true);
            this.resizes = resizes;
        }
    }
}
