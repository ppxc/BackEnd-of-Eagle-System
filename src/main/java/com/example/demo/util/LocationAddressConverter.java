package com.example.demo.util;

import com.example.demo.entity.LocationCache;
import com.example.demo.entity.UserLocation;
import com.example.demo.mapper.LocationCacheMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;
import java.util.HashSet;
@Component
public class LocationAddressConverter {

    // 读取你配置的腾讯KEY
    @Value("${tencent.map.key}")
    private String tencentMapKey;
    @Value("${tencent.map.api-domain}")
    private String tencentMapDomain;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final LocationCacheMapper locationCacheMapper;

    // 线程池，每秒最多5个请求
    private final ExecutorService executorService = Executors.newFixedThreadPool(3);



    public LocationAddressConverter(LocationCacheMapper locationCacheMapper) {
        this.locationCacheMapper = locationCacheMapper;
    }

    public List<UserLocation> convertBatch(List<UserLocation> locationList) {
        if (locationList == null || locationList.isEmpty()) return locationList;

        // 过滤出需要解析的坐标
        List<UserLocation> needConvertList = new ArrayList<>();
        for (UserLocation loc : locationList) {

            if (loc.getLongitude() == null || loc.getLatitude() == null ||
                Double.isNaN(loc.getLongitude()) || Double.isNaN(loc.getLatitude())) {
                loc.setAddress("坐标无效");
                continue;
            }

            // 验证坐标范围（中国地区大致范围）
            if (loc.getLongitude() < 73 || loc.getLongitude() > 135 ||
                loc.getLatitude() < 3 || loc.getLatitude() > 53) {
                loc.setAddress("坐标范围无效");
                continue;
            }

            // 先查询数据库缓存（只查询有效的缓存）
            LocationCache cache = locationCacheMapper.findValidByCoordinates(formatDouble(loc.getLongitude(), 3), formatDouble(loc.getLatitude(), 3), formatDate(LocalDateTime.now()));
            if (cache != null) {
                loc.setAddress(cache.getAddress());
                continue;
            }

            needConvertList.add(loc);
        }

        // 并行处理需要转换的坐标
        if (!needConvertList.isEmpty()) {
            try {
                // 对需要处理的坐标去重（根据经纬度）
                List<UserLocation> uniqueList = new ArrayList<>();
                Set<String> processedCoords = new HashSet<>();
                Map<String, UserLocation> coordToLocation = new HashMap<>();

                for (UserLocation loc : needConvertList) {
                    String coordKey = formatDouble(loc.getLongitude(), 3) + "," + formatDouble(loc.getLatitude(), 3);
                    if (!processedCoords.contains(coordKey)) {
                        processedCoords.add(coordKey);
                        coordToLocation.put(coordKey, loc);
                    }
                }
                uniqueList.addAll(coordToLocation.values());

                // 并行处理去重后的坐标
                List<Future<?>> futures = new ArrayList<>();
                for (UserLocation loc : uniqueList) {
                    futures.add(executorService.submit(() -> {
                        try {
                            convertSingleLocation(loc);
                        } catch (Exception e) {
                            loc.setAddress("解析异常");
                            e.printStackTrace();
                        }
                    }));
                }

                // 等待所有任务完成
                for (Future<?> future : futures) {
                    future.get();
                }

                // 将解析结果回填到原列表中相同坐标的位置
                for (UserLocation loc : needConvertList) {
                    String coordKey = formatDouble(loc.getLongitude(), 3) + "," + formatDouble(loc.getLatitude(), 3);
                    UserLocation processedLoc = coordToLocation.get(coordKey);
                    if (processedLoc != null) {
                        loc.setAddress(processedLoc.getAddress());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return locationList;
    }

    private void convertSingleLocation(UserLocation loc) {
        try {
            Double lng = loc.getLongitude();
            Double lat = loc.getLatitude();
            lng = formatDouble(lng, 3);
            lat = formatDouble(lat, 3);

            String url = tencentMapDomain + "/ws/geocoder/v1/"
                    + "?location=" + lat + "," + lng
                    + "&key=" + tencentMapKey;

            String jsonResp = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(jsonResp);

            String address;
            if (root.path("status").asInt() == 0) {
                address = root.path("result").path("address").asText();
                // 存入或更新缓存
                updateToCache(lng, lat, address);
            } else {
                address = root.path("message").asText();
            }
            loc.setAddress(address);

            // 延迟300毫秒，防止每秒请求超限
            Thread.sleep(300);
        } catch (Exception e) {
            loc.setAddress("解析异常");
            e.printStackTrace();
        }
    }

    private void updateToCache(Double longitude, Double latitude, String address) {
        // 先查询是否存在缓存
        LocationCache existingCache = locationCacheMapper.findByCoordinates(longitude, latitude);
        if (existingCache != null) {
            // 更新缓存
            existingCache.setAddress(address);
            existingCache.setUpdateTime(formatDate(LocalDateTime.now()));
            existingCache.setExpireTime(formatDate(LocalDateTime.now().plusDays(180)));
            locationCacheMapper.updateById(existingCache);
        } else {
            // 插入新缓存
            saveToCache(longitude, latitude, address);
        }
    }

    private void saveToCache(Double longitude, Double latitude, String address) {
        LocationCache cache = new LocationCache();
        cache.setLongitude(longitude);
        cache.setLatitude(latitude);
        cache.setAddress(address);
        cache.setCreateTime(formatDate(LocalDateTime.now()));
        cache.setUpdateTime(formatDate(LocalDateTime.now()));
        cache.setExpireTime(formatDate(LocalDateTime.now().plusDays(180)));
        locationCacheMapper.insert(cache);
    }
    public String formatDate(LocalDateTime dateTime) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateFormatter.format(dateTime);
    }
    private double formatDouble(double value, int decimalPlaces) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(decimalPlaces, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}