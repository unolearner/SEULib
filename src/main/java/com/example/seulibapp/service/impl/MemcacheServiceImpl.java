package com.example.seulibapp.service.impl;

import com.example.seulibapp.entity.User;
import com.example.seulibapp.service.MemcacheService;
import jakarta.annotation.Resource;
import net.spy.memcached.MemcachedClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MemcacheServiceImpl implements MemcacheService {

    private static final Logger log = LoggerFactory.getLogger(MemcacheServiceImpl.class);

    @Resource
    private MemcachedClient memcachedClient;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> void addToCache(String key, int expiration, T value) throws ExecutionException, InterruptedException {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Key or value cannot be null");
        }
        String jsonString = serialize(value);
        memcachedClient.set(key, expiration, jsonString).get();
        log.debug("Added to cache with key: {}", key);
    }

    @Override
    public <T> T getFromCache(String key, Class<T> type) throws ExecutionException, InterruptedException {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        Object result = memcachedClient.get(key);
        if (result == null) {
            log.warn("Cache entry for key: {} is null", key);
            return null;
        }

        if (!(result instanceof String)) {
            log.error("Cached data is not a string: {}", result.getClass());
            throw new RuntimeException("Cached data is not a string: " + result.getClass());
        }

        String jsonString = (String) result;
        log.debug("Retrieved JSON from cache with key {}: {}", key, jsonString);

        return deserialize(jsonString, type);
    }

    @Override
    public void deleteFromCache(String key) throws ExecutionException, InterruptedException {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        memcachedClient.delete(key).get();
        log.debug("Deleted from cache with key: {}", key);
    }

    private <T> String serialize(T value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (IOException e) {
            log.error("Failed to serialize object", e);
            throw new RuntimeException("Failed to serialize object", e);
        }
    }

    private <T> T deserialize(String jsonString, Class<T> type) {
        if (jsonString == null || jsonString.trim().isEmpty()) {
            log.warn("JSON string is empty or null");
            throw new IllegalArgumentException("JSON string is empty or null");
        }
        log.debug("Deserializing JSON: {}", jsonString);
        try {
            return objectMapper.readValue(jsonString, type);
        } catch (IOException e) {
            log.error("Failed to deserialize object: {}", jsonString, e);
            throw new RuntimeException("Failed to deserialize object: " + jsonString, e);
        }
    }
}