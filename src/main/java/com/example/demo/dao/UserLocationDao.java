package com.example.demo.dao;  // 注意改成你的包名！

import com.example.demo.entity.UserLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class UserLocationDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<UserLocation> getAllLocations() {
        String sql = "SELECT id, usercode, longitude, latitude, create_time FROM user_location";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(UserLocation.class));
    }
}