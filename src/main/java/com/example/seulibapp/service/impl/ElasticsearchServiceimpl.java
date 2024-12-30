package com.example.seulibapp.service.impl;

import com.example.seulibapp.dao.BookDao;
import com.example.seulibapp.entity.Book;
import com.example.seulibapp.repository.BookRepository;
import com.example.seulibapp.service.ElasticsearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


import org.springframework.data.domain.PageRequest;

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
    public Book searchBookById(Long id) {
        return bookRepository.findById(id).get();
    }

    @Override
    @Cacheable(value = "books", key = "#keyword")
    public List<Book> searchBooksByName(String keyword) {
        return bookRepository.findByBnameContaining(keyword);
    }

    @Override
    @Cacheable(value = "books", key = "#author")
    public List<Book> searchBooksByAuthor(String author) {
        return bookRepository.findByAuthorContaining(author);
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
    public void deleteBook(Long bid) {
        bookRepository.deleteById(bid);  // 删除书籍
    }

    @Override
    public List<Book> getAllBooksFromDb() {
        Iterable<Book> booksIterable = bookDao.findAll();
        return StreamSupport.stream(booksIterable.spliterator(), false)
                .collect(Collectors.toList()); // 使用 Stream 将 Iterable 转换为 List // 获取数据库中的所有书籍
    }

    @Override
    public List<Book> getTop10BooksBySales() {

        // 返回销量前10的书籍
        return bookRepository.findTop10ByOrderBySalesDesc();
    }


    @Override
    public void processImportedBooks(List<Book> importedBooks) {
        List<Book> newBooks = new ArrayList<>(); // 用于保存不存在的书籍
        List<Book> updatedBooks = new ArrayList<>(); // 用于保存需要更新库存的书籍

        for (Book importedBook : importedBooks) {
            // 根据书名，作者，出版社，出版日期 查询是否已存在
            Book existingBook = bookRepository.findByBnameAndAuthorAndPrinterAndDate(importedBook.getBname(),
                    importedBook.getAuthor(),importedBook.getPrinter(),importedBook.getDate());

            if (existingBook != null) {
                int store = importedBook.getStore() +existingBook.getStore();
                // 如果书已存在，更新库存
                existingBook.setStore(store);
                updatedBooks.add(existingBook);
            } else {

                // 如果书不存在，作为新书添加
                importedBook.setBid(UUID.randomUUID().getMostSignificantBits());
                importedBook.setSales(0);
                newBooks.add(importedBook);
            }
        }
        System.out.println("新书：");
        System.out.println(newBooks);
        System.out.println("已存在的书籍:");
        System.out.println(updatedBooks);
        // 批量保存更新的书籍
        if (!updatedBooks.isEmpty()) {
            bookRepository.saveAll(updatedBooks);
        }

        // 批量保存新书
        if (!newBooks.isEmpty()) {
            bookRepository.saveAll(newBooks);
        }
    }
}
