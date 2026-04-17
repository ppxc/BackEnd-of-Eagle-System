package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.PeriodicReportTable;
import com.example.demo.entity.GzlSs;
import com.example.demo.entity.Cur_Gzl_Table;
import java.util.List;

public interface ReportTableService extends IService<PeriodicReportTable> {
    List<PeriodicReportTable> getAllData();
    List<GzlSs> getGzlSsList(String queryTime);
    List<Cur_Gzl_Table> getCurGzlData(String startDate, String endDate, String comName, String groups, String userName);
}