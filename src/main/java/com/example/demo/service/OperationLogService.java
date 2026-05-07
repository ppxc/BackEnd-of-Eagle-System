package com.example.demo.service;

import com.example.demo.entity.OperationLog;

import java.util.Map;

public interface OperationLogService {

    void save(OperationLog log);

    Map<String, Object> queryPage(Map<String, Object> params);
}