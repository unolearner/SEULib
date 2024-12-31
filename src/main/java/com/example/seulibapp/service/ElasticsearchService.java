package com.example.seulibapp.service;

import com.example.seulibapp.entity.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ElasticsearchService {

    // 保存书籍到 Elasticsearch
    Book saveBook(Book book);

    //根据ID搜索书本
    Book searchBookById(Long id);

    // 根据书名搜索书籍
    List<Book> searchBooksByName(String keyword);

    // 根据作者模糊搜索书籍
    List<Book> searchBooksByAuthor(String author);

    // 根据出版社模糊搜索书籍
    List<Book> searchBooksByPrinter(String printer);

    // 根据类型模糊搜索书籍
    List<Book> searchBooksByType(String type);

    // 获取所有书籍
    Iterable<Book> getAllBooks();


    // 删除书籍
    boolean deleteBook(Long bid);

    // 从数据库获取书籍
    List<Book> getAllBooksFromDb();

    // 获取销量前十的书籍
    List<Book> getTop10BooksBySales();

    // 处理从Excel里导入的新书
    void processImportedBooks(List<Book> importedBooks);
}
