package com.example.seulibapp.controller;

import com.example.seulibapp.dao.ReservationDao;
import com.example.seulibapp.entity.Book;
import com.example.seulibapp.entity.RecordVo;
import com.example.seulibapp.entity.Reservation;
import com.example.seulibapp.entity.ReservationVo;
import com.example.seulibapp.request.CommonRequest;
import com.example.seulibapp.service.ElasticsearchService;
import com.example.seulibapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@Scope("prototype")
@RequestMapping("/reservation")
public class ReservationController {
    @Autowired
    ReservationDao reservationDao;
    @Autowired
    ElasticsearchService elasticsearchService;
    @Autowired
    UserService userService;

    /**
     * 前端展示用户预约记录
     * @param userId
     * @return
     */
    @GetMapping("/queryByUserId")
    public List<ReservationVo> queryByUserId(@RequestParam("userId") String userId) {
        List<Reservation> reservationList=reservationDao.findByUserId(userId);
        String userName=userService.getUserById(userId).getUserName();
        List<Book>books=elasticsearchService.getAllBooksFromDb();
        Map<Long,String> bookIdToName=new HashMap<>();//减少查询次数，一次查询将书名和Id对应
        for(int i=0;i<books.size();i++){
            bookIdToName.put(books.get(i).getBid(),books.get(i).getBname());
        }
        return reservationList.stream()
                .map(reservation -> new ReservationVo(
                        reservation,
                        bookIdToName.get(reservation.getBid()),           // 获取书名
                        userName// 获取用户名
                ))
                .collect(Collectors.toList());
    }

    @PostMapping("reserve")
    public ResponseEntity<?> reserva(@RequestBody CommonRequest request) {
        Reservation reservation=new Reservation();
        reservation.setUserId(request.getUserId());
        reservation.setBid(request.getBookId());
        reservation.setReservationDate(String.valueOf(LocalDate.now()));
        reservationDao.save(reservation);
        return ResponseEntity.ok("您已预约成功！");
    }
}
