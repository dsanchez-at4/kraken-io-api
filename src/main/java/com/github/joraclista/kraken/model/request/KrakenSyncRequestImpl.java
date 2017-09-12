package com.github.joraclista.kraken.model.request;


import com.github.joraclista.kraken.auth.Auth;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by Alisa
 * version 1.0.
 */
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KrakenSyncRequestImpl implements KrakenRequest {

    private Auth auth;
    private boolean wait = true;
    private boolean lossy;
    private String url;
    private List<ResizeItem> resize;
    private String imageFinalName;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Auth auth;
        private boolean lossy;
        private String url;
        private List<ResizeItem> resize;

        public Builder auth(Auth auth) {
            this.auth = auth;
            return this;
        }

        public Builder lossy(boolean lossy) {
            this.lossy = lossy;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder resize(List<ResizeItem> resize) {
            this.resize = resize;
            return this;
        }

        public KrakenSyncRequestImpl build() {
            KrakenSyncRequestImpl request = new KrakenSyncRequestImpl();
            request.setAuth(auth);
            request.setLossy(lossy);
            request.setUrl(url);
            request.setResize(resize);
            return request;
        }
    }

}
