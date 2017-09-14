package com.github.joraclista.kraken.model.request;

import com.github.joraclista.kraken.auth.Auth;

/**
 * Created by Alisa
 * version 1.0.
 */
public interface KrakenRequest {

    Auth getAuth();

    void setAuth(Auth auth);

    void setDevMode(boolean devMode);

    String getUrl();

}
