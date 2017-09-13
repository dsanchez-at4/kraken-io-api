package com.github.joraclista.kraken.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

/**
 * Created by Alisa
 * version 1.0.
 */
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MultipleResizeRequestImpl extends AbstractKrakenRequest {

    @JsonProperty("resize")
    private List<ResizeItem> resizes;

    public static SyncBuilder syncBuilder() {
        return new SyncBuilder();
    }

    public static AsyncBuilder asyncBuilder() {
        return new AsyncBuilder();
    }

    public static class SyncBuilder {
        private boolean lossy;
        private int quality;
        private String url;
        private List<ResizeItem> resizes;

        public SyncBuilder resizes(List<ResizeItem> resizes) {
            this.resizes = resizes;
            return this;
        }

        public SyncBuilder lossy(boolean lossy) {
            this.lossy = lossy;
            return this;
        }

        public SyncBuilder quality(int quality) {
            this.quality = quality;
            return this;
        }

        public SyncBuilder url(String url) {
            this.url = url;
            return this;
        }

        public MultipleResizeRequestImpl build() {
            MultipleResizeRequestImpl request = new MultipleResizeRequestImpl();
            request.setLossy(lossy);
            request.setQuality(quality);
            request.setWait(true);
            request.setUrl(url);
            request.setCallbackUrl(null);
            request.setResizes(resizes);
            return request;
        }
    }

    public static class AsyncBuilder {
        private boolean lossy;
        private int quality;
        private String url;
        private String callbackUrl;
        private List<ResizeItem> resizes;

        public AsyncBuilder resizes(List<ResizeItem> resizes) {
            this.resizes = resizes;
            return this;
        }

        public AsyncBuilder lossy(boolean lossy) {
            this.lossy = lossy;
            return this;
        }

        public AsyncBuilder quality(int quality) {
            this.quality = quality;
            return this;
        }

        public AsyncBuilder url(String url) {
            this.url = url;
            return this;
        }

        public AsyncBuilder callbackUrl(String callbackUrl) {
            this.callbackUrl = callbackUrl;
            return this;
        }

        public MultipleResizeRequestImpl build() {
            MultipleResizeRequestImpl request = new MultipleResizeRequestImpl();
            request.setLossy(lossy);
            request.setQuality(quality);
            request.setWait(false);
            request.setUrl(url);
            request.setCallbackUrl(callbackUrl);
            request.setResizes(resizes);
            return request;
        }
    }
}
