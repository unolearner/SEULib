package com.example.seulibapp.service;

import com.example.seulibapp.entity.Book;

import java.util.List;

public interface BookService {
    public List<Book> getBookList();
    public Book getBookById(int id);
    public int addBook(Book book);
    public int updateBook(Book book);
    public int deleteBook(int id);
}
