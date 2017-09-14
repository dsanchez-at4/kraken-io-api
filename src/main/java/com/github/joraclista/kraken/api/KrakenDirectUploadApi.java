package com.github.joraclista.kraken.api;

import com.github.joraclista.kraken.model.request.MultipleResizeRequestImpl;
import com.github.joraclista.kraken.model.request.OptimizeRequestImpl;
import com.github.joraclista.kraken.model.request.ResizeRequestImpl;
import com.github.joraclista.kraken.model.response.AbstractKrakenResponse.MultipleResizeResponseImpl;
import com.github.joraclista.kraken.model.response.AbstractKrakenResponse.OptimizeResponseImpl;
import com.github.joraclista.kraken.model.response.AbstractKrakenResponse.SingleResizeResponseImpl;

import java.io.File;

/**
 * Created by Alisa
 * version 1.0.
 */
public interface KrakenDirectUploadApi {

    OptimizeResponseImpl post(File file, OptimizeRequestImpl request);

    SingleResizeResponseImpl post(File file, ResizeRequestImpl request);

    MultipleResizeResponseImpl post(File file, MultipleResizeRequestImpl request);

    boolean isLiveMode();

}
