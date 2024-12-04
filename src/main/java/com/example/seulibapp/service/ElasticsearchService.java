package com.example.seulibapp.service;

import com.example.seulibapp.entity.Book;

import java.util.List;

public interface ElasticsearchService {

    // 保存书籍到 Elasticsearch
    void saveBook(Book book);

    // 根据书名搜索书籍
    List<Book> searchBooksByName(String keyword);

    // 根据作者模糊搜索书籍
    List<Book> searchBooksByWriter(String writer);

    // 根据出版社模糊搜索书籍
    List<Book> searchBooksByPrinter(String printer);

    // 根据类型模糊搜索书籍
    List<Book> searchBooksByType(String type);

    // 获取所有书籍
    Iterable<Book> getAllBooks();


    // 删除书籍
    void deleteBook(Integer bid);

    // 从数据库获取书籍
    List<Book> getAllBooksFromDb();
}
