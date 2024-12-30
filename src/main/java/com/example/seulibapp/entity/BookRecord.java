package com.example.seulibapp.entity;

import com.example.seulibapp.myEnum.ActionType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="record")
public class BookRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @ManyToOne
    @JoinColumn(name = "bid")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String actionDate;

    @Enumerated(EnumType.STRING)
    private ActionType actionType;
}
