package com.example.seulibapp.service.impl;

import com.example.seulibapp.entity.Book;
import com.example.seulibapp.repository.BookRepository;
import com.example.seulibapp.service.ElasticsearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElasticsearchServiceimpl implements ElasticsearchService {

    @Autowired
    private BookRepository bookRepository;  // 注入 BookRepository 进行 Elasticsearch 操作

    @Override
    public void saveBook(Book book) {
        bookRepository.save(book);  // 保存书籍
    }

    @Override
    @Cacheable(value = "books", key = "#keyword")
    public List<Book> searchBooksByName(String keyword) {
        return bookRepository.findByBnameContaining(keyword);  // Elasticsearch 搜索操作
    }

    @Override
    public Iterable<Book> getAllBooks() {
        return bookRepository.findAll();  // 获取所有书籍
    }

    @Override
    public Book getBookById(Integer bid) {
        return bookRepository.findById(bid).orElse(null);  // 根据ID查询书籍
    }

    @Override
    public void deleteBook(Integer bid) {
        bookRepository.deleteById(bid);  // 删除书籍
    }
}
