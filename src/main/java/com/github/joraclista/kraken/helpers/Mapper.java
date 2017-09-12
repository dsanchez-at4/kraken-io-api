package com.github.joraclista.kraken.helpers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Alisa
 * version 1.0.
 */
public interface Mapper {

    ObjectMapper MAPPER = new ObjectMapper()
            .configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    static <T> T readValue(InputStream src, Class<T> valueType) throws IOException {
        return MAPPER.readValue(src,valueType);
    }

    static String writeValueAsString(Object value) throws IOException {
        return MAPPER.writeValueAsString(value);
    }

    static <T> T readValue(String content, Class<T> valueType) throws IOException {
        return MAPPER.readValue(content, valueType);
    }
}
