package com.github.joraclista.kraken.api;

import com.github.joraclista.kraken.model.request.KrakenSyncRequestImpl.MultipleResizeRequestImpl;
import com.github.joraclista.kraken.model.request.KrakenSyncRequestImpl.OptimizeRequestImpl;
import com.github.joraclista.kraken.model.request.KrakenSyncRequestImpl.SingleResizeRequestImpl;
import com.github.joraclista.kraken.model.response.AbstractKrakenResponse.MultipleResizeResponseImpl;
import com.github.joraclista.kraken.model.response.AbstractKrakenResponse.OptimizeResponseImpl;
import com.github.joraclista.kraken.model.response.AbstractKrakenResponse.SingleResizeResponseImpl;

/**
 * Created by Alisa
 * version 1.0.
 */
public interface KrakenApi {

    OptimizeResponseImpl post(OptimizeRequestImpl request);

    SingleResizeResponseImpl post(SingleResizeRequestImpl request);

    MultipleResizeResponseImpl post(MultipleResizeRequestImpl request);

}
