package com.example.seulibapp.controller;

import com.example.seulibapp.entity.Book;
import com.example.seulibapp.entity.User;
import com.example.seulibapp.repository.BookRepository;
import com.example.seulibapp.request.BorrowRequest;
import com.example.seulibapp.response.MyErrorResponse;
import com.example.seulibapp.service.ElasticsearchService;
import com.example.seulibapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Scope("prototype")
@RequestMapping("/books")
public class BookController {

    @Autowired
    private ElasticsearchService elasticsearchService;  // 注入接口
    @Autowired
    private UserService userService;

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

    /**
     * 查询销量前十的书籍
     * @return 前十书籍的列表
     */
    @GetMapping("/top-books")
    public List<Book> getTop10Books() {
        return elasticsearchService.getTop10BooksBySales();
    }

    /**
     * 借阅书籍：sales++，store--
     * @return null
     */
    @PostMapping("/borrow")
    ResponseEntity<?>borrowBook(@RequestBody BorrowRequest request) {
        Book book=elasticsearchService.searchBookById(request.getBookId());
        if(book.getStore()==0){//库存不足，返回错误信息
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MyErrorResponse("Out of stock","库存不足，无法借阅"));
        }
        //库存足够，正常借阅，书籍库存减一，销量加一
        book.setStore(book.getStore()-1);
        book.setSales(book.getSales()+1);
        elasticsearchService.saveBook(book);
//        User user=userService.getUserById(request.getUserId());
        return ResponseEntity.ok("借阅成功,借阅人："+book.getStore()+" "+book.getSales());
    }
}
