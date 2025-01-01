package com.example.seulibapp.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import lombok.Data;


@Data
public class ReservationVo extends Reservation {
    private String bookName;
    private String userName;

    public ReservationVo() {}

    public ReservationVo(Reservation reservation,String bookName, String userName) {
        this.setId(reservation.getId());
        this.setBid(reservation.getBid());
        this.setUserId(reservation.getUserId());
        this.userName=userName;
        this.bookName=bookName;
        this.setReservationDate(reservation.getReservationDate());
    }
}
