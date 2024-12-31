package com.example.seulibapp.config;

import com.example.seulibapp.service.ElasticsearchSyncService;
import com.example.seulibapp.service.ElasticsearchService;
import com.example.seulibapp.entity.Book;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.Response;

import java.io.IOException;
import java.util.List;

@Configuration
public class ElasticsearchSyncConfig {

    // Bean that will sync data to Elasticsearch after startup
    @Bean
    public CommandLineRunner syncDataToElasticsearch(ElasticsearchSyncService elasticsearchSyncService,
                                                     ElasticsearchService bookService,
                                                     RestClient restClient) {
        return args -> {
            // Delete all books in Elasticsearch
            elasticsearchSyncService.deleteAllBooksFromElasticsearch();
            // Configure the IK Analyzer for the "Book" index
            createIndexWithIKAnalyzer(restClient);


            // Get all books from the database
            List<Book> booksFromDb = bookService.getAllBooksFromDb();
            System.out.println(booksFromDb);
            // Sync books to Elasticsearch
            elasticsearchSyncService.syncBooksToElasticsearch(booksFromDb);
        };
    }

    private void createIndexWithIKAnalyzer(RestClient restClient) {
        try {
            // 1. 删除已有的索引（如果已存在）
            Request deleteRequest = new Request("DELETE", "/books");
            Response deleteResponse = restClient.performRequest(deleteRequest);
            System.out.println("Deleted existing index (if any): " + deleteResponse.getStatusLine());

            // 2. 创建索引的设置（包括IK分词器）
            String settingsJson = "{\n" +
                    "  \"settings\": {\n" +
                    "    \"analysis\": {\n" +
                    "      \"tokenizer\": {\n" +
                    "        \"ik_max_word\": {\n" +
                    "          \"type\": \"ik_max_word\"\n" +
                    "        },\n" +
                    "        \"ik_smart\": {\n" +
                    "          \"type\": \"ik_smart\"\n" +
                    "        }\n" +
                    "      },\n" +
                    "      \"analyzer\": {\n" +
                    "        \"default\": {\n" +
                    "          \"type\": \"custom\",\n" +
                    "          \"tokenizer\": \"ik_max_word\"\n" +
                    "        }\n" +
                    "      }\n" +
                    "    }\n" +
                    "  }\n" +
                    "}";

            // 3. 创建索引请求
            Request createRequest = new Request("PUT", "/books");
            createRequest.setJsonEntity(settingsJson);

            // 4. 发送请求以创建索引
            Response createResponse = restClient.performRequest(createRequest);
            System.out.println("Index creation response: " + createResponse.getStatusLine());

        } catch (IOException e) {
            System.err.println("Error creating index with IK Analyzer: " + e.getMessage());
        }
    }
}
