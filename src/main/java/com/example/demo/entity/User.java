package com.example.demo.entity;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String usercode;
    private String username;
    private String password;
    private String role;
    private Integer status;
    private String reportTime;
    private String updateTime;
}