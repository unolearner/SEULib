package com.example.seulibapp.controller;

import com.example.seulibapp.service.BookService;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Scope("prototype")
@RequestMapping("/book")
public class BookController {

    @Resource
    private BookService bookService;

    @ResponseBody
    @RequestMapping("/list")
    public Object queryBookList(){
        return bookService.getBookList();
    }

}
