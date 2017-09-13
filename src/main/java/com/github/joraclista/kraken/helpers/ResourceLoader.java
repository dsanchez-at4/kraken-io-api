package com.github.joraclista.kraken.helpers;

import java.io.InputStream;
import java.net.URL;

import static org.springframework.util.ClassUtils.getDefaultClassLoader;

/**
 * Created by Alisa
 * version 1.0.
 */
public interface ResourceLoader {

    static InputStream load(String resourceName){
        return load(resourceName, getDefaultClassLoader());
    }

    static InputStream load(String resourceName, ClassLoader classLoader){
        return classLoader.getResourceAsStream(resourceName);
    }

    static URL getResource(String resourceName, ClassLoader classLoader) {
        try {
            return classLoader.getResource(resourceName);
        } catch (Throwable t) {
            return null;
        }
    }

    static URL getResource(String resourceName) {
        return getResource(resourceName, getDefaultClassLoader());
    }
}
