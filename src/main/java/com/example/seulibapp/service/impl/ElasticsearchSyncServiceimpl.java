package com.example.seulibapp.service.impl;


import com.example.seulibapp.entity.Book;
import com.example.seulibapp.repository.BookRepository;
import com.example.seulibapp.service.ElasticsearchSyncService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElasticsearchSyncServiceimpl implements ElasticsearchSyncService {

    private BookRepository bookRepository;


    // 从数据库同步书籍到 Elasticsearch
    // 同步数据库数据到 Elasticsearch
    @Override
    public void syncBooksToElasticsearch(List<Book> booksFromDb) {
        for (Book book : booksFromDb) {
            // 在 Elasticsearch 中检查是否已经存在该书籍（使用 book.getBid() 作为唯一标识符）
            if (bookRepository.existsById(book.getBid())) {
                // 如果存在，更新数据
                bookRepository.save(book);
            } else {
                // 如果不存在，插入新数据
                bookRepository.save(book);
            }
        }
        System.out.println("数据库数据同步到 Elasticsearch 完成");
    }

}

