package com.example.seulibapp.controller;

import com.example.seulibapp.entity.User;
import com.example.seulibapp.service.MemcacheService;
import com.example.seulibapp.service.UserService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.HashMap;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@CrossOrigin(origins = "*")
@RestController
@Scope("prototype")
@RequestMapping("/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;

    @Resource
    private MemcacheService memcacheService;

    @GetMapping("/list")
    public ResponseEntity<List<User>> getUserList() {
        List<User> users = userService.getUserList();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody User user) {
        try {
            if (user == null || user.getUserName() == null || user.getPassword() == null) {
                log.warn("Invalid input data: {}", user);
                return new ResponseEntity<>(Collections.singletonMap("error", "Invalid input data"), HttpStatus.BAD_REQUEST);
            }

            // 检查缓存中的用户信息
            User cachedUser = memcacheService.getFromCache(user.getUserName(), User.class);

            if (cachedUser != null) {
                log.info("User {} already logged in", user.getUserName());
                Map<String, Object> response = new HashMap<>();
                response.put("message", "User already logged in");
                response.put("user", cachedUser);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                // 执行用户名和密码验证
                boolean valid = userService.validateCredentials(user.getUserName(), user.getPassword());
                if (valid) {
                    log.info("Login successful for user: {}", user.getUserName());
                    // 登录成功，设置会话或返回登录令牌
                    memcacheService.addToCache(user.getUserName(), 3600, user);
                    Map<String, Object> response = new HashMap<>();
                    response.put("message", "Login successful");
                    response.put("user", user);
                    return new ResponseEntity<>(response, HttpStatus.OK);
                } else {
                    log.warn("Invalid credentials for user: {}", user.getUserName());
                    // 登录失败，返回错误信息
                    return new ResponseEntity<>(Collections.singletonMap("error", "Invalid username or password"), HttpStatus.UNAUTHORIZED);
                }
            }
        } catch (ExecutionException | InterruptedException e) {
            log.error("Error occurred during cache operation", e);
            return new ResponseEntity<>(Collections.singletonMap("error", "Cache operation failed"), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            log.error("Unexpected error occurred during login", e);
            return new ResponseEntity<>(Collections.singletonMap("error", "An unexpected error occurred"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}