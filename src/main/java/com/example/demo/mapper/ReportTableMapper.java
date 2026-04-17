package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.PeriodicReportTable;
import com.example.demo.entity.Cur_Gzl_Table;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ReportTableMapper extends BaseMapper<PeriodicReportTable> {
    // 继承BaseMapper，自带所有CRUD方法，不用写SQL
    List<Cur_Gzl_Table> getCurGzlData(@Param("startDate") String startDate, 
                                     @Param("endDate") String endDate, 
                                     @Param("comName") String comName, 
                                     @Param("groups") String groups, 
                                     @Param("userName") String userName);
}