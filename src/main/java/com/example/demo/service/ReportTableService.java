package com.example.demo.service;

import com.example.demo.entity.CurGzlTableRy;
import com.example.demo.entity.CurGzlTableBm;
import java.util.List;

public interface ReportTableService {
    List<CurGzlTableRy> getCurGzlData(String startDate, String endDate, String comName, String groups, String userName);

    // 通用获取最大日期
    String getMaxTjDate(String tableName);

    // 新增：按部门统计
    List<CurGzlTableBm> getCurGzlDataBm(String startDate, String endDate, String comName);

}