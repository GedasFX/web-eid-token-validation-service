package dev.gedas.webeid.rest.models;

import eu.webeid.security.authtoken.WebEidAuthToken;

public class IdTokenValidationRequest {
    private WebEidAuthToken token;
    private String nonce;

    public WebEidAuthToken getToken() {
        return token;
    }

    public String getNonce() {
        return nonce;
    }
}
