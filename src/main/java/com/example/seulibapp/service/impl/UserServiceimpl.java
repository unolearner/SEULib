package com.example.seulibapp.service.impl;

import com.example.seulibapp.dao.UserDao;
import com.example.seulibapp.entity.User;
import com.example.seulibapp.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Scope("prototype")
public class UserServiceimpl implements UserService {
    @Resource
    private UserDao userDao;

    @Override
    public List<User>getUserList(){
        return userDao.getUserList();
    }

    @Override
    public User getUserById(int id) {
        return userDao.getUserById(id);
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    @Override
    public int addUser(User user) {
        return userDao.addUser(user);
    }

    @Override
    public int updateUser(User user) {
        return userDao.updateUser(user);
    }

    @Override
    public int deleteUser(int id) {
        return userDao.deleteUser(id);
    }

    @Override
    public boolean validateCredentials(String username, String password) {
         User user = userDao.getUserByUsername(username);
         if (user == null) {
             return false;
         }
         else if(!user.getPassword().equals(password)) {
             return false;
         }

         return true;
    }

}
