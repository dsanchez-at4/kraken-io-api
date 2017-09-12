package com.github.joraclista.kraken.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by Alisa
 * version 1.0.
 */
public enum ResizeStrategy {
    FIT, CROP, EXACT, AUTO, PORTRAIT, LANDSCAPE, FILL;

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
