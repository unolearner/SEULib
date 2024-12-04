package com.example.seulibapp.service;



import com.example.seulibapp.entity.Book;

import java.util.List;

public interface ElasticsearchSyncService {
    // 同步数据库书籍数据到 Elasticsearch
    void syncBooksToElasticsearch(List<Book> booksFromDb);
}

