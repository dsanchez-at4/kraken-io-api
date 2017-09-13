package com.github.joraclista.kraken.config;

import com.github.joraclista.kraken.api.exceptions.KrakenApiConfigException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

/**
 * Created by Alisa
 * version 1.0.
 */
@Slf4j
public class ConfigLocation {

    private final ClassLoader classLoader;

    public ConfigLocation(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public File getConfig() {
        try {
            for (String location : getStandardConfigLocations()) {
                log.info("findConfig: trying to find config {}", location);
                ClassPathResource resource = new ClassPathResource(location,
                        this.classLoader);
                if (resource.exists()) {
                    return resource.getFile();
                }
                log.info("findConfig: config {} not found", location);
            }
        } catch (IOException e) {
            log.error("Couldn't get config file due to : {}", e.getMessage());
        }
        throw new KrakenApiConfigException("Config files not found: "
                + stream(getStandardConfigLocations()).collect(joining(", ")));
    }


    protected String[] getStandardConfigLocations() {
        return new String[] {"kraken-io-config.json", "kraken-io-default-config.json" };
    }
}
