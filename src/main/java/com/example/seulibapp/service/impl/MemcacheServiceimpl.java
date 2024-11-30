package com.example.seulibapp.service.impl;

import com.example.seulibapp.service.MemcacheService;
import jakarta.annotation.Resource;
import net.spy.memcached.MemcachedClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;


@Service
public class MemcacheServiceimpl implements MemcacheService {

    @Resource
    private MemcachedClient memcachedClient;

    public void addToCache(String key, int expiration, String value) throws
            ExecutionException, InterruptedException {
        memcachedClient.set(key, expiration, value).get();
    }
    public Object getFromCache(String key) throws ExecutionException,
            InterruptedException {
        return memcachedClient.get(key);
    }
    public void deleteFromCache(String key) throws ExecutionException,
            InterruptedException {
        memcachedClient.delete(key).get();
    }
}
