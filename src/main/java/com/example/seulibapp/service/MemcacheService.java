package com.example.seulibapp.service;

import com.example.seulibapp.entity.User;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public interface MemcacheService {

    /**
     * 将值添加到缓存中。
     *
     * @param key        缓存键
     * @param expiration 过期时间（秒）
     * @param value      要存储的对象
     * @param <T>        存储对象的类型
     * @throws ExecutionException   如果执行过程中发生错误
     * @throws InterruptedException 如果当前线程被中断
     */
    <T> void addToCache(String key, int expiration, T value) throws ExecutionException, InterruptedException;

    /**
     * 从缓存中获取值。
     *
     * @param key 缓存键
     * @param <T> 获取对象的类型
     * @return 缓存中的对象，如果不存在则为 null
     * @throws ExecutionException   如果执行过程中发生错误
     * @throws InterruptedException 如果当前线程被中断
     */
    <T> T getFromCache(String key, Class<T> type) throws ExecutionException, InterruptedException;

    /**
     * 从缓存中删除指定的键。
     *
     * @param key 缓存键
     * @throws ExecutionException   如果执行过程中发生错误
     * @throws InterruptedException 如果当前线程被中断
     */
    void deleteFromCache(String key) throws ExecutionException, InterruptedException;
}