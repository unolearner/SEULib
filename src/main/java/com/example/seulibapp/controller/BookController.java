package com.example.seulibapp.controller;

import com.example.seulibapp.entity.Book;
import com.example.seulibapp.service.ElasticsearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Scope("prototype")
@RequestMapping("/books")
public class BookController {

    @Autowired
    private ElasticsearchService elasticsearchService;  // 注入接口

    // 添加书籍
    @PostMapping("/add")
    public void addBook(@RequestBody Book book) {
        elasticsearchService.saveBook(book);  // 保存书籍
    }

    // 搜索书籍
    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam String keyword) {
        return elasticsearchService.searchBooksByName(keyword);  // Elasticsearch 搜索
    }

    // 获取所有书籍
    @GetMapping("/all")
    public Iterable<Book> getAllBooks() {
        return elasticsearchService.getAllBooks();  // 获取所有书籍
    }



    // 删除书籍
    @DeleteMapping("/{bid}")
    public void deleteBook(@PathVariable String bid) {
        elasticsearchService.deleteBook(bid);  // 删除书籍
    }

}
