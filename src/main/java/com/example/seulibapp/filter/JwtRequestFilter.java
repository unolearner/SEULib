package com.example.seulibapp.filter;

import com.example.seulibapp.util.JwtUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
/**
 * JWT请求过滤器，用于处理每次HTTP请求，验证JWT令牌并设置身份验证信息
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private JwtUtil jwtUtil;
    /**
     * 执行过滤器的主要逻辑
     *
     * @param request HTTP请求对象
     * @param response HTTP响应对象
     * @param chain 过滤器链，用于将请求传递给下一个过滤器或目标资源
     * @throws ServletException 如果过滤过程中发生Servlet异常
     * @throws IOException 如果过滤过程中发生I/O异常
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        // 获取授权头信息
        final String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;
        // 检查授权头信息是否以"Bearer "开头
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }
        // 如果用户名不为空且当前安全上下文中尚未设置身份验证信息
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // 加载用户详细信息
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            // 验证JWT令牌是否有效
            if (jwtUtil.isTokenValid(jwt, userDetails.getUsername())) {
                // 创建身份验证令牌并设置用户详细信息和权限
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                // 设置Web身份验证详细信息
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // 将身份验证信息设置到安全上下文中
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        // 继续执行过滤链
        chain.doFilter(request, response);
    }
}
