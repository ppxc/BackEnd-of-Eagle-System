package com.example.demo.controller;

import com.example.demo.entity.CurGzlTableRy;
import com.example.demo.entity.Result;
import com.example.demo.service.ReportTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*") // 允许前端跨域访问
public class ReportTableController {

    @Autowired
    private ReportTableService reportTableService;

    @GetMapping("/cur_gzl/list")
    public Result<List<CurGzlTableRy>> getCurGzlList(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String comName,
            @RequestParam(required = false) String groups,
            @RequestParam(required = false) String userName
    ) {
        try {
            if (endDate == null && endDate.trim().isEmpty()) {
                endDate = startDate; // 如果没有结束日期，使用开始日期
            }
            
            System.out.println("接收到的参数：");
            System.out.println("startDate: " + startDate);
            System.out.println("endDate: " + endDate);
            System.out.println("comName: " + comName);
            System.out.println("groups: " + groups);
            System.out.println("userName: " + userName);
            
            List<CurGzlTableRy> data = reportTableService.getCurGzlData(startDate, endDate, comName, groups, userName);
            return Result.success(data);
        } catch (Exception e) {
            return Result.error("获取当前工作量数据失败: " + e.getMessage());
        }
    }

}
