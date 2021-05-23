package com.example.backend.Services.cache;

// used for local cache service class.
public interface CacheService {
    // to set cache
    void setCommonCache(String key, Object value);

    // to get cache.
    Object getFromCommonCache(String key);
}
