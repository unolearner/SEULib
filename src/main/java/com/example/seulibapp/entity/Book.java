package com.example.seulibapp.entity;



import jakarta.persistence.Entity;
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
    private String bid;

    @Field(type = FieldType.Text) // 支持全文搜索
    private String bname;

    @Field(type = FieldType.Text) // 支持全文搜索
    private String detail;

    @Field(type = FieldType.Keyword) // 精确匹配
    private String pirce;

    @Field(type = FieldType.Keyword) // 精确匹配
    private String writer;

    @Field(type = FieldType.Keyword) // 精确匹配
    private String printer;

    @Field(type = FieldType.Date, format = {}, pattern = "yyyy-MM-dd") // 日期类型
    private Date date;

    @Field(type = FieldType.Keyword) // 分类字段
    private String type;

    @Field(type = FieldType.Text) // 图片路径可以作为全文搜索内容
    private String image;

    @Field(type = FieldType.Integer) // 数字类型
    private Integer store;
}
