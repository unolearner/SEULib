package com.example.seulibapp.dao;

import com.example.seulibapp.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("reservationRepository")
public interface ReservationDao extends JpaRepository<Reservation, Long> {
    List<Reservation> findByBid(Long bookId);
    List<Reservation> findByUserId(String userId);
}
