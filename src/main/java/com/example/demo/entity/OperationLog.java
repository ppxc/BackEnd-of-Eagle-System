package com.example.demo.entity;

import lombok.Data;

@Data
public class OperationLog {
    private Long id;
    private String username;
    private String usercode;
    private String roles;
    private String operation;
    private String createTime;
}