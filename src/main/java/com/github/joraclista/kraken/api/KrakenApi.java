package com.github.joraclista.kraken.api;

import com.github.joraclista.kraken.model.request.KrakenRequest;
import com.github.joraclista.kraken.model.response.KrakenResponseImpl;

/**
 * Created by Alisa
 * version 1.0.
 */
public interface KrakenApi {

    KrakenResponseImpl post(KrakenRequest request);

}
