package com.orange.score.common.utils;

public class RequestPairBuilder {

    public static RequestPair build(String key, Object value) {
        return new RequestPair(key, value);
    }
}
