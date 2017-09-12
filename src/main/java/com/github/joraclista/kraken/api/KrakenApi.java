package com.github.joraclista.kraken.api;

import com.github.joraclista.kraken.model.response.KrakenResponse;
import com.github.joraclista.kraken.model.request.KrakenRequest;

/**
 * Created by Alisa
 * version 1.0.
 */
public interface KrakenApi {

    KrakenResponse post(KrakenRequest request);

}
