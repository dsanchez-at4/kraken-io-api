package com.github.joraclista.kraken.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Created by Alisa
 * version 1.0.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResizeResponse {
    private String id;
    private String file_name;
    private boolean success;
    private String message;
    Map<String, ResizeResult> results;
}
