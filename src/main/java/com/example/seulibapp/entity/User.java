package com.example.seulibapp.entity;


import lombok.Data;

@Data
public class User {
    //用户id 主键
    private String id;
    //用户名
    private String userName;
    //用户密码
    private String password;
    //用户电子邮箱
    private String email;
    //用户类型，普通用户/管理员
    private String userType;

    //登录状态
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getStatus(){
        return this.status;
    }

    public void setStatus(String status){
        this.status = status;
    }
}
