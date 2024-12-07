package com.example.seulibapp.service;

import com.example.seulibapp.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public interface UserService {
    public List<User>getUserList();
    public User getUserById(String id);
    public User getUserByEmail(String email);
    public User getUserByUsername(String username);
    public User addUser(User user);

    public User updateUser(User user)throws ExecutionException, InterruptedException;
    public int deleteUser(String id);

    public boolean validateCredentials(String username, String password);
}
