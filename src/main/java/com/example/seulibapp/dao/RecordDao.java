package com.example.seulibapp.dao;

import com.example.seulibapp.entity.BookRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("recordDao")
public interface RecordDao extends JpaRepository<BookRecord, String> {
    List<Record> findByUserUid(String userId);
    List<Record> findByBookBid(String bookId);
}
