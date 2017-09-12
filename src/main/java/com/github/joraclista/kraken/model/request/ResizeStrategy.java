package com.github.joraclista.kraken.model.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by Alisa
 * version 1.0.
 */
public enum ResizeStrategy {
    FIT, CROP, EXACT, AUTO, PORTRAIT, LANDSCAPE, FILL, SQUARE;

    @JsonValue
    public String getValue(){
        return name().toLowerCase();
    }

    @JsonCreator
    static ResizeStrategy from(String value) {
        try {
            return ResizeStrategy.valueOf(value.toUpperCase());
        } catch (Exception e) {
            return PORTRAIT;
        }
    }
}
