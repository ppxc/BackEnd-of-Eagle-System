package com.example.demo.controller;

import com.example.demo.entity.PeriodicReportTable;
import com.example.demo.entity.GzlSs;
import com.example.demo.entity.Cur_Gzl_Table;
import com.example.demo.entity.Result;
import com.example.demo.service.ReportTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin("*") // 允许前端跨域访问
public class ReportTableController {

    @Autowired
    private ReportTableService reportTableService;

    /**
     * 查询aaa表所有数据
     * 接口地址：GET http://localhost:8080/api/aaa/list
     */
    @GetMapping("/aaa/list")
    public List<PeriodicReportTable> getReportTableList() {
        return reportTableService.getAllData();
    }

    // ====================== 获取指定时间所有数据 ======================
    @GetMapping("/gzlSs/list")
    public Map<String, Object> getGzlSsList(
            @RequestParam(value = "queryTime", required = false) String queryTime
    ) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<GzlSs> data = reportTableService.getGzlSsList(queryTime);
            result.put("code", 200);
            result.put("data", data);
            result.put("msg", "查询成功");
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", "查询失败");
        }
        return result;
    }

    // 新增：获取当前工作量数据（支持日期范围查询）
    @GetMapping("/cur_gzl/list")
    public Result<List<Cur_Gzl_Table>> getCurGzlList(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String comName,
            @RequestParam(required = false) String groups,
            @RequestParam(required = false) String userName
    ) {
        try {
            // 处理日期格式：将带连字符的日期转换为不带连字符的格式
            // if (startDate != null && !startDate.trim().isEmpty()) {
            //     startDate = startDate.replace("-", "");
            // } else {
            //     // 如果没有提供日期，默认使用今天
            //     startDate = java.time.LocalDate.now().toString().replace("-", "");
            // }
            if (endDate == null && endDate.trim().isEmpty()) {
                endDate = startDate; // 如果没有结束日期，使用开始日期
            }
            
            System.out.println("接收到的参数：");
            System.out.println("startDate: " + startDate);
            System.out.println("endDate: " + endDate);
            System.out.println("comName: " + comName);
            System.out.println("groups: " + groups);
            System.out.println("userName: " + userName);
            
            List<Cur_Gzl_Table> data = reportTableService.getCurGzlData(startDate, endDate, comName, groups, userName);
            return Result.success(data);
        } catch (Exception e) {
            return Result.error("获取当前工作量数据失败: " + e.getMessage());
        }
    }

}
