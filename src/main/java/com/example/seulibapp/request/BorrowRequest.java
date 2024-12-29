package com.example.seulibapp.request;

public class BorrowRequest {
    private String bookId;
    private String userId;

    // Getters and Setters
    public String getBookId() { return bookId; }
    public void setBookId(String bookId) { this.bookId = bookId; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
}
