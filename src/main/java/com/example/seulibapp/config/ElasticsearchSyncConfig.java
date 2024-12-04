package com.example.seulibapp.config;

import com.example.seulibapp.service.ElasticsearchSyncService;
import com.example.seulibapp.service.ElasticsearchService;
import com.example.seulibapp.entity.Book;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ElasticsearchSyncConfig {

    @Bean
    public CommandLineRunner syncDataToElasticsearch(ElasticsearchSyncService elasticsearchSyncService, ElasticsearchService bookService) {
        return args -> {
            // 获取数据库中的所有书籍
            List<Book> booksFromDb = bookService.getAllBooksFromDb();

            // 同步到 Elasticsearch
            elasticsearchSyncService.syncBooksToElasticsearch(booksFromDb);
        };
    }
}
