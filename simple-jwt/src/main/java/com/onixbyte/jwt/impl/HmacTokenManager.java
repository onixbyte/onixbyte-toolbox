package com.onixbyte.jwt.impl;

import com.onixbyte.devkit.utils.MapUtil;
import com.onixbyte.devkit.utils.ObjectMapAdapter;
import com.onixbyte.jwt.TokenCreator;
import com.onixbyte.jwt.TokenManager;
import com.onixbyte.jwt.TokenPayload;
import com.onixbyte.jwt.TokenResolver;
import com.onixbyte.jwt.constant.Algorithm;

import java.util.Map;

public class HmacTokenManager<T> implements TokenManager<T> {

    private final TokenCreator tokenCreator;
    private final TokenResolver tokenResolver;
    private final ObjectMapAdapter<T> adapter;

    public HmacTokenManager(Algorithm algorithm, String issuer, String secret, ObjectMapAdapter<T> adapter) {
        this.tokenCreator = new HmacTokenCreator(algorithm, issuer, secret);
        this.tokenResolver = new HmacTokenResolver(algorithm, secret);
        this.adapter = adapter;
    }

    @Override
    public T extract(String token) {
        var payloadMap = getPayload(token);
        return MapUtil.mapToObject(payloadMap, adapter);
    }

    @Override
    public String sign(TokenPayload payload) {
        return tokenCreator.sign(payload);
    }

    @Override
    public void verify(String token) {
        tokenResolver.verify(token);
    }

    @Override
    public Map<String, String> getHeader(String token) {
        return tokenResolver.getHeader(token);
    }

    @Override
    public Map<String, Object> getPayload(String token) {
        return tokenResolver.getPayload(token);
    }
}
