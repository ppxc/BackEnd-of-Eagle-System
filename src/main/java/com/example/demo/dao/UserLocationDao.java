package com.example.demo.dao;

import com.example.demo.entity.UserLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class UserLocationDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<UserLocation> getAllLocations() {
        String sql = "SELECT id, usercode, longitude, latitude, create_time FROM user_location";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(UserLocation.class));
    }

    // ====================== 获取指定日期每个用户最新位置（含姓名、ckl、dsl、hj） ======================
    public List<UserLocation> getLatestLocationsByDate(LocalDate date) {
        // 只传 日期字符串 给 SQL 的 date() 函数
        String dateStr = date.toString(); // 格式：2026-03-30

        String sql = "SELECT a.id, a.usercode, a.longitude, a.latitude, a.create_time, " +
                "b.username, b.ckl, b.dsl, b.hj " +
                "FROM user_location a " +
                "LEFT JOIN acd_ryzd b ON a.usercode = b.usercode " +
                "WHERE date(a.create_time) = ? " +  // 关键：= 日期，不用 >=
                "AND (a.usercode, a.create_time) IN " +
                "(SELECT usercode, MAX(create_time) FROM user_location " +
                " WHERE date(create_time) = ? GROUP BY usercode)";

        return jdbcTemplate.query(sql,
                new Object[]{ dateStr, dateStr },  // 只传日期字符串
                new BeanPropertyRowMapper<>(UserLocation.class));
    }

    // ====================== 【正确版】获取指定日期用户轨迹（也返回姓名） ======================
    public List<UserLocation> getUserLocationsByDate(String usercode, LocalDate date) {
        // 转成 2026-03-30 字符串
        String dateStr = date.toString();

        String sql = "SELECT a.id, a.usercode, a.longitude, a.latitude, a.create_time, " +
                "b.username, b.ckl, b.dsl, b.hj " +
                "FROM user_location a " +
                "LEFT JOIN acd_ryzd b ON a.usercode = b.usercode " +
                "WHERE a.usercode = ? AND date(a.create_time) = ? " +  // 这里改成按date匹配
                "ORDER BY a.create_time ASC";

        return jdbcTemplate.query(sql,
                new Object[]{usercode, dateStr},  // 参数对应：用户code + 日期字符串
                new BeanPropertyRowMapper<>(UserLocation.class));
    }
}