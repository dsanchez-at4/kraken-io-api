package com.github.joraclista.kraken.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    private ResizeStrategy strategy;

    private int width;
    private int height;

    private int size;
    private int scale;

    private int x;
    private int y;

    private String background;

    @JsonProperty("storage_path")
    private String storagePath;
}
