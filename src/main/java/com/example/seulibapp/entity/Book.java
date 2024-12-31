package com.example.seulibapp.entity;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Entity
@Data
@Document(indexName = "books") // Elasticsearch 索引名称
public class Book {
    @Id
    @jakarta.persistence.Id
    @jakarta.persistence.GeneratedValue(strategy = GenerationType.IDENTITY) // 自增主键
    private Long bid;

    @Field(type = FieldType.Text,analyzer = "ik_max_word") // 支持全文搜索
    private String bname;

    @Field(type = FieldType.Text,analyzer = "ik_max_word") // 支持全文搜索
    private String detail;

    @Field(type = FieldType.Keyword) // 精确匹配
    private String price;

    @Field(type = FieldType.Keyword) // 精确匹配
    private String author;

    @Field(type = FieldType.Keyword) // 精确匹配
    private String printer;

    @Field(type = FieldType.Text, format = {}, pattern = "yyyy-MM-dd") // 日期类型
    private String date;

    @Field(type = FieldType.Keyword) // 分类字段
    private String type;

    @Field(type = FieldType.Text) // 图片路径可以作为全文搜索内容
    private String image;

    @Field(type = FieldType.Integer) // 数字类型
    private Integer store;

    @Field(type = FieldType.Integer)
    private Integer sales;    // 销量
}
