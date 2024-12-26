//package com.example.seulibapp.util;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.stereotype.Component;
//import javax.crypto.SecretKey;
//import javax.crypto.spec.SecretKeySpec;
//import java.util.Base64;
//import java.util.Date;
///**
// * JWT工具类，用于生成和验证JWT令牌
// */
//@Component
//public class JwtUtil {
//    // 使用一个更长的密钥字符串
//    private static final String secret = "EaS8NqWmZlXjPdRbVtGyHuFkJeQpLrAz";
//    // 将密钥转换为 Base64 编码的字符串
//    private SecretKey getSecretKey() {
//        byte[] keyBytes = Base64.getEncoder().encode(secret.getBytes());
//        return new SecretKeySpec(keyBytes,
//                SignatureAlgorithm.HS256.getJcaName());
//    }
//    /**
//     * 生成JWT令牌
//     * @param username 用户名，作为令牌的主题
//     * @return 生成的JWT令牌字符串
//     */
//    public String generateToken(String username) {
//        return Jwts.builder()
//                .setSubject(username)
//                .signWith(getSecretKey())
//                .compact();
//    }
//    /**
//     * 提取JWT令牌中的所有声明
//     * @param token JWT令牌字符串
//     * @return 包含所有声明的Claims对象
//     */
//    public Claims extractAllClaims(String token) {
//        return Jwts.parser()
//                .setSigningKey(secret)
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//    /**
//     * 从JWT令牌中提取用户名
//     * @param token JWT令牌字符串
//     * @return 提取的用户名
//     */
//    public String extractUsername(String token) {
//        return extractAllClaims(token).getSubject();
//    }
//    /**
//     * 验证JWT令牌是否有效
//     * @param token JWT令牌字符串
//     * @param username 预期的用户名
//     * @return 如果令牌有效且用户名匹配，则返回true；否则返回false
//     */
//    public boolean isTokenValid(String token, String username) {
//        final String extractedUsername = extractUsername(token);
//        return (extractedUsername.equals(username) && !isTokenExpired(token));
//    }
//    /**
//     * 检查JWT令牌是否过期
//     * @param token JWT令牌字符串
//     * @return 如果令牌已过期，则返回true；否则返回false
//     */
//    private boolean isTokenExpired(String token) {
//        return extractAllClaims(token).getExpiration().before(new Date());
//    }
//}
