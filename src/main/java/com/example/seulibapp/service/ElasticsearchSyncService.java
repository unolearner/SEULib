package com.example.seulibapp.service;



import com.example.seulibapp.entity.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ElasticsearchSyncService {
    // 同步数据库书籍数据到 Elasticsearch
    void syncBooksToElasticsearch(List<Book> booksFromDb);

    // 删除ES中的所有书籍
    public void deleteAllBooksFromElasticsearch();
}

