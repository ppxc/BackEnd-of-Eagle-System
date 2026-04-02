package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demo.entity.UserLocation;
import com.example.demo.mapper.UserLocationMapper;
import com.example.demo.service.UserLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class UserLocationServiceImpl implements UserLocationService {

    @Autowired
    private UserLocationMapper userLocationMapper;

    // 用MyBatis-Plus自带的selectList实现，不用写SQL
    @Override
    public List<UserLocation> getAllLocations() {
        return userLocationMapper.selectList(new LambdaQueryWrapper<>());
    }

    // 调用自定义SQL方法
    @Override
    public List<UserLocation> getLatestLocationsByDate(LocalDate date) {
        String dateStr = date.toString();
        return userLocationMapper.getLatestLocationsByDate(dateStr);
    }

    // 调用自定义SQL方法
    @Override
    public List<UserLocation> getUserLocationsByDate(String usercode, LocalDate date) {
        String dateStr = date.toString();
        return userLocationMapper.getUserLocationsByDate(usercode, dateStr);
    }
}