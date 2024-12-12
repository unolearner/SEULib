package com.example.seulibapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;


@Configuration
@EnableElasticsearchRepositories(
        basePackages = "com.example.seulibapp.repository", // Elasticsearch 存储库接口所在的包
        elasticsearchTemplateRef = "elasticsearchTemplate"
)
public class ElasticsearchConfig {

    @Bean
    public RestClient restClient() {
        // 配置 HTTP 主机地址
        HttpHost httpHost = new HttpHost("192.168.204.132", 9200, "http");
        return RestClient.builder(httpHost).build();
    }

    @Bean
    public ClientConfiguration clientConfiguration() {
        // 配置 Elasticsearch 客户端配置
        return ClientConfiguration.builder()
                .connectedTo("192.168.204.132:9200")  // 配置连接的地址
                .build();
    }
}
