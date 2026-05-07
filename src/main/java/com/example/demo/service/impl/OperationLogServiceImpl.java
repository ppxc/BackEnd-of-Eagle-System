package com.example.demo.service.impl;

import com.example.demo.entity.OperationLog;
import com.example.demo.mapper.OperationLogMapper;
import com.example.demo.service.OperationLogService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OperationLogServiceImpl implements OperationLogService {

    private final OperationLogMapper operationLogMapper;

    public OperationLogServiceImpl(OperationLogMapper operationLogMapper) {
        this.operationLogMapper = operationLogMapper;
    }

    @Override
    public void save(OperationLog log) {
        operationLogMapper.insert(log);
    }

    @Override
    public Map<String, Object> queryPage(Map<String, Object> params) {
        int page = params.containsKey("page") ? Integer.parseInt(params.get("page").toString()) : 1;
        int pageSize = params.containsKey("pageSize") ? Integer.parseInt(params.get("pageSize").toString()) : 20;

        normalizeDateParam(params, "startTime", " 00:00:00");
        normalizeDateParam(params, "endTime", " 23:59:59");

        int offset = (page - 1) * pageSize;
        params.put("offset", offset);
        params.put("limit", pageSize);

        List<OperationLog> list = operationLogMapper.selectByCondition(params);
        int total = operationLogMapper.countByCondition(params);

        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("total", total);
        result.put("page", page);
        result.put("pageSize", pageSize);
        return result;
    }

    private void normalizeDateParam(Map<String, Object> params, String key, String append) {
        Object value = params.get(key);
        if (value != null && value.toString().length() == 10) {
            params.put(key, value.toString() + append);
        }
    }
}