package com.example.demo.controller;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.entity.User;
import com.example.demo.entity.Result;
import com.example.demo.mapper.UserMapper;
import com.example.demo.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;

    @Value("${jwt.expire}")
    private Long expire;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserMapper userMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userMapper = userMapper;
    }

    @PostMapping("/login")
    public Result<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
                )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = userMapper.findByUsername(loginRequest.getUsername());
            
            List<String> roles = parseRoles(user.getRole());
            
            String token = jwtUtil.generateToken(loginRequest.getUsername(), roles, user.getUsercode());

            LoginResponse.UserInfo userInfo = LoginResponse.UserInfo.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .usercode(user.getUsercode())
                    .roles(roles)
                    .build();

            LoginResponse response = LoginResponse.builder()
                    .token(token)
                    .tokenType("Bearer")
                    .expiresIn(expire)
                    .user(userInfo)
                    .build();

            logger.info("用户登录成功: {}", loginRequest.getUsername());
            return Result.success(response);
            
        } catch (Exception e) {
            logger.error("登录失败: {}", e.getMessage());
            return Result.error("用户名或密码错误");
        }
    }

    @GetMapping("/me")
    public Result<LoginResponse.UserInfo> getCurrentUser() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userMapper.findByUsername(username);
        if (user != null) {
            List<String> roles = parseRoles(user.getRole());
            
            LoginResponse.UserInfo userInfo = LoginResponse.UserInfo.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .usercode(user.getUsercode())
                    .roles(roles)
                    .build();
            
            return Result.success(userInfo);
        }
        
        return Result.error("用户不存在");
    }

    /**
     * 获取用户信息（按照前端期望的格式返回）
     */
    @GetMapping("/info")
    public Result<LoginResponse.UserInfo> getUserInfo() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userMapper.findByUsername(username);
        
        if (user != null) {
            List<String> roles = parseRoles(user.getRole());
            
            LoginResponse.UserInfo userInfo = LoginResponse.UserInfo.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .usercode(user.getUsercode())
                    .roles(roles)
                    .build();
            
            return Result.success(userInfo);
        }
        
        return Result.error("用户不存在");
    }

    private List<String> parseRoles(String roleStr) {
        if (roleStr == null || roleStr.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.asList(roleStr.split(","));
    }
}