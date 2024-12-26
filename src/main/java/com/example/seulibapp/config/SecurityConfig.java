//package com.example.seulibapp.config;
//
//import com.example.seulibapp.filter.JwtRequestFilter;
//import com.example.seulibapp.util.JwtUtil;
//import jakarta.annotation.Resource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
///**
// * Spring Security配置类
// * 配置了认证管理器、安全过滤链以及密码编码器
// */
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Resource
//    private UserDetailsService userDetailsService;
//
//    @Resource
//    private JwtRequestFilter jwtRequestFilter;
//
//    @Resource
//    private JwtUtil jwtUtil;
//
//    @Resource
//    private PasswordEncoder passwordEncoder;
//
//    /**
//     * 配置全局认证信息
//     * @param auth 认证管理器构建器
//     * @throws Exception 可能抛出的异常
//     */
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//    }
//
//    /**
//     * 创建安全过滤链，配置请求的授权规则和会话管理策略
//     * @param http HttpSecurity对象，用于配置Web安全
//     * @return SecurityFilterChain 安全过滤链
//     * @throws Exception 可能抛出的异常
//     */
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf(AbstractHttpConfigurer::disable)
//                .authorizeRequests(authorize -> authorize
//                        .requestMatchers("/authenticate").permitAll() // 允许所有用户访问登录接口
//                        .anyRequest().authenticated() // 其他所有请求都需要认证
//                )
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 禁用会话管理
//                );
//
//        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//
//    /**
//     * 创建认证管理器
//     * @param authenticationConfiguration 认证配置
//     * @return AuthenticationManager 认证管理器
//     * @throws Exception 可能抛出的异常
//     */
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//
//    /**
//     * 创建密码编码器
//     * 使用NoOpPasswordEncoder，明文存储密码，仅用于测试环境；在这里更换加密算法
//     * @return PasswordEncoder 密码编码器
//     */
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//    }
//}
