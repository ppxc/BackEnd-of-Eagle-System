package com.example.demo.mapper;

import com.example.demo.entity.OperationLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface OperationLogMapper {

    int insert(OperationLog operationLog);

    OperationLog selectById(Long id);

    int updateById(OperationLog operationLog);

    int deleteById(Long id);

    List<OperationLog> selectByCondition(Map<String, Object> params);

    int countByCondition(Map<String, Object> params);
}