package com.github.joraclista.kraken.config;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Created by Alisa
 * version 1.0.
 */
public enum Mode {
    SANDBOX, LIVE;

    @JsonCreator
    public static Mode from(String value) {
        Mode mode = LIVE;
        try {
            mode = valueOf(value.toUpperCase());
        } catch (Exception e) {
        }
        return mode;
    }
}
