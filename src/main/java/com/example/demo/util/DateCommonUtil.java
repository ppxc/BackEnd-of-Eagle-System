package com.example.demo.util;

import jakarta.annotation.Resource;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

@Component
public class DateCommonUtil {

    @Resource
    private SqlSession sqlSession;

    /**
     * 通用：执行自定义SQL，返回最大日期
     */
    public String getMaxDate(String sql) {
        return sqlSession.selectOne("com.example.demo.mapper.ReportTableMapper.executeSql", sql);
    }

}