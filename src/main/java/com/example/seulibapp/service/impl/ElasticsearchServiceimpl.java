package com.example.seulibapp.service.impl;

import com.example.seulibapp.dao.BookDao;
import com.example.seulibapp.entity.Book;
import com.example.seulibapp.repository.BookRepository;
import com.example.seulibapp.service.ElasticsearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ElasticsearchServiceimpl implements ElasticsearchService {

    @Autowired
    @Qualifier("bookRepository")
    private BookRepository bookRepository;  // 注入 BookRepository 进行 Elasticsearch 操作
    @Autowired
    @Qualifier("bookDao")
    private BookDao bookDao;


    @Override
    public void saveBook(Book book) {
        bookRepository.save(book);  // 保存书籍
    }

    @Override
    @Cacheable(value = "books", key = "#keyword")
    public List<Book> searchBooksByName(String keyword) {
        return bookRepository.findByBnameContaining(keyword);
    }

    @Override
    @Cacheable(value = "books", key = "#writer")
    public List<Book> searchBooksByWriter(String writer) {
        return bookRepository.findByWriterContaining(writer);
    }

    @Override
    @Cacheable(value = "books", key = "#printer")
    public List<Book> searchBooksByPrinter(String printer) {
       return bookRepository.findByPrinterContaining(printer);
    }

    @Override
    @Cacheable(value = "books", key = "#type")
    public List<Book> searchBooksByType(String type) {
        return bookRepository.findByTypeContaining(type);
    }

    @Override
    public Iterable<Book> getAllBooks() {
        return bookRepository.findAll();  // 获取所有书籍
    }


    @Override
    public void deleteBook(String bid) {
        bookRepository.deleteById(bid);  // 删除书籍
    }

    @Override
    public List<Book> getAllBooksFromDb() {
        Iterable<Book> booksIterable = bookDao.findAll();
        return StreamSupport.stream(booksIterable.spliterator(), false)
                .collect(Collectors.toList()); // 使用 Stream 将 Iterable 转换为 List // 获取数据库中的所有书籍
    }

}
