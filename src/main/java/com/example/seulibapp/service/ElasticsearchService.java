package com.example.seulibapp.service;

import com.example.seulibapp.entity.Book;

import java.util.List;

public interface ElasticsearchService {

    // 保存书籍到 Elasticsearch
    void saveBook(Book book);

    // 根据书名搜索书籍
    List<Book> searchBooksByName(String keyword);

    // 获取所有书籍
    Iterable<Book> getAllBooks();

    // 根据书籍ID获取书籍
    Book getBookById(Integer bid);

    // 删除书籍
    void deleteBook(Integer bid);
}
