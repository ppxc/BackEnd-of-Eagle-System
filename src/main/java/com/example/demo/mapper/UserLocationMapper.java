package com.example.demo.mapper;

import com.example.demo.entity.UserLocation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserLocationMapper {

    int insert(UserLocation userLocation);

    UserLocation selectById(Long id);

    int updateById(UserLocation userLocation);

    int deleteById(Long id);

    List<UserLocation> selectByUsercode(@Param("usercode") String usercode);

    List<UserLocation> getLatestLocationsByDate(
            @Param("dateStr") String dateStr,
            @Param("groupscode") String groupscode,
            @Param("keyword") String keyword
    );

    List<UserLocation> getUserLocationsByDate(@Param("usercode") String usercode, @Param("dateStr") String dateStr);

    List<UserLocation> getAllLocationsOrderByTime(@Param("dateStr") String dateStr);

    List<UserLocation> selectAll();
}