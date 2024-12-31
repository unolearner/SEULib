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
            // Configure the IK Analyzer for the "Book" index
            createIndexWithIKAnalyzer(restClient);

            // Delete all books in Elasticsearch
            elasticsearchSyncService.deleteAllBooksFromElasticsearch();

            // Get all books from the database
            List<Book> booksFromDb = bookService.getAllBooksFromDb();
            System.out.println(booksFromDb);
            // Sync books to Elasticsearch
            elasticsearchSyncService.syncBooksToElasticsearch(booksFromDb);
        };
    }

    private void createIndexWithIKAnalyzer(RestClient restClient) {
        try {
            // Create the index settings with IK Analyzer
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

            // Create index request with settings
            Request request = new Request("PUT", "/book_index");
            request.setJsonEntity(settingsJson);

            // Send request to create the index
            Response response = restClient.performRequest(request);

            // Optionally log the response to verify index creation
            System.out.println("Index creation response: " + response.getStatusLine());
        } catch (IOException e) {
            System.err.println("Error creating index with IK Analyzer: " + e.getMessage());
        }
    }
}
