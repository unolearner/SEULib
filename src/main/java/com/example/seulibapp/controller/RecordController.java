package com.example.seulibapp.controller;

import com.example.seulibapp.dao.RecordDao;
import com.example.seulibapp.dao.UserDao;
import com.example.seulibapp.entity.Book;
import com.example.seulibapp.entity.BookRecord;
import com.example.seulibapp.entity.RecordVo;
import com.example.seulibapp.service.ElasticsearchService;
import com.example.seulibapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@Scope("prototype")
@RequestMapping("/record")
public class RecordController {
    @Autowired
    RecordDao recordDao;
    @Autowired
    ElasticsearchService elasticsearchService;
    @Autowired
    UserService userService;

    /**
     * 前端展示用户借阅记录
     */
    @GetMapping("/queryByUserId")
    public List<RecordVo> queryUserRecord(@RequestParam("userId") String userId){
        String userName=userService.getUserById(userId).getUserName();
        List<Book>books=elasticsearchService.getAllBooksFromDb();
        Map<Long,String> bookIdToName=new HashMap<>();//减少查询次数，一次查询将书名和Id对应
        for(int i=0;i<books.size();i++){
            bookIdToName.put(books.get(i).getBid(),books.get(i).getBname());
        }
        List<BookRecord>records=recordDao.findByUserId(userId);
        return records.stream()
                .map(record -> new RecordVo(
                        record,
                        userName, // 获取用户名
                        bookIdToName.get(record.getBid())           // 获取书名
                ))
                .collect(Collectors.toList());
    }

}
