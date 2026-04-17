package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.PeriodicReportTable;
import com.example.demo.entity.GzlSs;
import com.example.demo.entity.Cur_Gzl_Table;
import com.example.demo.mapper.ReportTableMapper;
import com.example.demo.mapper.GzlSsMapper;
import com.example.demo.service.ReportTableService;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import java.util.List;

@Service
public class ReportTableServiceImpl extends ServiceImpl<ReportTableMapper, PeriodicReportTable> implements ReportTableService {

    @Resource
    private GzlSsMapper gzlSsMapper;

    @Resource
    private ReportTableMapper reportTableMapper;

    @Override
    public List<PeriodicReportTable> getAllData() { 
        return this.list(); // 查询aaa表所有数据
    }

    @Override
    public List<GzlSs> getGzlSsList(String queryTime) {
        return gzlSsMapper.list(queryTime);
    }

    @Override
    public List<Cur_Gzl_Table> getCurGzlData(String startDate, String endDate, String comName, String groups, String userName) {
        return reportTableMapper.getCurGzlData(startDate, endDate, comName, groups, userName);
    }
}
