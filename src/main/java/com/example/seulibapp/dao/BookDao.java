package com.example.seulibapp.dao;

import com.example.seulibapp.entity.Book;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository("bookDao")
public interface BookDao extends JpaRepository<Book, Long> {
    //自动实现，查询所有书籍
    List<Book> findAll();
}
