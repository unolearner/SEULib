package com.example.seulibapp.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;


@Entity
public class User {
    @Id
    //用户id 主键
    private String uid;
    //用户名
    private String userName;
    //用户密码
    private String password;
    //用户电子邮箱
    private String email;
    //用户类型，普通用户/管理员
    private String userType;

    public String getuid() {
        return uid;
    }

    public void setuid(String id) {
        this.uid = id;
    }

    public String getUserName() {
        return userName;
    }

    public String setUserName(String userName) {
        this.userName = userName;
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
