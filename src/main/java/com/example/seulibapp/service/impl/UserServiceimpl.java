package com.example.seulibapp.service.impl;

import com.example.seulibapp.dao.UserDao;
import com.example.seulibapp.entity.User;
import com.example.seulibapp.service.MemcacheService;
import com.example.seulibapp.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@Transactional
@Scope("prototype")
public class UserServiceimpl implements UserService {
    @Resource
    private UserDao userDao;

    @Resource
    private  MemcacheService memcacheService;

    @Override
    public List<User>getUserList(){
        return userDao.findAll();
    }

    @Override
    public User getUserById(String id) {
        return userDao.findByUid(id);
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public User getUserByUsername(String username) {
        return userDao.findByUserName(username);
    }

    @Override
    public User addUser(User user) {
        return userDao.save(user);
    }

    @Override
    public User updateUser(User user) throws ExecutionException, InterruptedException {

        memcacheService.addToCache(user.getUserName(), 3600, user.toString());
        return userDao.save(user);
    }

    @Override
    public int deleteUser(String id) {
        userDao.delete(userDao.findByUid(id));

        if(userDao.findByUid(id) == null) {
            return 0;
        }
        else{
            return 1;
        }
    }

    @Override
    public boolean validateCredentials(String username, String password) {
        User user = userDao.findByUserName(username);
        if (user == null) {
            return false;
        }
        else if(!user.getPassword().equals(password)) {
            return false;
        }

        return true;
    }

}