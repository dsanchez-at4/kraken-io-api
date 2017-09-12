package com.github.joraclista.kraken.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.joraclista.kraken.api.ResizeStrategy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Alisa
 * version 1.0.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResizeItem {
    private String id;
    private ResizeStrategy strategy = ResizeStrategy.FIT;
    private int width;
    private int height;
    private int size;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String background;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("storage_path")
    private String storagePath;
}
