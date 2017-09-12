package com.github.joraclista.kraken.api;

import lombok.Data;

/**
 * Created by Alisa
 * version 1.0.
 */
@Data
public class Size {
    private static int ORIGINAL_SIZE = 5000;

    private int width;
    private int height;

    private ResizeStrategy strategy = ResizeStrategy.PORTRAIT;

    private String background;
}
