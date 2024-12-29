package com.example.seulibapp.response;

public class MyErrorResponse {
    private String code;
    private String message;

    public MyErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    // Getters
    public String getCode() { return code; }
    public String getMessage() { return message; }
}
