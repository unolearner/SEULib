package com.example.seulibapp.config;

import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.InetSocketAddress;

@Configuration
@ConfigurationProperties(prefix = "memcached")
public class MemcachedConfig {
    @Value("${memcached.ip}")
    private String ip;
    @Value("${memcached.port}")
    private int port;
    @Bean(name = "memcachedClient")
    public MemcachedClient getMemcachedClient() throws IOException {
        return new MemcachedClient(new InetSocketAddress(this.ip, this.port));
    }
}
