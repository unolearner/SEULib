package com.example.seulibapp.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Book {
    private Integer bid;

    private String bname;

    private String detail;

    private String pirce;

    private String writer;

    private String printer;

    private Date date;

    private String type;

    private String image;

    private Integer store;
}
