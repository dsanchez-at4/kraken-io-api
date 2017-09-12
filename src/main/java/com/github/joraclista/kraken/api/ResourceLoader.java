package com.github.joraclista.kraken.api;

import java.io.InputStream;

/**
 * Created by Alisa
 * version 1.0.
 */
public interface ResourceLoader {

    static InputStream load(String resourceName){
        return ResourceLoader.class.getClassLoader().getResourceAsStream(resourceName);
    }

}
