package com.example.seulibapp.repository;

import com.example.seulibapp.entity.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("bookRepository")
public interface BookRepository extends ElasticsearchRepository<Book, Long> {

    // 根据书名模糊查询书籍
    List<Book> findByBnameContaining(String keyword);

    // 根据作者类型模糊查询书籍
    List<Book> findByAuthorContaining(String author);

    // 根据出版商模糊查询书籍
    List<Book> findByPrinterContaining(String printer);

    // 根据类型模糊查询书籍
    List<Book> findByTypeContaining(String type);

    // 查询销量前10的书籍
    List<Book> findTop10ByOrderBySalesDesc();

    // 按书名，作者，出版社，出版日期精确搜索书籍
    Book findByBnameAndAuthorAndPrinterAndDate(String bname, String author, String publisher,String date);


}
