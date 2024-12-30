package com.example.seulibapp.request;

public class BorrowRequest {
    private Long bookId;
    private String userId;

    // Getters and Setters
    public Long getBookId() { return bookId; }
    public void setBookId(Long bookId) { this.bookId = bookId; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
}
