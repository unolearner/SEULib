package com.example.seulibapp.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public interface MemcacheService {

    public void addToCache(String key, int expiration, String value) throws
            ExecutionException, InterruptedException;

    public Object getFromCache(String key) throws ExecutionException,
            InterruptedException;


    public void deleteFromCache(String key) throws ExecutionException,
            InterruptedException;
}
