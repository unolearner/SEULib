package com.example.seulibapp.service.impl;

import com.example.seulibapp.dao.BookDao;
import com.example.seulibapp.entity.Book;
import com.example.seulibapp.service.BookService;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Scope("prototype")
public class BookServiceimpl implements BookService {

    @Resource
    private BookDao bookDao;

    @Override
    public List<Book> getBookList() {
        return bookDao.getBookList();
    }

    @Override
    public Book getBookById(int id) {
        return bookDao.getBookById(id);
    }

    @Override
    public int addBook(Book book) {
        return bookDao.addBook(book);
    }

    @Override
    public int updateBook(Book book) {
        return bookDao.updateBook(book);
    }

    @Override
    public int deleteBook(int id) {
        return bookDao.deleteBook(id);
    }
}
