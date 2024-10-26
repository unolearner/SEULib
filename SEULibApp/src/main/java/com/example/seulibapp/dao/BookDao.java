package com.example.seulibapp.dao;

import com.example.seulibapp.entity.Book;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookDao {
    public List<Book> getBookList();
    public Book getBookById(int id);
    public int addBook(Book book);
    public int updateBook(Book book);
    public int deleteBook(int id);
}
