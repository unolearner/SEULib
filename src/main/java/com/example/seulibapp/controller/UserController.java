package com.example.seulibapp.controller;

import com.example.seulibapp.entity.User;
import com.example.seulibapp.service.MemcacheService;
import com.example.seulibapp.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Scope("prototype")
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @Resource
    private MemcacheService memcacheService;

    @ResponseBody
    @RequestMapping("/list")
    public List<User> getUserList() {
        return userService.getUserList();
    }

    @RequestMapping("/login")
    public Object login(User user) {
        //登入
        try {
            // 检查缓存中的用户信息
            Object o = memcacheService.getFromCache(user.getUserName());

            if (o != null) {
                // 用户已登录，返回相关信息
                return new ResponseEntity<>("User already logged in", HttpStatus.OK);
            } else {
                // 执行用户名和密码验证
                boolean valid = userService.validateCredentials(user.getUserName(), user.getPassword());
                if (valid) {
                    // 登录成功，设置会话或返回登录令牌
                    memcacheService.addToCache(user.getUserName(), 3600, user.toString());
                    return new ResponseEntity<>("Login successful", HttpStatus.OK);
                } else {
                    // 登录失败，返回错误信息
                    return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error occurred during login", HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

}
