package com.example.seulibapp.entity;

import com.example.seulibapp.myEnum.ActionType;
import jakarta.persistence.Entity;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 用于前端展示record
 */
@Data
public class RecordVo extends BookRecord {
    private String bookName;
    private String userName;
    private String returnDate;

    // 构造函数
    public RecordVo(BookRecord record, String userName, String bookName) {
        this.userName = userName;
        this.bookName = bookName;
        this.setId(record.getId());
        this.setBid(record.getBid());
        this.setUserId(record.getUserId());
        this.setActionDate(record.getActionDate());
        this.setActionType(record.getActionType());
        // 定义日期格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if(this.getActionType() == ActionType.BORROW || this.getActionType() == ActionType.RETURN){
            this.setReturnDate(String.valueOf(LocalDate.parse(record.getActionDate(),formatter).
                    plusMonths(1)));
        }
        if(this.getActionType()==ActionType.REBORROW){
            this.setReturnDate(String.valueOf(LocalDate.parse(record.getActionDate(),formatter).
                    plusMonths(2)));
        }
    }

    public RecordVo() {

    }
}
