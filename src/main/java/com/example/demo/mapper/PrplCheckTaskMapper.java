package com.example.demo.mapper;

import com.example.demo.entity.PrplCheckTask;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PrplCheckTaskMapper {

    int insert(PrplCheckTask prplCheckTask);

    PrplCheckTask selectById(Long id);

    int updateById(PrplCheckTask prplCheckTask);

    int deleteById(Long id);

    List<PrplCheckTask> getAllTasksByDate(String date);
}