package com.github.joraclista.kraken.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by Alisa
 * version 1.0.
 */
@Data
public class ResizeResult {

    @JsonProperty("file_name")
    private String fileName;

    @JsonProperty("original_size")
    private int originalSize;

    @JsonProperty("kraked_size")
    private int krakedSize;

    @JsonProperty("saved_bytes")
    private int savedBytes;

    @JsonProperty("kraked_url")
    private String krakedUrl;

    @JsonProperty("original_width")
    private int originalWidth;

    @JsonProperty("original_height")
    private int originalHeight;

    @JsonProperty("kraked_width")
    private int krakedWidth;

    @JsonProperty("kraked_height")
    private int krakedHeight;
}
