package com.onixbyte.jwt;

public interface TokenManager<T> extends TokenCreator, TokenResolver {

    T extract(String token);

}
