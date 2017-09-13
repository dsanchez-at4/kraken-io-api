package com.github.joraclista.kraken.model.request;

import lombok.*;

/**
 * Created by Alisa
 * version 1.0.
 */
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResizeRequestImpl extends AbstractKrakenRequest {

    private ResizeItem resize;

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
        private ResizeItem resize;

        public SyncBuilder resize(ResizeItem resize) {
            this.resize = resize;
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

        public ResizeRequestImpl build() {
            ResizeRequestImpl request = new ResizeRequestImpl();
            request.setLossy(lossy);
            request.setQuality(quality);
            request.setWait(true);
            request.setUrl(url);
            request.setCallbackUrl(null);
            request.setResize(resize);
            return request;
        }
    }

    public static class AsyncBuilder {
        private boolean lossy;
        private int quality;
        private String url;
        private String callbackUrl;
        private ResizeItem resize;

        public AsyncBuilder resize(ResizeItem resize) {
            this.resize = resize;
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

        public ResizeRequestImpl build() {
            ResizeRequestImpl request = new ResizeRequestImpl();
            request.setLossy(lossy);
            request.setQuality(quality);
            request.setWait(false);
            request.setUrl(url);
            request.setCallbackUrl(callbackUrl);
            request.setResize(resize);
            return request;
        }
    }
}
