package com.example.demo.controller;

import com.example.demo.dao.UserLocationDao;
import com.example.demo.entity.UserLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class LocationController {

    @Autowired
    private UserLocationDao userLocationDao;

    @GetMapping("/locations")
    public List<UserLocation> getLocations() {
        return userLocationDao.getAllLocations();
    }

    // ====================== 【修复完成】获取指定日期每个用户最新位置 ======================
    @GetMapping("/locations/latest")
    public List<UserLocation> getLatestLocations(
            @RequestParam(required = false) String date
    ) {
        LocalDate queryDate = date == null ? LocalDate.now() : LocalDate.parse(date);
        return userLocationDao.getLatestLocationsByDate(queryDate);
    }

    // ====================== 【修复完成】获取指定日期用户当天轨迹 ======================
    @GetMapping("/locations/user/{usercode}")
    public List<UserLocation> getTodayLocationsByUser(
            @PathVariable String usercode,
            @RequestParam(required = false) String date
    ) {
        LocalDate queryDate = date == null ? LocalDate.now() : LocalDate.parse(date);
        return userLocationDao.getUserLocationsByDate(usercode, queryDate);
    }
}