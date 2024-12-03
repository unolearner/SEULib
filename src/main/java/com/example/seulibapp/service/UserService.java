package com.example.seulibapp.service;

import com.example.seulibapp.entity.User;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface UserService {
    public List<User>getUserList();
    public User getUserById(int id);
    public User getUserByEmail(String email);
    public User getUserByUsername(String username);
    public int addUser(User user);

    public int updateUser(User user)throws ExecutionException, InterruptedException;
    public int deleteUser(int id);

    public boolean validateCredentials(String username, String password);
}
