package com.example.seulibapp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name="reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @ManyToOne
    @JoinColumn(name = "bid")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String reservationDate;
}
