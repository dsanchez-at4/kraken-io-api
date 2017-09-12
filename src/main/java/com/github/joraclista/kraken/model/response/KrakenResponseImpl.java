package com.github.joraclista.kraken.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Created by Alisa
 * version 1.0.
 */
@Data
@NoArgsConstructor
public class KrakenResponseImpl extends ResizeResponseItem implements KrakenResponse {

    private Integer httpStatusCode;

    private String httpStatusText;

    private String id;

    private String imageOriginalUrl;

    private String generatedFileName;

    private boolean success = true;

    private String message;

    private Map<String, ResizeResponseItem> results;


    public KrakenResponseImpl withSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public KrakenResponseImpl withMessage(String message) {
        this.message = message;
        return this;
    }

    public KrakenResponseImpl withImageOriginalUrl(String imageOriginalUrl) {
        this.imageOriginalUrl = imageOriginalUrl;
        return this;
    }

    public KrakenResponseImpl withGeneratedFileName(String generatedFileName) {
        this.generatedFileName = generatedFileName;
        return this;
    }

    public KrakenResponseImpl withHttpStatusCode(Integer httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
        return this;
    }

    public KrakenResponseImpl withHttpStatusText(String httpStatusText) {
        this.httpStatusText = httpStatusText;
        return this;
    }

}
