package com.example.seulibapp.dao;

import com.example.seulibapp.entity.BookRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository("recordDao")
public interface RecordDao extends JpaRepository<BookRecord, Long> {
    List<BookRecord> findByUserId(String userId);
    List<BookRecord> findByBid(Long bookId);

    @Query("SELECT r FROM BookRecord r WHERE r.userId = :userId " +
            "AND r.actionType = 'REBORROW' AND r.actionDate >= :startDate")
    List<BookRecord>findRecentOneMonthReBorrow(@Param("userId") String userId,
                                           @Param("startDate")String startDate);

    @Query("SELECT r FROM BookRecord r WHERE r.userId = :userId " +
            "AND (r.actionType = 'REBORROW' OR r.actionType='BORROW') AND r.bid = :bookId")
    List<BookRecord>findBeforeBorrowAndReborrow(@Param("userId") String userId,
                                                @Param("bookId") long bookId);
}
