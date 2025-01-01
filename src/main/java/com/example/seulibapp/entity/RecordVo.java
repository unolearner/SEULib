package com.example.seulibapp.entity;

import jakarta.persistence.Entity;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 用于前端展示record
 */
@Entity
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
        this.setActionDate(record.getActionDate());
        this.setActionType(record.getActionType());
        // 定义日期格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.setReturnDate(String.valueOf(LocalDate.parse(record.getActionDate(),formatter).plusMonths(1)));
    }

    public RecordVo() {

    }
}
