package com.example.seulibapp.dao;


import com.example.seulibapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    // 自动实现，查询所有用户
    List<User> findAll();

    // 根据 uid 查询用户
    User findByUid(String id);

    // 根据 email 查询用户
    User findByEmail(String email);

    // 根据 username 查询用户
    User findByUserName(String username);

}
