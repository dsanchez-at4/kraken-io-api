package com.github.joraclista.kraken.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Alisa
 * version 1.0.
 */
@Data
@NoArgsConstructor
public class ResizeResponseItem {

    @JsonProperty("file_name")
    private String fileName;

    @JsonProperty("kraked_url")
    private String krakedUrl;

    @JsonProperty("original_width")
    private long originalWidth;

    @JsonProperty("original_height")
    private long originalHeight;

    @JsonProperty("kraked_width")
    private long krakedWidth;

    @JsonProperty("kraked_height")
    private long krakedHeight;

    @JsonProperty("kraked_size")
    private long krakedSize;

    @JsonProperty("original_size")
    private long originalSize;
}
