package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.LocationCache;
import org.apache.ibatis.annotations.Param;

public interface LocationCacheMapper extends BaseMapper<LocationCache> {
    LocationCache findByCoordinates(@Param("longitude") Double longitude, @Param("latitude") Double latitude);
}