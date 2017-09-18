package com.github.joraclista.kraken.api;

import com.github.joraclista.kraken.model.request.MultipleResizeRequestImpl;
import com.github.joraclista.kraken.model.request.OptimizeRequestImpl;
import com.github.joraclista.kraken.model.request.ResizeRequestImpl;
import com.github.joraclista.kraken.model.response.AbstractKrakenResponse.MultipleResizeResponseImpl;
import com.github.joraclista.kraken.model.response.AbstractKrakenResponse.OptimizeResponseImpl;
import com.github.joraclista.kraken.model.response.AbstractKrakenResponse.SingleResizeResponseImpl;
import com.github.joraclista.kraken.model.response.AbstractKrakenResponse.UserStatusResponseImpl;

/**
 * Created by Alisa
 * version 1.0.
 */
public interface KrakenApi {

    OptimizeResponseImpl post(OptimizeRequestImpl request);

    SingleResizeResponseImpl post(ResizeRequestImpl request);

    MultipleResizeResponseImpl post(MultipleResizeRequestImpl request);

    UserStatusResponseImpl getStatus();

    boolean isLiveMode();

}
